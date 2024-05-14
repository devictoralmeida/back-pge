package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ColaboradorResponseDtoTest {

	public static ColaboradorResponseDto getColaboradorResponseDto() {
		var dto = new ColaboradorResponseDto();
		dto.setArea("TI");
		dto.setCpf("05459813360");
		dto.setDesvinculado(false);
		dto.setNome("Jose Junior");
		dto.setDataDesligamento(LocalDateTime.now());
		
		return dto;
	}
	
	public static ColaboradorResponseDto getColaboradorResponseDto_desvinculado() {
		var dto = new ColaboradorResponseDto();
		dto.setArea("TI");
		dto.setCpf("05459813360");
		dto.setDesvinculado(true);
		dto.setNome("Jose Junior");
		
		return dto;
	}
	
	@Test
	void test_colaborador() {
		var dto = getColaboradorResponseDto();
		assertEquals("TI", dto.getArea());
		assertEquals("05459813360", dto.getCpf());
		assertEquals(false, dto.getDesvinculado());
		assertEquals("Jose Junior", dto.getNome());
	}

}
