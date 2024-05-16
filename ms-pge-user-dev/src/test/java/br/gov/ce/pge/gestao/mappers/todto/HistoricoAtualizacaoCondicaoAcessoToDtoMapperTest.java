package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoricoAtualizacaoCondicaoAcessoToDtoMapperTest {

    @Test
    void test_dados_antigos_null() {
        var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-condicao-acesso").processarAuditoria(AuditoriaDtoTest.getAuditoriaCondicaoAcessoDto_dadosAntigoNull());
        assertNotNull(retorno);
        assertEquals(0, retorno.size());
    }

    @Test
    void test_dados_antigos() throws JsonProcessingException {
        var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-condicao-acesso").processarAuditoria(AuditoriaDtoTest.getAuditoriaCondicaoAcessoDto_dadosAntigos());
        assertNotNull(retorno);
    }

}
