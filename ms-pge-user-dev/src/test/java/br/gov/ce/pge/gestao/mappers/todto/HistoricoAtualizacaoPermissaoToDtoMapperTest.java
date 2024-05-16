package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDtoTest;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;

class HistoricoAtualizacaoPermissaoToDtoMapperTest {

	@Test
	void test() {
		var retorno = AuditoriaMapperStratery.mapperHistorico.get("historico-permissao")
				.processarAuditoria(AuditoriaDtoTest.getAuditoriaPermissaoDto_dadosAntigoNull());
		assertNotNull(retorno);
		assertEquals(0, retorno.size());
	}

}
