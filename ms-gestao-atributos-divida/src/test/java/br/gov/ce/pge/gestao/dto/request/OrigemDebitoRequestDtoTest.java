package br.gov.ce.pge.gestao.dto.request;

import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrigemDebitoRequestDtoTest {

	@Test
	public void test_criacao_origem() {
		OrigemDebitoRequestDto dto = getRequest();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("teste nome origem", dto.getNome());
		Assertions.assertEquals("teste", dto.getDescricao());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	@Test
	public void test_getters_setters() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		UUID uuid = UUID.randomUUID();
		dto.setId(uuid);
		Assertions.assertEquals(uuid, dto.getId());

		dto.setNome("teste nome origem");
		Assertions.assertEquals("teste nome origem", dto.getNome());

		dto.setDescricao("teste");
		Assertions.assertEquals("teste", dto.getDescricao());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	public static OrigemDebitoRequestDto getRequest() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setNome("teste nome origem");
		dto.setDescricao("teste");
		dto.setSituacao(Situacao.ATIVA);
		return dto;
	}
	
	public static OrigemDebitoRequestDto getRequestUpdate() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		dto.setNome("teste nome origem");
		dto.setDescricao("teste");
		dto.setSituacao(Situacao.INATIVA);
		return dto;
	}
	
	public static OrigemDebitoRequestDto getRequestSemNome() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setNome("");
		dto.setDescricao("teste");
		return dto;
	}

	public static OrigemDebitoRequestDto getRequestSemDescricao() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setNome("teste junit");
		dto.setDescricao("");
		return dto;
	}

	public static OrigemDebitoRequestDto getRequestUpdateOutroNome() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setNome("teste nome origem update");
		dto.setDescricao("teste");
		dto.setSituacao(Situacao.ATIVA);
		return dto;
	}

	public static OrigemDebitoRequestDto getRequestUpdateOutraDescricao() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setNome("teste nome origem");
		dto.setDescricao("teste descticao diferente");
		dto.setSituacao(Situacao.ATIVA);
		return dto;
	}

	public static OrigemDebitoRequestDto getRequestUpdateOutraSituacao() {
		OrigemDebitoRequestDto dto = new OrigemDebitoRequestDto();
		dto.setNome("teste nome origem");
		dto.setDescricao("teste");
		dto.setSituacao(Situacao.INATIVA);
		return dto;
	}
}
