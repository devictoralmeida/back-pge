package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class OrigemDebitoConsultaResponseDtoTest {

	@Test
	public void test_criacao_origem() {
		OrigemDebitoConsultaResponseDto dto = getOrigemDebito();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a", dto.getId());
		Assertions.assertEquals("teste nome origem", dto.getNome());
		Assertions.assertEquals("teste", dto.getDescricao());
		Assertions.assertEquals(Situacao.ATIVA.toString(), dto.getSituacao());
	}

	@Test
	public void test_getters_setters() {
		OrigemDebitoConsultaResponseDto dto = new OrigemDebitoConsultaResponseDto();

		dto.setId("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18c");
		Assertions.assertEquals("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18c", dto.getId());

		dto.setNome("teste nome origem");
		Assertions.assertEquals("teste nome origem", dto.getNome());

		dto.setDescricao("teste");
		Assertions.assertEquals("teste", dto.getDescricao());

		dto.setSituacao(Situacao.ATIVA.toString());
		Assertions.assertEquals(Situacao.ATIVA.toString(), dto.getSituacao());
	}

	public static OrigemDebitoConsultaResponseDto getOrigemDebito() {
		var dto = new OrigemDebitoConsultaResponseDto();
		dto.setId("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a");
		dto.setNome("teste nome origem");
		dto.setDescricao("teste");
		dto.setSituacao(Situacao.ATIVA.toString());
		return dto;
	}

}
