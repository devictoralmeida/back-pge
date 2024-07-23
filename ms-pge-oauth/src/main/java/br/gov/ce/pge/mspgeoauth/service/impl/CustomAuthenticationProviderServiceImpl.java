package br.gov.ce.pge.mspgeoauth.service.impl;

import br.gov.ce.pge.mspgeoauth.client.CondicaoAcessoFeignClient;
import br.gov.ce.pge.mspgeoauth.client.UsuarioFeingClient;
import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeoauth.constants.SharedConstant;
import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.CondicaoAcesso;
import br.gov.ce.pge.mspgeoauth.entity.IpUsuario;
import br.gov.ce.pge.mspgeoauth.entity.RequisicaoLogar;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.enums.SituacaoUsuario;
import br.gov.ce.pge.mspgeoauth.enums.TipoUsuario;
import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.LdapAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.RedisService;
import br.gov.ce.pge.mspgeoauth.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CustomAuthenticationProviderServiceImpl implements CustomAuthenticationProviderService {

    private final UsuarioFeingClient usuarioFeingClient;

    private final PasswordEncoder passwordEncoder;

    private final CondicaoAcessoFeignClient condicaoAcessoClient;

    private final LdapAuthenticationProviderService ldapAuthentication;

    private final RedisService redisService;


    @Autowired
    public CustomAuthenticationProviderServiceImpl(UsuarioFeingClient usuarioFeingClient, PasswordEncoder passwordEncoder,
                                                   CondicaoAcessoFeignClient condicaoAcessoClient, LdapAuthenticationProviderService ldapAuthentication,
                                                   RedisService redisService) {
        this.usuarioFeingClient = usuarioFeingClient;
        this.passwordEncoder = passwordEncoder;
        this.condicaoAcessoClient = condicaoAcessoClient;
        this.ldapAuthentication = ldapAuthentication;
        this.redisService = redisService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        Usuario usuario = getUsuario(username);

        validarUsuarioExistente(usuario);

        List<CondicaoAcesso> condicaoAcesso = getCondicaoAcessos();

        return getAuthentication(authentication, usuario, condicaoAcesso);
    }

    private List<CondicaoAcesso> getCondicaoAcessos() {
        try {
            ResponseDto<List<CondicaoAcesso>> response = condicaoAcessoClient.findCondicaoAcesso().getBody();
            return response.getData();
        } catch (FeignException feignException) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_REQUISICAO_USUARIO);
        }
    }

    private Usuario getUsuario(String username) {
        try {
            ResponseEntity<ResponseDto<Usuario>> usuarioResponse = usuarioFeingClient.findUser(username);
            return usuarioResponse.getBody().getData();
        } catch (FeignException feignException) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_REQUISICAO_USUARIO);
        }
    }

    private Authentication getAuthentication(Authentication authentication, Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {

        if (TipoUsuario.INTERNO.equals(usuario.getTipoUsuario())) {
            return usuarioInterno(authentication, usuario, condicaoAcesso);
        } else {
            return usuarioExterno(authentication, usuario, condicaoAcesso);
        }
    }

    private Authentication usuarioInterno(Authentication authentication, Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {

        try{
            Authentication auth = ldapAuthentication.authenticate(authentication, usuario.getUsuarioRede());

            validarSituacao(usuario);

            return auth;
        } catch (AuthenticationException e) {
            erroLogin(usuario, condicaoAcesso);
        }
        throw new NegocioException(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR);
    }

    private Authentication usuarioExterno(Authentication authentication, Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {
        UserDetails usuarioExterno = usuario;
        if(!passwordEncoder.matches(authentication.getCredentials().toString(), usuarioExterno.getPassword())) {
            erroLogin(usuario, condicaoAcesso);
        }

        validarSituacao(usuario);

        if(TipoUsuario.EXTERNO.equals(usuario.getTipoUsuario())) {
            validarUltimaAlteracaoSenha(usuario, condicaoAcesso);
        }

        return new UsernamePasswordAuthenticationToken(usuarioExterno, null, usuarioExterno.getAuthorities());
    }

    private void erroLogin(Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {
        if(!validarTentativa(usuario, condicaoAcesso)) {
            usuarioFeingClient.bloquearUsuario(usuario.getId());
            usuarioFeingClient.requisicaoLogin(usuario.getId(), false);
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_ACESSO_BLOQUEADO);
        }
        usuarioFeingClient.requisicaoLogin(usuario.getId(), false);
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
            List<RequisicaoLogar> requisicoesLogin = getRequisicoes(usuario);

            LocalDateTime limiteTempo = LocalDateTime.now().minus(condicaoAcesso.get(SharedConstant.INDICE_INICIAL).getIntervaloBloqueio(), ChronoUnit.MINUTES);

            List<RequisicaoLogar> requisicoes = requisicoesLogin.stream().filter(r -> r.getHoraRequisicao().isBefore(LocalDateTime.now()) && r.getHoraRequisicao().isAfter(limiteTempo)).toList();

            return requisicoes.size() < condicaoAcesso.get(SharedConstant.INDICE_INICIAL).getTentativasInvalidas() - SharedConstant.SUBTRACAO_INDICE;
        }

        return true;
    }

    private List<RequisicaoLogar> getRequisicoes(Usuario usuario) {
        try {
            ResponseEntity<ResponseDto<List<RequisicaoLogar>>> requisicoesLogar = usuarioFeingClient.getRequisicoesLogar(usuario.getId());
            return requisicoesLogar.getBody().getData();
        } catch (FeignException feignException) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_REQUISICAO_USUARIO);
        }
    }

    private void validarUltimaAlteracaoSenha(Usuario usuario, List<CondicaoAcesso> condicaoAcesso) {
        if(condicaoAcesso != null && !condicaoAcesso.isEmpty()) {
            LocalDateTime dataAtual       = LocalDateTime.now();
            LocalDateTime ultimaAlteracao = usuario.getDataUltimaAlteracaoSenha();
            int quantidadeAlteracao       = condicaoAcesso.get(SharedConstant.INDICE_INICIAL).getAlteracaoSenha();

            if(ultimaAlteracao != null) {
                long diferenca = ultimaAlteracao.until(dataAtual, ChronoUnit.DAYS);

                if(diferenca > quantidadeAlteracao) {
                    throw new NegocioException(MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA + usuario.getId());
                }
            }
        }
    }

    private void validarSituacao(Usuario usuario) {
        if(SituacaoUsuario.BLOQUEADA.equals(usuario.getSituacao()) || SituacaoUsuario.INATIVA.equals(usuario.getSituacao())) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA);
        }
    }

    @Override
    public void validarDispositivo(LoginDto loginDto, HttpServletRequest request) throws JsonProcessingException {

        String ip = request.getRemoteAddr();

        Usuario usuario = getUsuario(loginDto.getLogin());

        if(loginDto.getNovoDispositivo() == null || !loginDto.getNovoDispositivo()) {

            ObjectMapper objectMapper = new ObjectMapper();

            String cache = this.redisService.getUserIp(usuario.getNome());

            if(cache != null && !cache.isEmpty()) {
                IpUsuario userIp = objectMapper.readValue(cache, IpUsuario.class);

                if(userIp != null && !userIp.getIp().equals(ip)) {
                    throw new NegocioException(MessageCommonsContanst.MENSAGEM_JA_LOGADO);
                }
            }
        }

    }

}