package br.gov.ce.pge.mspgeoauth.service.impl;

import br.gov.ce.pge.mspgeoauth.client.CondicaoAcessoFeignClient;
import br.gov.ce.pge.mspgeoauth.client.UsuarioFeingClient;
import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.CondicaoAcesso;
import br.gov.ce.pge.mspgeoauth.entity.RequisicaoLogar;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.enums.SituacaoUsuario;
import br.gov.ce.pge.mspgeoauth.enums.TipoUsuario;
import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.LdapAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.shared.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CustomAuthenticationProviderServiceImpl implements CustomAuthenticationProviderService {

    @Autowired
    private UsuarioFeingClient usuarioFeingClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CondicaoAcessoFeignClient condicaoAcessoClient;

    @Autowired
    private LdapAuthenticationProviderService ldapAuthentication;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        ResponseEntity<ResponseDto<Usuario>> usuarioResponse = usuarioFeingClient.findUser(username);

        var usuario = usuarioResponse.getBody().getData();

        validarUsuarioExistente(usuario);

        ResponseDto<List<CondicaoAcesso>> response = condicaoAcessoClient.findCondicaoAcesso().getBody();
        List<CondicaoAcesso> condicaoAcesso = response.getData();

        if(usuario.getTipoUsuario().equals(TipoUsuario.EXTERNO)) {
            validarUltimaAlteracaoSenha(usuario, condicaoAcesso);
        }

        validarSituacao(usuario);

        return getAuthentication(authentication, username, usuario, condicaoAcesso);
    }

    private Authentication getAuthentication(Authentication authentication, String username, Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {
        try {
        	
            return getAuthenticationPorTipoUsuario(authentication, username, usuario);
            
        } catch (Exception e) {
            if(!validarTentativa(usuario, condicaoAcesso)) {
                usuarioFeingClient.bloquearUsuario(usuario.getId());
                usuarioFeingClient.requisicaoLogin(usuario.getId(), false);
                throw new NegocioException(MessageCommonsContanst.MENSAGEM_ACESSO_BLOQUEADO);
            }
            usuarioFeingClient.requisicaoLogin(usuario.getId(), false);
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR);
        }
    }

	private Authentication getAuthenticationPorTipoUsuario(Authentication authentication, String username, Usuario usuario) {
		if (usuario.getTipoUsuario().equals(TipoUsuario.INTERNO)) {
		    return ldapAuthentication.authenticate(authentication, username);
		} else {
		    return usuarioExterno(authentication, usuario);
		}
	}

	private Authentication usuarioExterno(Authentication authentication, Usuario usuario) {
		UserDetails usuarioExterno = usuario;
		if (passwordEncoder.matches(authentication.getCredentials().toString(), usuarioExterno.getPassword())) {
		    return new UsernamePasswordAuthenticationToken(usuarioExterno, null, usuarioExterno.getAuthorities());
		}
		throw new NegocioException(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR);
	}

    private void validarUsuarioExistente(Usuario usuario) {
        if(usuario == null) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean validarTentativa(Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {
        if(condicaoAcesso != null && !condicaoAcesso.isEmpty()) {
            ResponseEntity<ResponseDto<List<RequisicaoLogar>>> requisicoesLogar = usuarioFeingClient.getRequisicoesLogar(usuario.getId());

            LocalDateTime limiteTempo = LocalDateTime.now().minus(condicaoAcesso.get(0).getIntervaloBloqueio(), ChronoUnit.MINUTES);

            List<RequisicaoLogar> requisicoes = requisicoesLogar.getBody().getData().stream().filter(r -> r.getHoraRequisicao().isBefore(LocalDateTime.now()) && r.getHoraRequisicao().isAfter(limiteTempo)).toList();

            return requisicoes.size() < condicaoAcesso.get(0).getTentativasInvalidas() - 1;
        }

        return true;
    }

    private void validarUltimaAlteracaoSenha(Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {
        if(condicaoAcesso != null && !condicaoAcesso.isEmpty()) {
            LocalDateTime dataAtual       = LocalDateTime.now();
            LocalDateTime ultimaAlteracao = usuario.getDataUltimaAlteracaoSenha();
            int quantidadeAlteracao       = condicaoAcesso.get(0).getAlteracaoSenha();

            if(ultimaAlteracao != null) {
                long diferenca = ultimaAlteracao.until(dataAtual, ChronoUnit.DAYS);

                if(diferenca > quantidadeAlteracao) {
                    throw new NegocioException(MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA + usuario.getId());
                }
            }
        }
    }

    private void validarSituacao(Usuario usuario) {
        if(usuario.getSituacao().equals(SituacaoUsuario.BLOQUEADA) || usuario.getSituacao().equals(SituacaoUsuario.INATIVA)) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA);
        }
    }

}
