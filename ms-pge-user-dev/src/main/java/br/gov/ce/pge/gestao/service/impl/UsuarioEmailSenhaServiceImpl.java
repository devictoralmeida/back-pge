package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.constants.MessageUsuarioConstant;
import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDto;
import br.gov.ce.pge.gestao.entity.CodigoVerificacao;
import br.gov.ce.pge.gestao.entity.RequisicaoRecuperarSenha;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.repository.CondicaoAcessoRepository;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import br.gov.ce.pge.gestao.security.Password;
import br.gov.ce.pge.gestao.service.*;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.CodigoUtil;
import com.google.gson.Gson;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UsuarioEmailSenhaServiceImpl implements UsuarioEmailSenhaService {

	private static final int EXPIRATION_MINUTES = 30;

    @Value("${url.sistema.portalOrigens}")
    private String urlPortalOrigens;

    @Value("${url.sistema.portalDivida}")
    private String urlPortalDivida;

    @Autowired
    private UsuarioConsultaService consultaService;

    @Autowired
    private CodigoVerificacaoService codigoService;

    @Autowired
    private EnviarEmailService enviarEmailService;

    @Autowired
    private RequisicaoRecuperarSenhaService recuperacaoService;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CondicaoAcessoRepository condicaoAcessoRepository;

    @Override
    public void sendChangeEmail(UsuarioCadastroSenhaRequestDto request, UUID id, String nomeSistema) {
        var usuario = consultaService.findByIdModel(id);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_SENHA_INCORRETA);
        }

        var codigoVerificacao = new CodigoVerificacao();
        codigoVerificacao.setCodigo(CodigoUtil.gerarCodigoSeisDigitos());
        codigoVerificacao.setDataExpiracao(LocalDateTime.now().plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES));

        var codigoSalvo = codigoService.save(codigoVerificacao);

        usuario.setCodigoVerificacao(codigoSalvo);
        repository.save(usuario);

        this.dispararEmail(usuario, nomeSistema, MessageUsuarioConstant.TEMPLATE_ALTERAR_SENHA_FTLH, MessageUsuarioConstant.ACAO_ALTERAR);
    }

    @Override
    public void sendRecoveryEmail(String email, String nomeSistema) {
        var usuario = this.repository.findByEmail(email);

        validarUsuario(usuario);

        usuario.setTokenAcessoUnico(UUID.randomUUID());

        this.repository.save(usuario);

        this.dispararEmail(usuario, nomeSistema, MessageUsuarioConstant.TEMPLATE_RECUPERAR_SENHA_FTLH, MessageUsuarioConstant.ACAO_RECUPERAR);

        var requisicao = new RequisicaoRecuperarSenha();
        requisicao.setHoraRequisicao(LocalDateTime.now());
        requisicao.setEmail(email);

        this.recuperacaoService.save(requisicao);
    }

    @Override
    public void updatePassword(UUID id, UsuarioCadastroSenhaRequestDto request) {
        var usuario = this.consultaService.findByIdModel(id);
        this.salvarSenha(usuario, null, request, true);
    }

    @Override
    public void createPassword(UUID id, UUID token, @Valid UsuarioCadastroSenhaRequestDto request) {
        var usuario = this.consultaService.findByIdModel(id);
        this.salvarSenha(usuario, token, request, false);
    }

    @Override
    public void recoverPassword(UUID id, UUID token, UsuarioCadastroSenhaRequestDto request) {
        var usuario = this.consultaService.findByIdModel(id);
        salvarSenha(usuario, token, request, false);
    }

    @Override
    public void validarCodigo(UUID id, String codigo) {
        var usuario = this.consultaService.findByIdModel(id);

        if(usuario.getCodigoVerificacao() != null) {
            LocalDateTime dataAtual     = LocalDateTime.now();
            LocalDateTime dataExpiracao = usuario.getCodigoVerificacao().getDataExpiracao();

            if (!dataAtual.isBefore(dataExpiracao)) {
                throw new NegocioException(MessageCommonsContanst.MENSAGEM_CODIGO_EXPIRADO);
            }

            if(!usuario.getCodigoVerificacao().getCodigo().equals(codigo)) {
                throw new NegocioException(MessageCommonsContanst.MENSAGEM_CODIGO_INVALIDO);
            }
        }else {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_CODIGO_NAO_INFORMADO);
        }
    }

    private void salvarSenha(Usuario usuario, UUID token, UsuarioCadastroSenhaRequestDto request, boolean validarSenha) {

        Password.equalsPassword(request.getSenha(), request.getConfirmarSenha());

        if(token != null && !token.equals(usuario.getTokenAcessoUnico())) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_TOKEN_INCORRETO);
        }

        var senhaEncriptada = Password.encripty(request.getSenha());

        var ultimasSenhas = usuario.getUltimasSenhas();

        var gson = new Gson();

        List<String> list = ultimasSenhas != null ? gson.fromJson(ultimasSenhas, (Type) List.class) : new ArrayList<>();

        validarUltimasSenhas(request, validarSenha, list);

        validaSenhasCadastradas(list);

        list.add(senhaEncriptada);

        usuario.setUltimasSenhas(gson.toJson(list));
        usuario.setSenha(senhaEncriptada);
        usuario.setTokenAcessoUnico(null);
        usuario.setCodigoVerificacao(null);
        usuario.setDataUltimaAlteracaoSenha(LocalDateTime.now());

        this.repository.save(usuario);
    }

    private void validarUltimasSenhas(UsuarioCadastroSenhaRequestDto request, boolean validarSenha, List<String> list) {
        if(validarSenha) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            list.forEach(s -> {
                if(encoder.matches(request.getSenha(), s)) {
                    throw new NegocioException(MessageCommonsContanst.MENSAGEM_SENHA_JA_UTILIZADA);
                }
            });
        }
    }

    private void validaSenhasCadastradas(List<String> list) {
        var condicaoAcesso = this.condicaoAcessoRepository.findAll();
        var totalSenhasCadastradas = 0;

        if(!condicaoAcesso.isEmpty()) {
            totalSenhasCadastradas = condicaoAcesso.get(0).getSenhasCadastradas();
        }

        if(!condicaoAcesso.isEmpty() && totalSenhasCadastradas == list.size()) {
            list.remove(0);
        }
    }

    private void validarUsuario(Usuario usuario) {
        if(!Objects.nonNull(usuario)) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_EMAIL_NAO_CADASTRADO);
        }

        if(usuario.getTipoUsuario().equals(TipoUsuario.INTERNO)) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_INTERNO);
        }

        if(usuario.getSituacao().equals(SituacaoUsuario.BLOQUEADA)) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_BLOQUEADO);
        }else if(usuario.getSituacao().equals(SituacaoUsuario.INATIVA)) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVO);
        }

        LocalDateTime limiteTempo = LocalDateTime.now().minusHours(2);
        LocalDateTime agora       = LocalDateTime.now();
        List<RequisicaoRecuperarSenha> requests = this.recuperacaoService.getRequestByEmail(usuario.getEmail());
        List<RequisicaoRecuperarSenha> solicitacoes = requests.stream()
                .filter(req -> req.getHoraRequisicao().isAfter(limiteTempo) && req.getHoraRequisicao().isBefore(agora))
                .toList();

        if(solicitacoes.size() >= MessageUsuarioConstant.TENTATIVA_MAXIMA_SOLICITACAO) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_MUITAS_SOLICITACOES);
        }
    }

    private void dispararEmail(Usuario usuario, String nomeSistema, String template, String acao) {
        Map<String, Object> params = new HashMap<>();
        params.put("nomeUsuario", usuario.getNome());
        params.put("nomeSistema", nomeSistema);

        Map<String, String> dataEmail = getDataEmail(acao, nomeSistema, usuario);

        var urlAmbiente = "";

        if(nomeSistema.equals(MessageUsuarioConstant.PORTAL_DIVIDA_ATIVA)) {
            urlAmbiente = this.urlPortalDivida;
        }else if(nomeSistema.equals(MessageUsuarioConstant.PORTAL_DAS_ORIGENS)){
            urlAmbiente = this.urlPortalOrigens;
        }else {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_SISTEMA_INEXISTENTE);
        }

        params.put("urlCadastro", urlAmbiente + "autenticacao/" + dataEmail.get(MessageUsuarioConstant.URL));
        params.put("enderecoSistema", urlAmbiente + "autenticacao");

        var codigo = usuario.getCodigoVerificacao() != null ? usuario.getCodigoVerificacao().getCodigo() : null;
        if(codigo != null) {
            params.put("codigo", codigo);
        }

        try {
            enviarEmailService.enviarEmailTemplate(dataEmail.get(MessageUsuarioConstant.ASSUNTO), usuario.getEmail(), template, params);
        } catch (IOException | MessagingException | TemplateException e) {
            throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_ENVIAR_EMAIL);
        }
    }

    private Map<String, String> getDataEmail(String acao, String nomeSistema, Usuario usuario) {
        var assunto  = "";
        var url	     = "";

        if(acao.equals(MessageUsuarioConstant.ACAO_RECUPERAR)) {
            assunto = "Recuperação de Senha de Acesso ao ";
            url = "recuperar-senha/" + usuario.getId() + "?token=" + usuario.getTokenAcessoUnico();
        } else if(acao.equals(MessageUsuarioConstant.ACAO_ALTERAR)) {
            assunto = "Alteração de Senha de Acesso ao ";
            url = "alterar-senha/" + usuario.getId();
        }

        return Map.of(MessageUsuarioConstant.ASSUNTO, assunto + nomeSistema, MessageUsuarioConstant.URL, url);
    }

}
