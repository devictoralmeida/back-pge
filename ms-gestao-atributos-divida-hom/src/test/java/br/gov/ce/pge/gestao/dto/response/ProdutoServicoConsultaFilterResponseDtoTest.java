package br.gov.ce.pge.gestao.dto.response;

import java.util.List;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProdutoServicoConsultaFilterResponseDtoTest {

	@Test
	public void test_criacao_produto_servico() {
		ProdutoServicoConsultaFilterResponseDto dto = getProdutoServico();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1", dto.getId());
		Assertions.assertEquals("produto servico 1", dto.getDescricao());
		Assertions.assertEquals("00001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	public static ProdutoServicoConsultaFilterResponseDto getProdutoServico() {
		var dto = new ProdutoServicoConsultaFilterResponseDto();
		dto.setId("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1");
		dto.setCodigo("00001");
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		dto.setTipoReceitas(List.of(TipoReceitaConsultaFilterResponseDtoTest.getResponseConsulta()));
		return dto;
	}
	
}
