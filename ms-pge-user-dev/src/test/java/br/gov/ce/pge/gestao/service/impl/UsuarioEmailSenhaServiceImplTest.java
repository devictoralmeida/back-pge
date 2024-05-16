package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDtoTest;
import br.gov.ce.pge.gestao.entity.CodigoVerificacao;
import br.gov.ce.pge.gestao.entity.CondicaoAcessoTest;
import br.gov.ce.pge.gestao.entity.RequisicaoRecuperarSenhaTest;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.repository.CondicaoAcessoRepository;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({ MockitoExtension.class })
class UsuarioEmailSenhaServiceImplTest {

    @Mock
    private UsuarioConsultaService consultaService;

    @Mock
    private CondicaoAcessoRepository condicaoAcessoRepository;

    @InjectMocks
    private UsuarioEmailSenhaServiceImpl usuarioService;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private EnviarEmailServiceImpl enviarEmailService;

    @Mock
    private RequisicaoRecuperarSenhaServiceImpl requisicaoService;

    @Mock
    private CodigoVerificacaoServiceImpl codigoService;

    @Test
    void test_criar_senha() {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());
        when(condicaoAcessoRepository.findAll()).thenReturn(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));

        usuarioService.createPassword(id, UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"), UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenha());

        verify(consultaService).findByIdModel(id);

    }


    @Test
    void test_criar_senha_error() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.createPassword(id, id, UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenha());
        });

        assertEquals("Erro ao salvar a senha, entre em contato com o e-mail cti@pge.ce.gov.br.", error.getMessage());
    }

    @Test
    void test_recuperar_senha() {

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());
        when(condicaoAcessoRepository.findAll()).thenReturn(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));

        usuarioService.recoverPassword(UUID.fromString("4931259c-2f03-4542-9f58-97a26f64d3d8"), UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"), UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenha());

    }

    @Test
    void test_sendrecovery_portal_divida_email_sucesso() throws MessagingException, TemplateException, IOException {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());
        doNothing().when(enviarEmailService).enviarEmailTemplate(any(), any(), any(), any());

        usuarioService.sendRecoveryEmail("email@gov.br", "Portal da Dívida Ativa");
    }

    @Test
    void test_sendrecovery_portal_origens_email_sucesso() throws MessagingException, TemplateException, IOException {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());
        doNothing().when(enviarEmailService).enviarEmailTemplate(any(), any(), any(), any());

        usuarioService.sendRecoveryEmail("email@gov.br", "Portal das Origens");
    }

    @Test
    void test_sendrecovery_error() throws MessagingException, TemplateException, IOException {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());
        doThrow(new IOException("error")).when(enviarEmailService).enviarEmailTemplate(any(), any(), any(), any());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.sendRecoveryEmail("email@gov.br", "Portal da Dívida Ativa");
        });

        assertEquals("Erro ao enviar o email!", exception.getMessage());
    }

    @Test
    void test_sendrecovery_validacao_usuario_error() {

        when(repository.findByEmail(any())).thenReturn(null);

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.sendRecoveryEmail("email@gov.br", "Portal da Dívida Ativa");
        });

        assertEquals("E-mail não cadastrado! Verifique se há algum erro de digitação. Caso o seu e-mail esteja correto, entre em contato com o administrador do sistema.", exception.getMessage());
    }

    @Test
    void test_sendrecovery_validacao_tipo_usuario_error() {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuarioInterno());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.sendRecoveryEmail("email@gov.br", "Portal da Dívida Ativa");
        });

        assertEquals("Para recuperar sua senha, entre em contato com o e-mail cti@pge.ce.gov.br.", exception.getMessage());
    }

    @Test
    void test_sendrecovery_validacao_tipo_inativo_usuario_error() {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuarioUpdate());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.sendRecoveryEmail("email@gov.br", "Portal da Dívida Ativa");
        });

        assertEquals("Não foi possível prosseguir com a recuperação de senha. Para mais informações, entre em contato com o e-mail cti@pge.ce.gov.br.", exception.getMessage());
    }

    @Test
    void test_sendrecovery_validacao_situacao_bloqueada_usuario_error() {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuarioBloqueada());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.sendRecoveryEmail("email@gov.br", "Portal da Dívida Ativa");
        });

        assertEquals("Não foi possível prosseguir com a recuperação de senha.", exception.getMessage());
    }

    @Test
    void test_sendrecovery_validacao_limite_requests_error() {

        when(repository.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());
        when(requisicaoService.getRequestByEmail(any())).thenReturn(RequisicaoRecuperarSenhaTest.getListRequisicao());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.sendRecoveryEmail("teste@pge.ce.gov.br", "Portal da Dívida Ativa");
        });

        assertEquals("Você já solicitou a recuperação de senha. Verifique a caixa de entrada e de SPAM do seu e-mail. Caso não tenha recebido, entre em contato com o e-mail cti@pge.ce.gov.br.", exception.getMessage());
    }

    @Test
    void test_alterar_senha() {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenha());
        when(condicaoAcessoRepository.findAll()).thenReturn(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));

        usuarioService.updatePassword(id, UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenha());

        verify(consultaService).findByIdModel(id);

    }

    @Test
    void test_alterar_senha_error() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenha());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.updatePassword(id, UsuarioCadastroSenhaRequestDtoTest.getUsuarioAlterarSenhaJaInformada());
        });

        assertEquals("A senha informada já foi utilizada anteriormente.", error.getMessage());
    }

    @Test
    void test_alterar_senha_error_codigo_nao_informado() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.validarCodigo(id, "123456");
        });

        assertEquals("Código não informado!", error.getMessage());
    }

    @Test
    void test_alterar_senha_error_codigo_expirado() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenhaCodigoExpirado());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.validarCodigo(id, "123456");
        });

        assertEquals("Código informado expirado!", error.getMessage());
    }

    @Test
    void test_alterar_senha_error_codigo_invalido() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenhaCodigoInvalido());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.validarCodigo(id, "123456");
        });

        assertEquals("Código informado inválido!", error.getMessage());
    }

    @Test
    void test_send_email_alterar_senha() {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        var codigo = new CodigoVerificacao();
        codigo.setCodigo("123456");
        codigo.setDataExpiracao(LocalDateTime.now().plus(30, ChronoUnit.MINUTES));

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenha());
        when(codigoService.save(any())).thenReturn(codigo);

        usuarioService.sendChangeEmail(UsuarioCadastroSenhaRequestDtoTest.getUsuarioAlterarSenhaJaInformada(), id, "Portal da Dívida Ativa");

        verify(consultaService).findByIdModel(id);

    }

    @Test
    void test_send_email_alterar_error_senha_incorreta() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenha());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.sendChangeEmail(UsuarioCadastroSenhaRequestDtoTest.getUsuarioAlterarSenhaJaInformada(), id, "Sistema Inexistente");
        });

        assertEquals("Sistema não existe!", error.getMessage());
    }

    @Test
    void test_send_email_alterar_error_sistema_nao_existe() {
        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioAlterarSenha());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.sendChangeEmail(UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenha(), id, "Portal Da Dívida Ativa");
        });

        assertEquals("Senha incorreta! Verifique o conteúdo digitado e tente novamente.", error.getMessage());
    }

}
