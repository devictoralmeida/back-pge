package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;

class HistoricoAtualizacaoUsuarioToDtoMapperTest {

	@Test
	void test() {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-usuario")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaUsuarioDto_dadosAntigoNull());
		assertNotNull(retorno);
		assertEquals(1, retorno.size());
	}
	
	
	@Test
	void test_idsAdicionadosPerfis() throws JsonProcessingException {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-usuario")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaUsuarioDto_idsAdicionados_perfis());
		assertNotNull(retorno);
	}
	
	@Test
	void test_idsRemovidosPerfis() throws JsonProcessingException {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-usuario")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaUsuarioDto_idsRemovidos_perfis());
		assertNotNull(retorno);
	}
	
	@Test
	void test_idsAdicionadosSistema() throws JsonProcessingException {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-usuario")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaUsuarioDto_idsAdicionados_sistema());
		assertNotNull(retorno);
	}
	
	@Test
	void test_idsRemovidosSistema() throws JsonProcessingException {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-usuario")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaUsuarioDto_idsRemovidos_sistema());
		assertNotNull(retorno);
	}

}
