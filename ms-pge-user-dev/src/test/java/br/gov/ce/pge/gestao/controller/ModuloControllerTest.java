package br.gov.ce.pge.gestao.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;
import java.util.UUID;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.ModuloRequestDto;
import br.gov.ce.pge.gestao.dto.request.ModuloRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDtoTest;
import br.gov.ce.pge.gestao.entity.ModuloTest;
import br.gov.ce.pge.gestao.service.ModuloService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class ModuloControllerTest {

	@InjectMocks
	ModuloController controller;

	@Mock
	ModuloService service;
	
	@Mock
	GlobalExceptionHandler globalExceptionHandler;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}
	
	@Test
	public void test_save_ok() throws Exception {
		when(service.save(any())).thenReturn(ModuloTest.getModulo());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(ModuloRequestDtoTest.getRequest());

	    mockMvc.perform(MockMvcRequestBuilders.post("/modulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"origem debito\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO)));

	    verify(service, times(1)).save(any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_consulta_por_id_ok() throws Exception {
		when(service.findById(any())).thenReturn(ModuloTest.getModulo());

	    mockMvc.perform(MockMvcRequestBuilders.get("/modulo/{id}", "5027c7f7-622b-4161-ac75-97c9110553f2")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"5027c7f7-622b-4161-ac75-97c9110553f2\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"origem debito\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

	    verify(service, times(1)).findById(any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_list_all_ok() throws Exception {
		when(service.findAll()).thenReturn(ModuloTest.getListModulo());

	    mockMvc.perform(MockMvcRequestBuilders.get("/modulo/")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

	    verify(service, times(1)).findAll();
		verifyNoMoreInteractions(service);

	}
	
	@Test
	public void test_update_ok() throws Exception {
		when(service.update(any(),any())).thenReturn(ModuloTest.getModuloUpdate());

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(ModuloRequestDtoTest.getRequestUpdate());

	    mockMvc.perform(MockMvcRequestBuilders.put("/modulo/{id}", "5027c7f7-622b-4161-ac75-97c9110553f2")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"5027c7f7-622b-4161-ac75-97c9110553f2\"}}"))
	            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"origem debito update\"}}"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

	    verify(service, times(1)).update(any(),any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_cadastro_debito_origem_sem_nome_error() throws Exception {
		
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonBody = objectMapper.writeValueAsString(ModuloRequestDtoTest.getRequestSemNome());

	    mockMvc.perform(MockMvcRequestBuilders.post("/modulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());
	    
	    verify(service, times(0)).update(any(),any());
	}

	@Test
	public void test_delete_ok() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/modulo/{id}", "5027c7f7-622b-4161-ac75-97c9110553f2")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO)));

		verify(service, times(1)).delete(any());
		verifyNoMoreInteractions(service);

	}
	
	@Test
	public void test_historico_ok() throws Exception {
		when(service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1)).thenReturn(PaginacaoResponseDtoTest.getPaginacaoHistoricoModulo());

	    mockMvc.perform(MockMvcRequestBuilders.get("/modulo/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO)));

	    verify(service, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1);
		verifyNoMoreInteractions(service);

	}
	
	@Test
	public void test_historico_lista_vazia_ok() throws Exception {
		when(service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1)).thenReturn(PaginacaoResponseDto.fromResultado(List.of(), 1, 1, 1));

	    mockMvc.perform(MockMvcRequestBuilders.get("/modulo/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_VAZIA)));

	    verify(service, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1);
		verifyNoMoreInteractions(service);

	}
	
	@Test
	public void test_save_error_nome_null() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    ModuloRequestDto request = ModuloRequestDtoTest.getRequest();
	    request.setNome(null);
		String jsonBody = objectMapper.writeValueAsString(request);

	    mockMvc.perform(MockMvcRequestBuilders.post("/modulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).save(any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_save_error_nome_vazio() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    ModuloRequestDto request = ModuloRequestDtoTest.getRequest();
	    request.setNome("");
		String jsonBody = objectMapper.writeValueAsString(request);

	    mockMvc.perform(MockMvcRequestBuilders.post("/modulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).save(any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_save_error_permissoes_vazio() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    ModuloRequestDto request = ModuloRequestDtoTest.getRequest();
	    request.setPermissoes(List.of());
		String jsonBody = objectMapper.writeValueAsString(request);

	    mockMvc.perform(MockMvcRequestBuilders.post("/modulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).save(any());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void test_save_error_permissoes_null() throws Exception {

	    ObjectMapper objectMapper = new ObjectMapper();
	    ModuloRequestDto request = ModuloRequestDtoTest.getRequest();
	    request.setPermissoes(null);
		String jsonBody = objectMapper.writeValueAsString(request);

	    mockMvc.perform(MockMvcRequestBuilders.post("/modulo")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(jsonBody))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest());

	    verify(service, times(0)).save(any());
		verifyNoMoreInteractions(service);
	}
}
