package br.gov.ce.pge.gestao.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageCommonsConstantsTest {

    @Test
    @DisplayName("Teste MENSAGEM_SAVE_SUCESSO")
    public void test_mensagem_save_sucesso() {
        assertEquals("O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta",
                MessageCommonsConstants.MENSAGEM_SAVE_SUCESSO);
    }

    @Test
    @DisplayName("Teste MENSAGEM_UPDATE_SUCESSO")
    public void test_mensagem_update_sucesso() {
        assertEquals("O registro foi salvo com sucesso!", MessageCommonsConstants.MENSAGEM_UPDATE_SUCESSO);
    }

    @Test
    @DisplayName("Teste MENSAGEM_CONSULTA_ID_SUCESSO")
    public void test_mensagem_consulta_id_sucesso() {
        assertEquals("Registro encontrado.", MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO);
    }

    @Test
    @DisplayName("Teste MENSAGEM_CONSULTA_TODOS_SUCESSO")
    public void test_mensagem_consulta_todos_sucesso() {
        assertEquals("Registros encontrados", MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO);
    }

    @Test
    @DisplayName("Teste MENSAGEM_CONSULTA_FILTRO_VAZIA")
    public void test_mensagem_consulta_filtro_vazia() {
        assertEquals("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.",
                MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA);
    }

    @Test
    @DisplayName("Teste MENSAGEM_CONSULTA_FILTRO_SUCESSO")
    public void test_mensagem_consulta_filtro_sucesso() {
        assertEquals("Registros encontrados", MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO);
    }

    @Test
    @DisplayName("Teste MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO")
    public void test_mensagem_consulta_historico_encontrado() {
        assertEquals("Histórico dos Registros encontrados",
                MessageCommonsConstants.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO);
    }

    @Test
    @DisplayName("Teste MENSAGEM_CONSULTA_HISTORICO_VAZIA")
    public void test_mensagem_consulta_historico_vazia() {
        assertEquals("Registro selecionado não possui histórico de edições",
                MessageCommonsConstants.MENSAGEM_CONSULTA_HISTORICO_VAZIA);
    }

    @Test
    @DisplayName("Teste MENSAGEM_DELETE_SUCESSO")
    public void test_mensagem_delete_sucesso() {
        assertEquals("Registro excluído com sucesso!", MessageCommonsConstants.MENSAGEM_DELETE_SUCESSO);
    }
}
