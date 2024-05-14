package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.FaseDividaTest;
import br.gov.ce.pge.gestao.entity.OrigemDebitoTest;
import br.gov.ce.pge.gestao.entity.ProdutoServicoTest;
import br.gov.ce.pge.gestao.entity.TipoReceitaTest;
import br.gov.ce.pge.gestao.enums.Situacao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AuditoriaDtoTest {

    @Test
    public void test_criacao_auditoria() throws JsonProcessingException {
        AuditoriaDto dto = getAuditoriaOrigemDebito();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals("anonimo", dto.getNomeUsuario());
        Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
    }

    @Test
    public void test_getters_setters() {
        AuditoriaDto dto = getAuditoriaOrigemDebito();

        dto.setNomeUsuario("anonimo2");
        Assertions.assertEquals("anonimo2", dto.getNomeUsuario());

        dto.setSituacao(Situacao.INATIVA);
        Assertions.assertEquals(Situacao.INATIVA, dto.getSituacao());
    }

    public static AuditoriaDto getAuditoriaOrigemDebito() {
        var auditoria = new AuditoriaOrigemDebitoDto();
        auditoria.setDadosAntigos(OrigemDebitoTest.getOrigem().toString());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);
        return auditoria;
    }

    public static AuditoriaDto getAuditoriaTipoReceita() throws JsonProcessingException {
        var auditoria = new AuditoriaTipoReceitaDto();
        auditoria.setDadosAntigos(TipoReceitaTest.getTipoReceita().toStringMapper());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);
        return auditoria;
    }

    public static AuditoriaDto getAuditoriaProdutoServico() throws JsonProcessingException {
        var auditoria = new AuditoriaTipoReceitaDto();
        auditoria.setDadosAntigos(ProdutoServicoTest.getProdutoServico().toStringMapper());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);
        return auditoria;
    }

    public static AuditoriaDto getAuditoriaFaseDivida() throws JsonProcessingException {
        var auditoria = new AuditoriaFaseDividaDto();
        auditoria.setDadosAntigos(FaseDividaTest.getFaseDivida().toStringMapper());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setDataMovimento(LocalDateTime.now().toString());
        auditoria.setNomeUsuario("anonimo");
        auditoria.setSituacao(Situacao.ATIVA);
        return auditoria;
    }

}
