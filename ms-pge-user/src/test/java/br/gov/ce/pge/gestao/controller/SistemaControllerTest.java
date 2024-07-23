package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.SistemaRequestDto;
import br.gov.ce.pge.gestao.dto.request.SistemaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDtoTest;
import br.gov.ce.pge.gestao.entity.SistemaTest;
import br.gov.ce.pge.gestao.service.SistemaService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class SistemaControllerTest {

	@InjectMocks
	SistemaController controller;

	@Mock
	SistemaService service;

	@Mock
	GlobalExceptionHandler globalExceptionHandler;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	public void test_save_ok() throws Exception {
		when(service.save(any())).thenReturn(SistemaTest.getSistema());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(SistemaRequestDtoTest.getRequest());

	    mockMvc.perform(MockMvcRequestBuilders.post("/sistema")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"Portal da Dívida Ativa\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO)));

	    verify(service, times(1)).save(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_consulta_por_id_ok() throws Exception {
		when(service.findById(any())).thenReturn(SistemaTest.getSistema());

	    mockMvc.perform(MockMvcRequestBuilders.get("/sistema/{id}", "f08cc600-1268-4e35-8279-63ecef41454b")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"f08cc600-1268-4e35-8279-63ecef41454b\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"Portal da Dívida Ativa\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

	    verify(service, times(1)).findById(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_list_all_ok() throws Exception {
		when(service.findAllOrdenados()).thenReturn(SistemaTest.getListSistemaDto());

	    mockMvc.perform(MockMvcRequestBuilders.get("/sistema/")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

	    verify(service, times(1)).findAllOrdenados();
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_update_ok() throws Exception {
		when(service.update(any(),any())).thenReturn(SistemaTest.getSistemaUpdate());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(SistemaRequestDtoTest.getRequestUpdate());

	    mockMvc.perform(MockMvcRequestBuilders.put("/sistema/{id}", "f08cc600-1268-4e35-8279-63ecef41454b")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"f08cc600-1268-4e35-8279-63ecef41454b\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"portal divida ativa update\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

	    verify(service, times(1)).update(any(),any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_cadastro_debito_origem_sem_nome_error() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(SistemaRequestDtoTest.getRequestSemNome());

	    mockMvc.perform(MockMvcRequestBuilders.post("/sistema")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

		verify(service, times(0)).update(any(),any());
	}

	@Test
	public void test_delete_ok() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/sistema/{id}", "f08cc600-1268-4e35-8279-63ecef41454b")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO)));

		verify(service, times(1)).delete(any());
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_list_all_by_sistema() throws Exception {
		when(service.findAllPermissoesBySistema()).thenReturn(SistemaTest.getListSistema());

	    mockMvc.perform(MockMvcRequestBuilders.get("/sistema/lista-permissao")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("Sistemas encontrados")));

	    verify(service, times(1)).findAllPermissoesBySistema();
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_historico_ok() throws Exception {
		when(service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10)).thenReturn(PaginacaoResponseDtoTest.getPaginacaoHistoricoSistema());

	    mockMvc.perform(MockMvcRequestBuilders.get("/sistema/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO)));

		verify(service, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10);
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_historico_lista_vazia_ok() throws Exception {
		when(service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10)).thenReturn(PaginacaoResponseDto.fromResultado(List.of(), 1, 1, 1));

	    mockMvc.perform(MockMvcRequestBuilders.get("/sistema/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_VAZIA)));

		verify(service, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"), 1, 10);
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_save_error_nome_null() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    SistemaRequestDto request = SistemaRequestDtoTest.getRequest();
	    request.setNome(null);
		String jsonBody = objectMapper.writeValueAsString(request);

	    mockMvc.perform(MockMvcRequestBuilders.post("/sistema")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).save(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_save_error_nome_vazio() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    SistemaRequestDto request = SistemaRequestDtoTest.getRequest();
	    request.setNome("");
		String jsonBody = objectMapper.writeValueAsString(request);

	    mockMvc.perform(MockMvcRequestBuilders.post("/sistema")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).save(any());
		verifyNoMoreInteractions(service);
	}
}