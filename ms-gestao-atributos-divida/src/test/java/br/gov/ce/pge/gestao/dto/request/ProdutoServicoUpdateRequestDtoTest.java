package br.gov.ce.pge.gestao.dto.request;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProdutoServicoUpdateRequestDtoTest {

	@Test
	public void test_criacao_request() {
		ProdutoServicoUpdateRequestDto dto = getRequest();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("produto servico 1", dto.getDescricao());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getIdsTipoReceitas().size());
	}

	@Test
	public void test_getters_setters() {
		ProdutoServicoUpdateRequestDto dto = new ProdutoServicoUpdateRequestDto();
		UUID uuid = UUID.randomUUID();
		dto.setId(uuid);
		Assertions.assertEquals(uuid, dto.getId());

		dto.setDescricao("produto servico 1");
		Assertions.assertEquals("produto servico 1", dto.getDescricao());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());

		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getIdsTipoReceitas().size());
	}

	public static ProdutoServicoUpdateRequestDto getRequest() {
		var dto = new ProdutoServicoUpdateRequestDto();
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}
	
	public static ProdutoServicoUpdateRequestDto getRequestUpdate() {
		var dto = new ProdutoServicoUpdateRequestDto();
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}
}
