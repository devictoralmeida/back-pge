package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProdutoServicoToDtoMapperTest {

	@Test
	void test() {
		var mapper = new ProdutoServicoToDtoMapper();
		
		assertNotNull(mapper);
	}

}
