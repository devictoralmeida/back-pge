package br.gov.ce.pge.gestao.mappers.todto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FaseDividaToDtoMapperTest {

	@Test
	void test() {
		var mapper = new FaseDividaToDtoMapper();
		
		assertNotNull(mapper);
	}

}
