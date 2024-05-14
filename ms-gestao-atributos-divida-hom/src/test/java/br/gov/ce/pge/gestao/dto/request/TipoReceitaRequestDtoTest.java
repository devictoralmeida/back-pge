package br.gov.ce.pge.gestao.dto.request;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TipoReceitaRequestDtoTest {

	@Test
	public void test_criacao_produto_servico() {
		TipoReceitaRequestDto dto = getRequest();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("Receita 01", dto.getDescricao());
		Assertions.assertEquals("0001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	@Test
	public void test_getters_setters() {
		TipoReceitaRequestDto dto = new TipoReceitaRequestDto();
		UUID uuid = UUID.randomUUID();
		dto.setId(uuid);
		Assertions.assertEquals(uuid, dto.getId());

		dto.setDescricao("Receita 01");
		Assertions.assertEquals("Receita 01", dto.getDescricao());

		dto.setCodigo("0001");
		Assertions.assertEquals("0001", dto.getCodigo());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	public static TipoReceitaRequestDto getRequest() {
		var dto = new TipoReceitaRequestDto();
		dto.setCodigo("0001");
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.ATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

	public static TipoReceitaRequestDto getRequestUpdate() {
		var dto = new TipoReceitaRequestDto();
		dto.setCodigo("0001");
		dto.setDescricao("Receita 01 update");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.INATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

	public static TipoReceitaRequestDto getRequestSemDescricao() {
		TipoReceitaRequestDto dto = new TipoReceitaRequestDto();
		dto.setDescricao("");
		dto.setNatureza(Natureza.TRIBUTARIA);
		return dto;
	}

	public static TipoReceitaUpdateRequestDto getRequestUpdateOutraDescricao() {
		var dto = new TipoReceitaUpdateRequestDto();
		dto.setDescricao("Receita 02");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.ATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

	public static TipoReceitaUpdateRequestDto getRequestUpdateOutraSituacao() {
		var dto = new TipoReceitaUpdateRequestDto();
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.INATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

	public static TipoReceitaUpdateRequestDto getRequestUpdateOutraNatureza() {
		var dto = new TipoReceitaUpdateRequestDto();
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.NAO_TRIBUTARIA);
		dto.setSituacao(Situacao.ATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}

	public static TipoReceitaUpdateRequestDto getRequestUpdateOutraOrigem() {
		var dto = new TipoReceitaUpdateRequestDto();
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.ATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("b88bc5e8-6126-4af9-9c61-cea471800974")));
		return dto;
	}
}
