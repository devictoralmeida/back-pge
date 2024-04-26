package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoUsuario;

public class UsuarioTest {

    public static Usuario getUsuario() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.ATIVA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("orgao teste");
        model.setUsuarioRede("teste");
        model.setTipoUsuario(TipoUsuario.EXTERNO);
        model.setTokenAcessoUnico(UUID.fromString("f9a2b13d-284d-4e2f-adea-d044aff51b4a"));
        model.setDataAceiteTermoPortalDivida(LocalDateTime.now());
        model.setDataAceiteTermoPortalOrigens(LocalDateTime.now());

        return model;
    }

    public static Usuario getUsuarioBloqueada() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.BLOQUEADA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("orgao teste");
        model.setTipoUsuario(TipoUsuario.EXTERNO);
        model.setTokenAcessoUnico(UUID.fromString("f9a2b13d-284d-4e2f-adea-d044aff51b4a"));

        return model;
    }

    public static Usuario getUsuarioInterno() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.ATIVA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("Procuradoria-Geral do Estado do Ceará");
        model.setTipoUsuario(TipoUsuario.INTERNO);
        model.setTokenAcessoUnico(UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"));
        model.setUltimasSenhas("[\"$2a$10$bk8T21mMx8f589dEVtDdwOd289uVi1n8DfTEdF/hLbU6MOhIESjOm\"]");
        model.setUsuarioRede("outro teste");
        model.setDataUltimoAcesso(LocalDateTime.now());

        return model;
    }

    public static Usuario getUsuarioUpdate() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.INATIVA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("orgao teste");
        model.setTipoUsuario(TipoUsuario.EXTERNO);

        return model;
    }

    public static Usuario getUsuarioAlterarSenha() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.ATIVA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("Procuradoria-Geral do Estado do Ceará");
        model.setTipoUsuario(TipoUsuario.INTERNO);
        model.setTokenAcessoUnico(UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"));
        model.setUltimasSenhas("[\"$2a$10$bk8T21mMx8f589dEVtDdwOd289uVi1n8DfTEdF/hLbU6MOhIESjOm\"]");
        model.setSenha("$2a$10$bk8T21mMx8f589dEVtDdwOd289uVi1n8DfTEdF/hLbU6MOhIESjOm");

        var codigo = new CodigoVerificacao();
        codigo.setCodigo("123456");
        codigo.setDataExpiracao(LocalDateTime.now().plus(30, ChronoUnit.MINUTES));
        model.setCodigoVerificacao(codigo);

        return model;
    }

    public static Usuario getUsuarioAlterarSenhaCodigoExpirado() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.ATIVA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("Procuradoria-Geral do Estado do Ceará");
        model.setTipoUsuario(TipoUsuario.INTERNO);
        model.setTokenAcessoUnico(UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"));
        model.setUltimasSenhas("[\"$2a$10$bk8T21mMx8f589dEVtDdwOd289uVi1n8DfTEdF/hLbU6MOhIESjOm\"]");

        var codigo = new CodigoVerificacao();
        codigo.setCodigo("123456");
        codigo.setDataExpiracao(LocalDateTime.now().minus(30, ChronoUnit.MINUTES));
        model.setCodigoVerificacao(codigo);

        return model;
    }

    public static Usuario getUsuarioAlterarSenhaCodigoInvalido() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.ATIVA);
        model.setNome("teste usuario");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("01234567890");
        model.setArea("area teste");
        model.setOrgao("Procuradoria-Geral do Estado do Ceará");
        model.setTipoUsuario(TipoUsuario.INTERNO);
        model.setTokenAcessoUnico(UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"));
        model.setUltimasSenhas("[\"$2a$10$bk8T21mMx8f589dEVtDdwOd289uVi1n8DfTEdF/hLbU6MOhIESjOm\"]");

        var codigo = new CodigoVerificacao();
        codigo.setCodigo("123897");
        codigo.setDataExpiracao(LocalDateTime.now().plus(10, ChronoUnit.MINUTES));
        model.setCodigoVerificacao(codigo);

        return model;
    }

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setTipoUsuario(TipoUsuario.EXTERNO);
        usuario.setCpf("12345678901");
        usuario.setNome("John Doe");
        usuario.setOrgao("Some Organization");
        usuario.setEmail("john.doe@example.com");
        usuario.setSituacao(SituacaoUsuario.INATIVA);
    }

    @Test
    public void test_toString_mapper() {
        assertDoesNotThrow(() -> {
            String jsonString = usuario.toStringMapper();
            assertNotNull(jsonString);
            assertTrue(jsonString.contains("\"id\":"));
            assertTrue(jsonString.contains("\"tipoUsuario\":"));
            assertTrue(jsonString.contains("\"cpf\":"));
            assertTrue(jsonString.contains("\"nome\":"));
            assertTrue(jsonString.contains("\"orgao\":"));
            assertTrue(jsonString.contains("\"email\":"));
            assertTrue(jsonString.contains("\"situacao\":"));
        });
    }

    @Test
    public void test_getter_and_setters() {
        assertEquals(usuario.getId(), usuario.getId());
        assertEquals(usuario.getTipoUsuario(), TipoUsuario.EXTERNO);
        assertEquals(usuario.getCpf(), "12345678901");
        assertEquals(usuario.getNome(), "John Doe");
        assertEquals(usuario.getOrgao(), "Some Organization");
        assertEquals(usuario.getEmail(), "john.doe@example.com");
        assertEquals(usuario.getSituacao(), SituacaoUsuario.INATIVA);

        assertDoesNotThrow(() -> {
            usuario.setUsuarioRede(null);
            usuario.setArea(null);
            usuario.setSenha(null);
        });
        assertNull(usuario.getUsuarioRede());
        assertNull(usuario.getArea());
        assertNull(usuario.getSenha());
    }

    public static Usuario getColaboradorInterno() {
        var model = new Usuario();
        model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
        model.setPerfisAcessos(PerfilAcessoTest.getListaPerfilAcesso());
        model.setSistemas(SistemaTest.getListSistema());
        model.setSituacao(SituacaoUsuario.ATIVA);
        model.setNome("Jose");
        model.setEmail("teste@pge.ce.gov.br");
        model.setCpf("05459813360");
        model.setArea("TI 2");
        model.setOrgao("Procuradoria-Geral do Estado do Ceará");
        model.setTipoUsuario(TipoUsuario.INTERNO);
        model.setTokenAcessoUnico(UUID.fromString("d7431940-2029-4a90-abe7-89e8b49a5b70"));
        model.setUltimasSenhas("[\"$2a$10$bk8T21mMx8f589dEVtDdwOd289uVi1n8DfTEdF/hLbU6MOhIESjOm\"]");
        model.setUsuarioRede("teste");

        return model;
    }

}
