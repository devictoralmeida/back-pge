package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.InscricaoResponseDto;
import br.gov.ce.pge.gestao.entity.InscricaoTest;
import br.gov.ce.pge.gestao.enums.StatusInscricao;

class InscricaoMapperToDtoTest {

	@Test
	void teste_converter() {
		InscricaoResponseDto dto = InscricaoMapperToDto.converter(InscricaoTest.getIncricaoCompleta());
		
		assertNotNull(dto.getDevedor());
		assertNotNull(dto.getDivida());
		assertEquals(StatusInscricao.EM_ANALISE, dto.getStatus());
		assertNotNull(dto.getCorresponsaveis());
		assertEquals(2, dto.getCorresponsaveis().size());
		assertEquals("Kamila Lima", dto.getCorresponsaveis().get(0).getNomeRazaoSocial());
		assertEquals("Osvaldo e Julio Marketing ME", dto.getCorresponsaveis().get(1).getNomeRazaoSocial());
		assertEquals(BigDecimal.valueOf(150), dto.getDebitos().get(0).getValorPrincipal());
		assertEquals(BigDecimal.valueOf(50), dto.getDebitos().get(0).getValorMulta());
	}

}
