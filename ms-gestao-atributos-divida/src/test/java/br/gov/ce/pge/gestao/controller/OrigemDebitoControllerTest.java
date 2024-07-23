package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class OrigemDebitoControllerTest {

	@InjectMocks
	OrigemDebitoController controller;

	@Mock
	OrigemDebitoService service;

	MockMvc mockMvc;

	@Mock
	HttpServletRequest httpRequest;

	@Mock
	GlobalExceptionHandler global;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	public void test_cadastro_debito_origem_ok() throws Exception {
		when(service.save(any(), anyString())).thenReturn(OrigemDebitoResponseDtoTest.getResponse());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(OrigemDebitoRequestDtoTest.getRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/origem-debito")
	    		.header("nomeUsuario", "daniel")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"ATIVA\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Origens do Débito.\"}"));

	    verify(service, times(1)).save(any(), anyString());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_consulta_por_id_ok() throws Exception {
		when(service.findById(any())).thenReturn(OrigemDebitoResponseDtoTest.getResponse());

		mockMvc.perform(MockMvcRequestBuilders.get("/origem-debito/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Origem débito encontrada\"}"));

	    verify(service, times(1)).findById(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_list_all_ok() throws Exception {
		when(service.findAll()).thenReturn(Arrays.asList(OrigemDebitoResponseDtoTest.getResponse()));

		mockMvc.perform(MockMvcRequestBuilders.get("/origem-debito/")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Origem débito encontrada\"}"));

	    verify(service, times(1)).findAll();
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_update_debito_origem_ok() throws Exception {
		when(service.update(any(),any(),anyString())).thenReturn(OrigemDebitoResponseDtoTest.getResponseUpdate());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(OrigemDebitoRequestDtoTest.getRequestUpdate());

		mockMvc.perform(MockMvcRequestBuilders.put("/origem-debito/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
	    		.header("nomeUsuario", "daniel")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"INATIVA\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("Origem débito alterada com sucesso")));

	    verify(service, times(1)).update(any(),any(),anyString());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_update_debito_origem_error() throws Exception {
		when(httpRequest.getHeader("nomeUsuario")).thenReturn("daniel");
	    UUID id = UUID.randomUUID(); // Defina um ID válido para o teste
	    OrigemDebitoRequestDto requestDto = new OrigemDebitoRequestDto(); // Crie um objeto válido para o teste

	    doThrow(new NegocioException("O registro já foi cadastrado anteriormente, com o nome: junit"))
	        .when(service).update(eq(id), any(OrigemDebitoRequestDto.class), eq("daniel"));

		NegocioException exception = assertThrows(NegocioException.class, () ->
						controller.update(id, requestDto, httpRequest)
	    );

	    assertEquals("O registro já foi cadastrado anteriormente, com o nome: junit", exception.getMessage());
	}

	@Test
	public void test_cadastro_debito_origem_sem_nome_error() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(OrigemDebitoRequestDtoTest.getRequestSemNome());

		mockMvc.perform(MockMvcRequestBuilders.post("/origem-debito")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

		verify(service, times(0)).update(any(), any(), anyString());
	}

	@Test
	public void test_cadastro_origem_debito_sem_descricao_error() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(OrigemDebitoRequestDtoTest.getRequestSemDescricao());

		mockMvc.perform(MockMvcRequestBuilders.post("/origem-debito")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		verify(service, times(0)).update(any(),any(),anyString());
	}

	@Test
	public void test_consulta_historico_por_id_ok() throws Exception {
		when(service.findHistorys(any(UUID.class), eq(1), eq(10))).thenReturn(HistoricoAtualizacaoDtoTest.getResponse());

		mockMvc.perform(MockMvcRequestBuilders.get("/origem-debito/historico/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data.totalRegistros").value(1))
				.andExpect(jsonPath("$.data.totalPaginas").value(1))
				.andExpect(jsonPath("$.data.paginaAtual").value(1))
				.andExpect(jsonPath("$.data.resultado[0].dadosAlterados[0].campoAtualizado").value("situacao"))
				.andExpect(jsonPath("$.data.resultado[0].dadosAlterados[0].valorAntigo").value("ATIVA"))
				.andExpect(jsonPath("$.data.resultado[0].dadosAlterados[0].valorNovo").value("INATIVA"))
				.andExpect(jsonPath("$.data.resultado[0].responsavel").value("anonimo"))
				.andExpect(jsonPath("$.data.resultado[0].dataAlterado").value("21/11/2023 10:57:31"))
				.andExpect(jsonPath("$.mensagem").value("Históricos do débito encontrados"))
				.andDo(MockMvcResultHandlers.print());

		verify(service, times(1)).findHistorys(any(UUID.class), eq(1), eq(10));
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_historico_lista_vazia_ok() {
		doThrow(new HistoricoAtualizacaoNotFoundException("Origem de Débito selecionada não possui histórico de edições.")).when(service).findHistorys(any(), eq(0), eq(0));

		HistoricoAtualizacaoNotFoundException exception = assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			controller.viewHistoryById(any(), eq(0), eq(0));
		});

		assertEquals("Origem de Débito selecionada não possui histórico de edições.", exception.getMessage());

		verify(service).findHistorys(any(), eq(0), eq(0));
	}

	@Test
	public void test_consulta_por_filtro_ok() throws Exception {
		when(service.findOrigemDebitoByFilter(any())).thenReturn(Arrays.asList(OrigemDebitoConsultaResponseDtoTest.getOrigemDebito()));

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(OrigemDebitoConsultaResponseDtoTest.getOrigemDebito());

		mockMvc.perform(MockMvcRequestBuilders.post("/origem-debito/filtros")
						.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.data[0].id").value("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"))
				.andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Origem débito encontrada com sucesso\"}"));

		verify(service, times(1)).findOrigemDebitoByFilter(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void test_filter_lista_vazia_ok() throws Exception {
		when(service.findOrigemDebitoByFilter(OrigemDebitoFilterRequestDtoTest.getFilterRequest())).thenReturn(Arrays.asList());

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(OrigemDebitoFilterRequestDtoTest.getFilterRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/origem-debito/filtros")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.")));

		verify(service, times(1)).findOrigemDebitoByFilter(OrigemDebitoFilterRequestDtoTest.getFilterRequest());

	}

	@Test
	public void test_delete_ok() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/origem-debito/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
						.contentType(MediaType.APPLICATION_JSON).header("nomeUsuario", "Paulo"))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Origem Débito deletado com sucesso\"}"));

		verify(service, times(1)).delete(any(), anyString());
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_delete_error() {
		doThrow(new NegocioException("Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.")).when(service).delete(any(),anyString());

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("nomeUsuario", "Paulo");

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.delete(UUID.randomUUID(), request);
		});

		assertEquals("Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.", exception.getMessage());

		verify(service).delete(any(), anyString());
	}

	@Test
	public void test_cadastro_debito_origem_error() throws Exception {
		when(httpRequest.getHeader("nomeUsuario")).thenReturn("daniel");
		doThrow(new NegocioException("O registro já foi cadastrado anteriormente, com o nome: junit")).when(service).save(any(), eq("daniel"));

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.save(any(), httpRequest);
		});

		assertEquals("O registro já foi cadastrado anteriormente, com o nome: junit", exception.getMessage());

		verify(service).save(any(), eq("daniel"));
	}

}
