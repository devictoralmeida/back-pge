package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class HistoricoAtualizacaoMapperCommonsTest {

	@Test
	void test() {
		var mapper = new HistoricoAtualizacaoMapperCommons();
		assertNotNull(mapper);
	}

}
