package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;

class HistoricoAtualizacaoModuloToDtoMapperTest {

    @Test
    void test() {
        var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-modulo").processarAuditoria(AuditoriaDtoTest.getAuditoriaModuloDto_dadosAntigoNull());
        assertNotNull(retorno);
        assertEquals(0, retorno.size());
    }

}
