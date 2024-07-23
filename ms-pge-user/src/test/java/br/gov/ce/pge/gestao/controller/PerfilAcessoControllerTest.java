package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoResponseDtoTest;
import br.gov.ce.pge.gestao.entity.PerfilAcessoTest;
import br.gov.ce.pge.gestao.service.PerfilAcessoService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class PerfilAcessoControllerTest {

	@InjectMocks
	PerfilAcessoController controller;

	@Mock
	PerfilAcessoService service;

	@Mock
	GlobalExceptionHandler globalExceptionHandler;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	public void test_save_ok() throws Exception {
		when(service.save(any(),anyString())).thenReturn(PerfilAcessoTest.getPerfilAcesso());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(PerfilAcessoRequestDtoTest.getRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/perfil-acesso")
						.header("nomeUsuario", "Paulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"ADMIN\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO + " de Perfil de Acesso")));

	    verify(service, times(1)).save(any(),anyString());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_consulta_por_id_ok() throws Exception {
		when(service.findById(any())).thenReturn(PerfilAcessoResponseDtoTest.getPerfilAcessoResponse());

		mockMvc.perform(MockMvcRequestBuilders.get("/perfil-acesso/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a597aa9e-0a05-48de-adcb-a7a1b631aedd\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"ADMIN\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

	    verify(service, times(1)).findById(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_list_all_ok() throws Exception {
		when(service.findAll()).thenReturn(PerfilAcessoTest.getListaPerfilAcesso());

		mockMvc.perform(MockMvcRequestBuilders.get("/perfil-acesso/")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

	    verify(service, times(1)).findAll();
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_update_ok() throws Exception {
		when(service.update(any(),any(),anyString())).thenReturn(PerfilAcessoTest.getPerfilAcessoUpdate());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(PerfilAcessoRequestDtoTest.getRequestUpdate());

		mockMvc.perform(MockMvcRequestBuilders.put("/perfil-acesso/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
						.header("nomeUsuario", "Paulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a597aa9e-0a05-48de-adcb-a7a1b631aedd\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"ADMIN\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"INATIVA\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

	    verify(service, times(1)).update(any(),any(),anyString());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_cadastro_debito_origem_sem_nome_error() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(PerfilAcessoRequestDtoTest.getRequestSemNome());

		mockMvc.perform(MockMvcRequestBuilders.post("/perfil-acesso")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).update(any(),any(),anyString());
	}

	@Test
	public void test_delete_ok() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/perfil-acesso/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO)));

		verify(service, times(1)).delete(any());
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_historico_ok() throws Exception {
		when(service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10)).thenReturn(PaginacaoResponseDtoTest.getPaginacaoHistoricoPerfil());

		mockMvc.perform(MockMvcRequestBuilders.get("/perfil-acesso/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO)));

		verify(service, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10);
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_historico_lista_vazia_ok() {
		doThrow(new HistoricoAtualizacaoNotFoundException("O Perfil de acesso selecionado não possui histórico de edições.")).when(service).findHistorys(any(), eq(0), eq(0));

		HistoricoAtualizacaoNotFoundException exception = assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			controller.viewHistoryById(any(), eq(0), eq(0));
		});

		assertEquals("O Perfil de acesso selecionado não possui histórico de edições.", exception.getMessage());

		verify(service).findHistorys(any(), eq(0), eq(0));

	}

	@Test
	public void test_filter_ok() throws Exception {
		when(service.findByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter(), 1, "nome", 10)).thenReturn(PaginacaoResponseDtoTest.getPaginacaoFilterPerfil());

		ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(PerfilAcessoFilterRequestDtoTest.getRequestFilter());

		mockMvc.perform(MockMvcRequestBuilders.post("/perfil-acesso/filtros")
	    	            .contentType(MediaType.APPLICATION_JSON)
	    	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO)));

		verify(service, times(1)).findByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter(), 1, "nome", 10);

	}

	@Test
	public void test_filter_lista_vazia_ok() throws Exception {
		when(service.findByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter(), 1, "nome", 10)).thenReturn(PaginacaoResponseDto.fromResultado(List.of(), 1, 1, 1));

		ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(PerfilAcessoFilterRequestDtoTest.getRequestFilter());

		mockMvc.perform(MockMvcRequestBuilders.post("/perfil-acesso/filtros")
	    	            .contentType(MediaType.APPLICATION_JSON)
	    	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA)));

		verify(service, times(1)).findByFilter(PerfilAcessoFilterRequestDtoTest.getRequestFilter(), 1, "nome", 10);

	}

}
