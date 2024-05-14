package br.gov.ce.pge.gestao.contantes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MensagemTipoReceitaTest {

	@Test
	public void test_mensagem_tipo_receita_cadastrada() {
		assertEquals("O registro já foi cadastrado anteriormente!",
				MensagemTipoReceita.MENSAGEM_TIPORECEITA_CADASTRADA);
	}

	@Test
	public void test_mensagem_tipo_receita_nao_encontrada() {
		assertEquals("Tipo de Receita não encontrada.", MensagemTipoReceita.MENSAGEM_TIPORECEITA_NAO_ENCONTRADA);
	}

	@Test
	public void test_mensagem_tipo_receita_sem_historico_edicao() {
		assertEquals("Tipo Receita selecionado não possui histórico de edições.",
				MensagemTipoReceita.MENSAGEM_TIPORECEITA_SEM_HISTORICO_EDICAO);
	}

	@Test
	public void test_mensagem_tipo_receita_erro_delete() {
		assertEquals(
				"Não foi possível realizar a exclusão! O Tipo receita está sendo utilizado para um ou mais Produto/Serviço cadastrado.",
				MensagemTipoReceita.MENSAGEM_TIPORECEITA_ERRO_DELETE);
	}

}
