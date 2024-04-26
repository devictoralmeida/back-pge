package br.gov.ce.pge.gestao.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageCommonsContanstTest {

	    @Test
	    @DisplayName("Teste MENSAGEM_SAVE_SUCESSO")
	    public void testMensagemSaveSucesso() {
	        assertEquals("O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta",
	                MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_UPDATE_SUCESSO")
	    public void testMensagemUpdateSucesso() {
	        assertEquals("O registro foi salvo com sucesso!", MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_CONSULTA_ID_SUCESSO")
	    public void testMensagemConsultaIdSucesso() {
	        assertEquals("Registro encontrado", MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_CONSULTA_TODOS_SUCESSO")
	    public void testMensagemConsultaTodosSucesso() {
	        assertEquals("Registros encontrados", MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_CONSULTA_FILTRO_VAZIA")
	    public void testMensagemConsultaFiltroVazia() {
	        assertEquals("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.",
	                MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_CONSULTA_FILTRO_SUCESSO")
	    public void testMensagemConsultaFiltroSucesso() {
	        assertEquals("Registros encontrados", MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO")
	    public void testMensagemConsultaHistoricoEncontrado() {
	        assertEquals("Histórico dos Registros encontrados",
	                MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_CONSULTA_HISTORICO_VAZIA")
	    public void testMensagemConsultaHistoricoVazia() {
	        assertEquals("Registro selecionado não possui histórico de edições",
	                MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_VAZIA);
	    }

	    @Test
	    @DisplayName("Teste MENSAGEM_DELETE_SUCESSO")
	    public void testMensagemDeleteSucesso() {
	        assertEquals("Registro excluído com sucesso!", MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO);
	    }

	@Test
	@DisplayName("Teste MENSAGEM_CONSULTA_CPF_SUCESSO")
	public void testMensagemConsultaCPFSucesso() {
		assertEquals("Registro encontrado", MessageCommonsContanst.MENSAGEM_CONSULTA_CPF_SUCESSO);
	}

	@Test
	@DisplayName("Teste MENSAGEM_EMAIL_RECUPERACAO_SENHA")
	public void testMensagemEmailReceuperacaoSucesso() {
		assertEquals("Envio de email de recuperação de senha realizado com sucesso!", MessageCommonsContanst.MENSAGEM_EMAIL_RECUPERACAO_SENHA);
	}

}
