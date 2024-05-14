package br.gov.ce.pge.gestao.dto.request;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProdutoServicoRequestDtoTest {


	@Test
	public void test_criacao_produto_servico() {
		ProdutoServicoRequestDto dto = getRequest();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("produto servico 1", dto.getDescricao());
		Assertions.assertEquals("00001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	@Test
	public void test_getters_setters() {
		ProdutoServicoRequestDto dto = new ProdutoServicoRequestDto();
		UUID uuid = UUID.randomUUID();
		dto.setId(uuid);
		Assertions.assertEquals(uuid, dto.getId());

		dto.setDescricao("produto servico 1");
		Assertions.assertEquals("produto servico 1", dto.getDescricao());

		dto.setCodigo("00001");
		Assertions.assertEquals("00001", dto.getCodigo());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	public static ProdutoServicoRequestDto getRequest() {
		var dto = new ProdutoServicoRequestDto();
		dto.setCodigo("00001");
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}
	
	public static ProdutoServicoRequestDto getRequestUpdate() {
		var dto = new ProdutoServicoRequestDto();
		dto.setCodigo("00001");
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}

	public static ProdutoServicoRequestDto getRequestSemDescricao() {
		ProdutoServicoRequestDto dto = new ProdutoServicoRequestDto();
		dto.setDescricao("");
		dto.setSituacao(Situacao.ATIVA);
		return dto;
	}

	public static ProdutoServicoUpdateRequestDto getRequestUpdateOutraDescricao() {
		var dto = new ProdutoServicoUpdateRequestDto();
		dto.setDescricao("produto servico 2");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}

	public static ProdutoServicoUpdateRequestDto getRequestUpdateOutraSituacao() {
		var dto = new ProdutoServicoUpdateRequestDto();
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.INATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}

	public static ProdutoServicoUpdateRequestDto getRequestUpdateOutroTipoReceita() {
		var dto = new ProdutoServicoUpdateRequestDto();
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("bb5b7b6f-cb6e-4ec3-8ddf-ad4489d011b4")));
		return dto;
	}
}
