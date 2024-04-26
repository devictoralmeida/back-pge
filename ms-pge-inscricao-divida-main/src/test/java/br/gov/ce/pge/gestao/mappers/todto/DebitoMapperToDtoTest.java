package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.DebitoResponseDto;
import br.gov.ce.pge.gestao.entity.DebitoTest;

class DebitoMapperToDtoTest {

	@Test
	void teste_converter() {
		DebitoResponseDto dto = DebitoMapperToDto.converter(DebitoTest.getDebito());
		
		assertEquals("01/2024", dto.getReferenciaInicial());
		assertEquals("10/2024", dto.getReferenciaFinal());
		assertEquals(LocalDate.parse("2024-03-30"), dto.getDataVencimento());
		assertEquals(LocalDate.parse("2024-03-30"), dto.getDataConstituicaoDefinitivaCredito());
		assertEquals(BigDecimal.valueOf(150), dto.getValorPrincipal());
		assertEquals(BigDecimal.valueOf(50), dto.getValorMulta());
	}
	
	@Test
	void teste_converter_lista() {
		List<DebitoResponseDto> listaDto = DebitoMapperToDto.converterList(DebitoTest.getDebitos());
		
		assertEquals("01/2024", listaDto.get(0).getReferenciaInicial());
		assertEquals("10/2024", listaDto.get(0).getReferenciaFinal());
		assertEquals(LocalDate.parse("2024-03-30"), listaDto.get(0).getDataVencimento());
		assertEquals(LocalDate.parse("2024-03-30"), listaDto.get(0).getDataConstituicaoDefinitivaCredito());
		assertEquals(BigDecimal.valueOf(150), listaDto.get(0).getValorPrincipal());
		assertEquals(BigDecimal.valueOf(50), listaDto.get(0).getValorMulta());
	}

}
