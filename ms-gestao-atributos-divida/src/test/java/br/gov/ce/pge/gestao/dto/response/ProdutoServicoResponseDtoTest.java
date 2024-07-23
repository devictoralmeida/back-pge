package br.gov.ce.pge.gestao.dto.response;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProdutoServicoResponseDtoTest {

	@Test
	public void test_criacao_produto_servico() {
		ProdutoServicoResponseDto dto = getProdutoServicoResponse();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"), dto.getId());
		Assertions.assertEquals("produto servico 1", dto.getDescricao());
		Assertions.assertEquals("00001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getIdsTipoReceitas().size());
	}
	
	public static ProdutoServicoResponseDto getProdutoServicoResponse() {
		var dto = new ProdutoServicoResponseDto();
		dto.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		dto.setCodigo("00001");
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}

	public static ProdutoServicoResponseDto getProdutoServicoResponseUpdate() {
		var dto = new ProdutoServicoResponseDto();
		dto.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		dto.setCodigo("00001");
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setIdsTipoReceitas(List.of(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed")));
		return dto;
	}
}
