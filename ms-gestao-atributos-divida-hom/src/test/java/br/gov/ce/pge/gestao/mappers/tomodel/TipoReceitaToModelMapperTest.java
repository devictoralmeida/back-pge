package br.gov.ce.pge.gestao.mappers.tomodel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class TipoReceitaToModelMapperTest {

	@Test
	void test() {
		var mapper = new TipoReceitaToModelMapper();
		assertNotNull(mapper);
	}

}
