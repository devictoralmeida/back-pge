package br.gov.ce.pge.gestao.contantes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MensagemOrigemDebitoTest {

	@Test
	public void test_mensagem_origem_cadastrada() {
		assertEquals("O registro já foi cadastrado anteriormente!",
				MensagemOrigemDebito.MENSAGEM_ORIGEM_CADASTRADA);
	}

	@Test
	public void test_mensagem_origem_nao_encontrada() {
		assertEquals("Origem débito não encontrada.", MensagemOrigemDebito.MENSAGEM_ORIGEM_NAO_ENCONTRADA);
	}

	@Test
	public void test_mensagem_origem_sem_historico_edicao() {
		assertEquals("Origem de Débito selecionada não possui histórico de edições.",
				MensagemOrigemDebito.MENSAGEM_ORIGEM_SEM_HISTORICO_EDICAO);
	}

	@Test
	public void test_mensagem_origem_erro_delete() {
		assertEquals(
				"Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.",
				MensagemOrigemDebito.MENSAGEM_ORIGEM_ERRO_DELETE);
	}

}
