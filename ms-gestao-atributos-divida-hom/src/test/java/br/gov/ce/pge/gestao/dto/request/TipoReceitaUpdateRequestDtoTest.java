package br.gov.ce.pge.gestao.dto.request;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TipoReceitaUpdateRequestDtoTest {

	@Test
	public void test_criacao_request() {
		TipoReceitaUpdateRequestDto dto = getRequest();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("Receita 01", dto.getDescricao());
		Assertions.assertEquals(Natureza.TRIBUTARIA, dto.getNatureza());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
	}

	@Test
	public void test_getters_setters() {
		TipoReceitaUpdateRequestDto dto = new TipoReceitaUpdateRequestDto();
		UUID uuid = UUID.randomUUID();
		dto.setId(uuid);
		Assertions.assertEquals(uuid, dto.getId());

		dto.setDescricao("Receita 01");
		Assertions.assertEquals("Receita 01", dto.getDescricao());

		dto.setNatureza(Natureza.TRIBUTARIA);
		Assertions.assertEquals(Natureza.TRIBUTARIA, dto.getNatureza());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());

		dto.setOrigemDebitos(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
	}

	public static TipoReceitaUpdateRequestDto getRequest() {
		var dto = new TipoReceitaUpdateRequestDto();
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.ATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

	public static TipoReceitaUpdateRequestDto getRequestUpdate() {
		var dto = new TipoReceitaUpdateRequestDto();
		dto.setDescricao("Receita 01 update");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.INATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

}
