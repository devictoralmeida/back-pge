package br.gov.ce.pge.gestao.contantes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MensagemProdutoServicoTest {

	@Test
	public void test_mensagem_produto_cadastrado() {
		assertEquals("O registro já foi cadastrado anteriormente!",
				MensagemProdutoServico.MENSAGEM_PRODUTO_CADASTRADO);
	}

	@Test
	public void test_mensagem_produto_nao_encontrado() {
		assertEquals("Produto/Serviço não encontrado.", MensagemProdutoServico.MENSAGEM_PRODUTO_NAO_ENCONTRADO);
	}

	@Test
	public void test_mensagem_produto_sem_historico_edicao() {
		assertEquals("Produto/Serviço selecionado não possui histórico de edições.",
				MensagemProdutoServico.MENSAGEM_PRODUTO_SEM_HISTORICO_EDICAO);
	}

}
