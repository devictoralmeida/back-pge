package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoReceitaToDtoMapperTest {

	@Test
	void test() {
		var mapper = new TipoReceitaToDtoMapper();
		
		assertNotNull(mapper);
	}

}
