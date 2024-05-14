package br.gov.ce.pge.gestao.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;

import javax.mail.MessagingException;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDtoTest;
import br.gov.ce.pge.gestao.entity.PerfilAcessoTest;
import br.gov.ce.pge.gestao.entity.SistemaTest;
import br.gov.ce.pge.gestao.entity.TermoCondicaoTest;
import br.gov.ce.pge.gestao.entity.Usuario;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.repository.UsuarioRepository;
import br.gov.ce.pge.gestao.service.SistemaService;
import br.gov.ce.pge.gestao.service.TermoCondicaoService;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import freemarker.template.TemplateException;

@ExtendWith({ MockitoExtension.class })
public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioConsultaService consultaService;

    @Mock
    private SistemaService sistemaService;

    @Mock
    private PerfilAcessoServiceImpl perfilAcessoService;
    
    @Mock
    private TermoCondicaoService termoService;

    @Mock
    private EnviarEmailServiceImpl enviarEmailService;

    @Mock
    private LdapServiceImpl ldapService;

    @Mock
    private PortalColaboradorServiceImpl portalColaboradorService;

    @Test
    void save_sucesso() throws MessagingException, TemplateException, IOException {

        when(consultaService.findByCpf(any())).thenReturn(null);
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
        when(termoService.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicao());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());
        doNothing().when(enviarEmailService).enviarEmailTemplate(any(), any(), any(), any());

        usuarioService.save(UsuarioRequestDtoTest.getRequest());

        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void save_sucesso_sistema_portal_origens() throws MessagingException, TemplateException, IOException {

        when(consultaService.findByCpf(any())).thenReturn(null);
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistemaPortalOrigens());
        when(termoService.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicao());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());
        doNothing().when(enviarEmailService).enviarEmailTemplate(any(), any(), any(), any());
        when(ldapService.userExists(any())).thenReturn(true);

        usuarioService.save(UsuarioRequestDtoTest.getRequestUsuarioInterno());

        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void save_exception_email() throws MessagingException, TemplateException, IOException {

        when(consultaService.findByCpf(any())).thenReturn(null);
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
        when(termoService.findByNomeSistema()).thenReturn(TermoCondicaoTest.getListTermoCondicao());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());

        doThrow(new IOException("error")).when(enviarEmailService).enviarEmailTemplate(any(), any(), any(), any());

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            usuarioService.save(UsuarioRequestDtoTest.getRequest());
        });

        assertEquals("Erro ao enviar o email!", exception.getMessage());
    }
    
    @Test
    void save_error_usuario_existente() {

       when(consultaService.findByCpf(any())).thenReturn(UsuarioTest.getUsuario());

       Exception error = assertThrows(NegocioException.class, () -> {
    	   usuarioService.save(UsuarioRequestDtoTest.getRequest());
       });

       assertEquals("O CPF já foi cadastrado anteriormente!", error.getMessage());

       verify(repository, times(0)).save(any(Usuario.class));
   }

    @Test
    void save_interno_error() {

        when(consultaService.findByCpf(any())).thenReturn(null);
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());
        when(ldapService.userExists(any())).thenReturn(true);

        Exception error = assertThrows(NegocioException.class, () -> {
        	UsuarioRequestDto request = UsuarioRequestDtoTest.getRequest();
        	request.setOrgao("PGE");
        	request.setTipoUsuario(TipoUsuario.INTERNO);
			usuarioService.save(request);
        });

 		assertEquals("Para usuário Interno o órgão deve ser Procuradoria-Geral do Estado do Ceará.", error.getMessage());

        verify(repository, times(0)).save(any(Usuario.class));
    }
    
    @Test
    void save_error() {

        when(consultaService.findByCpf(any())).thenReturn(null);
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistemaOther());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());

        Exception error = assertThrows(NegocioException.class, () -> {
        	usuarioService.save(UsuarioRequestDtoTest.getRequest());
        });

 		assertEquals("Portal não faz parte dos Perfis Selecionados", error.getMessage());

        verify(repository, times(0)).save(any(Usuario.class));
    }

    @Test
    void save_interno_ldap_error() {
        when(consultaService.findByCpf(any())).thenReturn(null);
        when(ldapService.userExists(any())).thenReturn(false);

        Exception error = assertThrows(NegocioException.class, () -> {
            UsuarioRequestDto request = UsuarioRequestDtoTest.getRequest();
            request.setOrgao("PGE");
            request.setTipoUsuario(TipoUsuario.INTERNO);
            usuarioService.save(request);
        });

        assertEquals("Usuário de rede não encontrado! Certifique-se que o usuário esteja correto ou entre em contato com o setor de TI.", error.getMessage());

        verify(repository, times(0)).save(any(Usuario.class));
    }

    @Test
    void delete_sucesso() {

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        usuarioService.delete(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));

        verify(repository, times(1)).delete(any());
    }

    @Test
    void delete_error() {

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.delete(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        });

        assertEquals(MessageCommonsContanst.MENSAGEM_USUARIO_COM_ACESSO, error.getMessage());
    }


    @Test
    void update_sucesso() throws Exception {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());
        when(ldapService.userExists(any())).thenReturn(true);

        usuarioService.update(id, UsuarioRequestDtoTest.getRequestUsuarioInterno());

        verify(repository, times(1)).save(any());
    }

    @Test
    void update_sucesso_objetos_iguais() throws Exception {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());

        usuarioService.update(id, UsuarioRequestDtoTest.getRequestObjetoIgual());

    }

    @Test
    void update_sucesso_outro_email() throws Exception {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());
        when(consultaService.findByEmail(any())).thenReturn(null);

        usuarioService.update(id, UsuarioRequestDtoTest.getRequestOther());

        verify(repository, times(1)).save(any());
    }
    
    @Test
    void update_sucesso_other_cpf() throws Exception {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());
        when(sistemaService.findById(any())).thenReturn(SistemaTest.getSistema());
        when(consultaService.findByCpf(any())).thenReturn(null);
        when(perfilAcessoService.findByIdModel(any())).thenReturn(PerfilAcessoTest.getPerfilAcesso());

        UsuarioRequestDto request = UsuarioRequestDtoTest.getRequest();
        request.setCpf("12345678910");
		usuarioService.update(id, request);

        verify(repository, times(1)).save(any());
    }

    
    @Test
    void update_error() {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");
        
        when(consultaService.findByCpf(any())).thenReturn(UsuarioTest.getUsuario());
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        Exception error = assertThrows(NegocioException.class, () -> {
        	usuarioService.update(id, UsuarioRequestDtoTest.getRequestOther());
        });

 		assertEquals("O CPF já foi cadastrado anteriormente!", error.getMessage());

        verify(repository, times(0)).save(any());
    }

    @Test
    void update_error_email() {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(consultaService.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.update(id, UsuarioRequestDtoTest.getRequestOther());
        });

        assertEquals("O email já foi cadastrado anteriormente!", error.getMessage());

        verify(repository, times(0)).save(any());
    }

    @Test
    void update_error_usuario_rede_existente() {

        UUID id = UUID.fromString("c4095434-f704-4209-be74-3d42d519d438");

        when(repository.findByUsuarioRede(any())).thenReturn(UsuarioTest.getUsuarioInterno());
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuarioInterno());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.update(id, UsuarioRequestDtoTest.getRequestUsuarioInterno());
        });

        assertEquals("O usuário de rede já foi cadastrado anteriormente!", error.getMessage());

        verify(repository, times(0)).save(any());
    }


    @Test
    void save_error_email_existente() {

        when(consultaService.findByEmail(any())).thenReturn(UsuarioTest.getUsuario());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.save(UsuarioRequestDtoTest.getRequest());
        });

        assertEquals("O email já foi cadastrado anteriormente!", error.getMessage());

        verify(repository, times(0)).save(any(Usuario.class));
    }

    @Test
    void bloqueio_usuario_test() {
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        usuarioService.bloquearUsuario(any());

        verify(repository, times(1)).save(any());
    }

    @Test
    void set_ultimo_acesso_usuario_test() {
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        usuarioService.ultimoAcesso(any());

        verify(repository, times(1)).save(any());
    }

    @Test
    void test_aceitar_termo_portal_divida() {
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        usuarioService.aceitarTermo(any(), "Portal da Dívida Ativa");

        verify(repository, times(1)).save(any());
    }

    @Test
    void test_aceitar_termo_portal_origem() {
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        usuarioService.aceitarTermo(any(), "Portal das Origens");

        verify(repository, times(1)).save(any());
    }

    @Test
    void test_aceitar_termo_sistema_inexistente() {
        when(consultaService.findByIdModel(any())).thenReturn(UsuarioTest.getUsuario());

        Exception error = assertThrows(NegocioException.class, () -> {
            usuarioService.aceitarTermo(any(), "Sistema Inexistente");
        });

        assertEquals("Sistema não existe!", error.getMessage());
    }
}
