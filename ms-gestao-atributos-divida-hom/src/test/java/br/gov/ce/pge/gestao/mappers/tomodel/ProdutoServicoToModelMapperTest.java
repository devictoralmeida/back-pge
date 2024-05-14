package br.gov.ce.pge.gestao.mappers.tomodel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ProdutoServicoToModelMapperTest {

	@Test
	void test() {
		var mapper = new ProdutoServicoToModelMapper();
		assertNotNull(mapper);
	}

}
