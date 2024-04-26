package br.gov.ce.pge.gestao.dto.response;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.Situacao;

public class OrigemDebitoResponseDtoTest {

	public static OrigemDebitoResponseDto getOrigem() {
		var response = new OrigemDebitoResponseDto();
		response.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		response.setDescricao("teste");
		response.setSituacao(Situacao.ATIVA);
		response.setNome("teste nome origem");
		return response;
	}
	
	@Test
	public void test_criacao_origem() {
		OrigemDebitoResponseDto dto = getOrigem();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());
		Assertions.assertEquals("teste nome origem", dto.getNome());
		Assertions.assertEquals("teste", dto.getDescricao());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

}
