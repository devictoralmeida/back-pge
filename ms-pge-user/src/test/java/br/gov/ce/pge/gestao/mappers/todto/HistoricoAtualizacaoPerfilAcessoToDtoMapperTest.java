package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;

class HistoricoAtualizacaoPerfilAcessoToDtoMapperTest {

	@Test
	void test() {
		 var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-perfil").processarAuditoria(AuditoriaDtoTest.getAuditoriaPerfilDto_dadosAntigoNull());
		 assertNotNull(retorno);
		 assertEquals(0, retorno.size());
	}
	
	@Test
	void test_idsAdicionados_sistema() throws JsonProcessingException {
		 var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-perfil").processarAuditoria(AuditoriaDtoTest.getAuditoriaPerfilDto_idsAdicionados_sistema());
		 assertNotNull(retorno);
	}
	
	@Test
	void test_idsRemovidos_sistema() throws JsonProcessingException {
		 var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-perfil").processarAuditoria(AuditoriaDtoTest.getAuditoriaPerfilDto_idsRemovidos_sistema());
		 assertNotNull(retorno);
	}
	
	@Test
	void test_idsAdicionados_permissao() throws JsonProcessingException {
		 var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-perfil").processarAuditoria(AuditoriaDtoTest.getAuditoriaPerfilDto_idsAdicionados_permissao());
		 assertNotNull(retorno);
	}
	
	@Test
	void test_idsRemovidos_permissao() throws JsonProcessingException {
		 var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-perfil").processarAuditoria(AuditoriaDtoTest.getAuditoriaPerfilDto_idsRemovidos_permissao());
		 assertNotNull(retorno);
	}
}
