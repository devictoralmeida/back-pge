package br.gov.ce.pge.gestao.dto.response;

import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrigemDebitoResponseDtoTest {

	@Test
	public void test_criacao_origem() {
		OrigemDebitoResponseDto dto = getResponse();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());
		Assertions.assertEquals("teste nome origem", dto.getNome());
		Assertions.assertEquals("teste", dto.getDescricao());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	@Test
	public void test_getters_setters() {
		OrigemDebitoResponseDto dto = new OrigemDebitoResponseDto();

		dto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		Assertions.assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());

		dto.setNome("teste nome origem");
		Assertions.assertEquals("teste nome origem", dto.getNome());

		dto.setDescricao("teste");
		Assertions.assertEquals("teste", dto.getDescricao());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	public static OrigemDebitoResponseDto getResponse() {
		var response = new OrigemDebitoResponseDto();
		response.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		response.setDescricao("teste");
		response.setSituacao(Situacao.ATIVA);
		response.setNome("teste nome origem");
		return response;
	}
	
	public static OrigemDebitoResponseDto getResponseUpdate() {
		var response = new OrigemDebitoResponseDto();
		response.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		response.setDescricao("teste");
		response.setSituacao(Situacao.INATIVA);
		response.setNome("teste nome origem");
		return response;
	}
}
