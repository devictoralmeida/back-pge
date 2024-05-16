package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;

class HistoricoAtualizacaoSistemaToDtoMapperTest {

	@Test
	void test() {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-sistema")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaSistemaDto_dadosAntigoNull());
		assertNotNull(retorno);
		assertEquals(0, retorno.size());
	}

	@Test
	void test_idsAdicionados() throws JsonProcessingException {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-sistema")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaSistemaDto_idsAdicionados());
		assertNotNull(retorno);
	}
	
	@Test
	void test_idsRemovidos() throws JsonProcessingException {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-sistema")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaSistemaDto_idsRemovidos());
		assertNotNull(retorno);
	}
}
