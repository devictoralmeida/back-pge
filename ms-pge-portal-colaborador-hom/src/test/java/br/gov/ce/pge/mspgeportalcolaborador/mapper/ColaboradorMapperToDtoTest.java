package br.gov.ce.pge.mspgeportalcolaborador.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.mspgeportalcolaborador.dto.ColaboradorResponseDto;
import br.gov.ce.pge.mspgeportalcolaborador.entity.ColaboradorTest;

class ColaboradorMapperToDtoTest {

	@Test
	void test_mapper_dto() {
		ColaboradorResponseDto dto = ColaboradorMapperToDto.toDto(ColaboradorTest.getColaborador());
		assertEquals("054.598.133-60", ColaboradorTest.getColaborador().getCpf());
		assertEquals("05459813360", dto.getCpf());
	}

}
