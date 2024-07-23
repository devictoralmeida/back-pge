package br.gov.ce.pge.gestao.entity;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProdutoServicoTest {

	@Test
	public void test_criacao_produto_servico() {
		ProdutoServico dto = getProdutoServico();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"), dto.getId());
		Assertions.assertEquals("produto servico 1", dto.getDescricao());
		Assertions.assertEquals("00001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getTipoReceitas().size());
	}

	public static ProdutoServico getProdutoServico() {
		var model = new ProdutoServico();
		model.setId(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));
		model.setCodigo("00001");
		model.setDescricao("produto servico 1");
		model.setSituacao(Situacao.ATIVA);
		model.setTipoReceitas(List.of(TipoReceitaTest.getTipoReceita()));
		return model;
	}
	
	public static ProdutoServico getProdutoServicoUpdate() {
		var model = new ProdutoServico();
		model.setId(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));
		model.setCodigo("00001");
		model.setDescricao("produto servico 1 update");
		model.setSituacao(Situacao.ATIVA);
		model.setTipoReceitas(List.of(TipoReceitaTest.getTipoReceita()));
		return model;
	}

	public static ProdutoServico getProdutoServicoOutraDescricao() {
		var model = new ProdutoServico();
		model.setId(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));
		model.setCodigo("00001");
		model.setDescricao("produto servico 2");
		model.setSituacao(Situacao.ATIVA);
		model.setTipoReceitas(List.of(TipoReceitaTest.getTipoReceita()));
		return model;
	}

	public static ProdutoServico getProdutoServicoOutraSituacao() {
		var model = new ProdutoServico();
		model.setId(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));
		model.setCodigo("00001");
		model.setDescricao("produto servico 1");
		model.setSituacao(Situacao.INATIVA);
		model.setTipoReceitas(List.of(TipoReceitaTest.getTipoReceita()));
		return model;
	}

	public static ProdutoServico getProdutoServicoOutroTipoReceita() {
		var model = new ProdutoServico();
		model.setId(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));
		model.setCodigo("00001");
		model.setDescricao("produto servico 1");
		model.setSituacao(Situacao.ATIVA);
		model.setTipoReceitas(List.of(TipoReceitaTest.getTipoReceitaDiferente()));
		return model;
	}

}
