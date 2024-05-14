package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import br.gov.ce.pge.gestao.dto.response.*;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.auditoria.Auditoria;

public class AuditoriaTest {


    public static AuditoriaPerfilAcessoDto getAuditoriaPerfilAcesso() throws JsonProcessingException {

        var auditoria = new AuditoriaPerfilAcessoDto();
        auditoria.setDadosAntigos(PerfilAcessoTest.getPerfilAcesso().toStringMapper());
        auditoria.setNome(PerfilAcessoTest.getPerfilAcessoUpdate().getNome());
        auditoria.setSituacao(PerfilAcessoTest.getPerfilAcessoUpdate().getSituacao());
        auditoria.setIdsAdicionados("");
        auditoria.setIdsRemovidos("");
        auditoria.setIdsSistemaAdicionados("");
        auditoria.setIdsSistemaRemovidos("");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");


        return auditoria;
    }

    public static AuditoriaPermissaoDto getAuditoriaPermissao() throws JsonProcessingException {
        var auditoria = new AuditoriaPermissaoDto();
        auditoria.setDadosAntigos(PermissaoTest.getPermissao().toStringMapper());
        auditoria.setNome("cadastrar update");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");
        auditoria.setSituacao(Situacao.INATIVA);
        auditoria.setId(PermissaoTest.getPermissao().getId().toString());
        return auditoria;
    }

    public static AuditoriaModuloDto getAuditoriaModulo() throws JsonProcessingException {
        var auditoria = new AuditoriaModuloDto();
        auditoria.setDadosAntigos(ModuloTest.getModulo().toStringMapper());
        auditoria.setNome("origem debito update");
        auditoria.setSituacao(Situacao.INATIVA);
        auditoria.setIdsAdicionados("");
        auditoria.setIdsRemovidos("");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");
        return auditoria;
    }

    public static AuditoriaSistemaDto getAuditoriaSistema() throws JsonProcessingException {
        var auditoria = new AuditoriaSistemaDto();
        auditoria.setDadosAntigos(SistemaTest.getSistema().toStringMapper());
        auditoria.setNome("portal divida ativa update");
        auditoria.setSituacao(Situacao.ATIVA);
        auditoria.setIdsAdicionados("");
        auditoria.setIdsRemovidos("");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");
        return auditoria;
    }

    public static AuditoriaUsuarioDto getAuditoriaUsuario() throws JsonProcessingException {

        var auditoria = new AuditoriaUsuarioDto();
        auditoria.setDadosAntigos(UsuarioTest.getUsuario().toStringMapper());
        auditoria.setNome(UsuarioTest.getUsuarioUpdate().getNome());
        auditoria.setSituacaoUsuario(UsuarioTest.getUsuarioUpdate().getSituacao());
        auditoria.setArea(UsuarioTest.getUsuarioUpdate().getArea());
        auditoria.setOrgao(UsuarioTest.getUsuarioUpdate().getOrgao());
        auditoria.setCpf(UsuarioTest.getUsuarioUpdate().getCpf());
        auditoria.setEmail(UsuarioTest.getUsuarioUpdate().getEmail());
        auditoria.setTipoUsuario(UsuarioTest.getUsuarioUpdate().getTipoUsuario());
        auditoria.setUsuarioRede(UsuarioTest.getUsuarioUpdate().getUsuarioRede());
        auditoria.setIdsPerfisAdicionados("");
        auditoria.setIdsPerfisRemovidos("");
        auditoria.setIdsSistemaAdicionados("");
        auditoria.setIdsSistemaRemovidos("");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");


        return auditoria;
    }

    public static AuditoriaCondicaoAcessoDto getAuditoriaCondicaoAcesso() throws JsonProcessingException {

        var auditoria = new AuditoriaCondicaoAcessoDto();
        auditoria.setDadosAntigos(CondicaoAcessoTest.getCondicaoAcesso().toStringMapper());
        auditoria.setAlteracaoSenha(CondicaoAcessoTest.getCondicaoAcessoUpdate().getAlteracaoSenha());
        auditoria.setBloqueioAutomatico(CondicaoAcessoTest.getCondicaoAcessoUpdate().getBloqueioAutomatico());
        auditoria.setSenhasCadastradas(CondicaoAcessoTest.getCondicaoAcessoUpdate().getSenhasCadastradas());
        auditoria.setTentativasInvalidas(CondicaoAcessoTest.getCondicaoAcessoUpdate().getTentativasInvalidas());
        auditoria.setEncerramentoSessao(CondicaoAcessoTest.getCondicaoAcessoUpdate().getEncerramentoSessao());
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");


        return auditoria;
    }


    public static AuditoriaTermoCondicaoDto getAuditoriaTermos() throws JsonProcessingException {

        var auditoria = new AuditoriaTermoCondicaoDto();
        auditoria.setVersao("0.1");
        auditoria.setConteudo("conteúdo teste update");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");

        return auditoria;
    }
    
    
    public static List<AuditoriaDto> getListAuditoriaPerfilAcesso() throws JsonProcessingException {
        return List.of(getAuditoriaPerfilAcesso());
    }

    public static List<AuditoriaDto> getListAuditoriaPermissao() throws JsonProcessingException {
        return List.of(getAuditoriaPermissao());
    }

    public static List<AuditoriaDto> getListAuditoriaModulo() throws JsonProcessingException {
        return List.of(getAuditoriaModulo());
    }

    public static List<AuditoriaDto> getListAuditoriaSistema() throws JsonProcessingException {
        return List.of(getAuditoriaSistema());
    }

    public static List<AuditoriaDto> getListAuditoriaUsuario() throws JsonProcessingException {
        return List.of(getAuditoriaUsuario());
    }

    public static List<AuditoriaDto> getListAuditoriaCondicaoAcesso() throws JsonProcessingException {
        return List.of(getAuditoriaCondicaoAcesso());
    }

    public static List<AuditoriaDto> getListAuditoriaTermo() throws JsonProcessingException {
        return List.of(getAuditoriaTermos());
    }

    public static List<AuditoriaDto> getMultipleListAuditoriaTermo() throws JsonProcessingException {

        var auditoria = new AuditoriaTermoCondicaoDto();
        auditoria.setVersao("0.1");
        auditoria.setConteudo("conteúdo teste update");
        auditoria.setDataMovimento("2023-11-21 10:57:31.091234");
        auditoria.setNomeUsuario("anônimo");

        var auditoriaDois = new AuditoriaTermoCondicaoDto();
        auditoriaDois.setVersao("0.2");
        auditoriaDois.setConteudo("conteúdo teste");
        auditoriaDois.setDataMovimento("2023-11-22 10:57:31.091234");
        auditoriaDois.setNomeUsuario("anônimo");

        return Arrays.asList(auditoriaDois, auditoria);
    }
    
    @Test
    public void test_auditoria() {
        var auditoria = new Auditoria();
        auditoria.setDadosAntigos("{id: 1, nome: teste}");
        LocalDateTime dataMovimento = LocalDateTime.now();
        auditoria.setDataMovimento(dataMovimento);
        auditoria.setTimestamp(11);
        auditoria.setNomeUsuario("Adla");
        auditoria.setId(2l);

        assertEquals("{id: 1, nome: teste}", auditoria.getDadosAntigos());
        assertEquals(2l, auditoria.getId());
        assertEquals("Adla", auditoria.getNomeUsuario());
        assertEquals(11, auditoria.getTimestamp());
        assertEquals(dataMovimento, auditoria.getDataMovimento());
        assertNotNull(auditoria.toString());
    }
}
