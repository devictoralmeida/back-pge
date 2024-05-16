package br.gov.ce.pge.gestao.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageCommonsContanstTest {

	@Test
	@DisplayName("Teste MENSAGEM_SAVE_SUCESSO")
	public void test_mensagem_save_sucesso() {
		assertEquals("O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta",
				MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_UPDATE_SUCESSO")
	public void test_mensagem_update_sucesso() {
		assertEquals("O registro foi salvo com sucesso!", MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_ID_SUCESSO")
	public void test_mensagem_consultaId_Sucesso() {
		assertEquals("Registro encontrado", MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_TODOS_SUCESSO")
	public void test_mensagem_consulta_todos_sucesso() {
		assertEquals("Registros encontrados", MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_FILTRO_VAZIA")
	public void test_mensagem_consulta_filtro_vazia() {
		assertEquals("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.",
				MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_FILTRO_SUCESSO")
	public void test_mensagem_consulta_filtro_sucesso() {
		assertEquals("Registros encontrados", MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO")
	public void test_mensagem_consulta_historico_encontrado() {
		assertEquals("Histórico dos Registros encontrados",
				MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_HISTORICO_VAZIA")
	public void test_mensagem_consulta_historico_vazia() {
		assertEquals("Registro selecionado não possui histórico de edições",
				MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_VAZIA);
	}

	@Test
	@DisplayName("Teste MENSAGEM_DELETE_SUCESSO")
	public void test_mensagem_delete_sucesso() {
		assertEquals("Registro excluído com sucesso!", MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_CPF_SUCESSO")
	public void test_mensagem_consultaCPF_Sucesso() {
		assertEquals("Registro encontrado", MessageCommonsContanst.MENSAGEM_CONSULTA_CPF_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_EMAIL_RECUPERACAO_SENHA")
	public void test_mensagem_email_receuperacao_Sucesso() {
		assertEquals("Envio de email de recuperação de senha realizado com sucesso!",
				MessageCommonsContanst.MENSAGEM_EMAIL_RECUPERACAO_SENHA);
	}

	@Test
	@DisplayName("Teste MENSAGEM_USUARIO_NAO_ENCONTRADO")
	public void test_mensagem_usuario_nao_encontrado() {
		assertEquals("Usuário não encontrado!", MessageCommonsContanst.MENSAGEM_USUARIO_NAO_ENCONTRADO);
	}

}
