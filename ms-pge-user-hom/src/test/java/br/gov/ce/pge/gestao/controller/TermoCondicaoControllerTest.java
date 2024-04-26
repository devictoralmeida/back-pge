package br.gov.ce.pge.gestao.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDtoTest;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDtoTest;
import br.gov.ce.pge.gestao.service.TermoCondicaoService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class TermoCondicaoControllerTest {

	@InjectMocks
	@Autowired
	private TermoCondicaoController controller;

	@Mock
	private TermoCondicaoService service;

	@Mock
	GlobalExceptionHandler globalExceptionHandler;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	void update_sucesso() throws Exception {
		doNothing().when(service).update(any(UUID.class), any(TermoCondicaoRequestDto.class));

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(TermoCondicaoRequestDtoTest.getRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/termos-condicoes/6cb0b4a6-03bd-40a7-b899-efcbbaa4b404")
				.contentType(MediaType.APPLICATION_JSON).content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.mensagem",
						is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

		verify(service, times(1)).update(any(), any());
		verifyNoMoreInteractions(service);

	}

	@Test
	void update_sucesso_sem_versao() throws Exception {
		doNothing().when(service).updateExistente(any(UUID.class), any(TermoCondicaoRequestDto.class));

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(TermoCondicaoRequestDtoTest.getRequest());

		mockMvc.perform(MockMvcRequestBuilders.put("/termos-condicoes/6cb0b4a6-03bd-40a7-b899-efcbbaa4b404")
						.contentType(MediaType.APPLICATION_JSON).content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.mensagem",
						is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

		verify(service, times(1)).updateExistente(any(), any());
		verifyNoMoreInteractions(service);

	}

	@Test
	void test_consulta_por_id_ok() throws Exception {
		when(service.findById(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404")))
				.thenReturn(TermoCondicaoResponseDtoTest.getTermoCondicaoResponseDto());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/termos-condicoes/6cb0b4a6-03bd-40a7-b899-efcbbaa4b404").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem",
						is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

		verify(service, times(1)).findById(any());
		verifyNoMoreInteractions(service);
	}
	
	
	@Test
	void test_termos_por_sistema() throws Exception {
		when(service.findBySistema()).thenReturn(TermoCondicaoSistemaResponseDtoTest.listaTermoCondicaoSistemaResponseDto());

		mockMvc.perform(MockMvcRequestBuilders
				.get("/termos-condicoes/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem",
						is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

		verify(service, times(1)).findBySistema();
		verifyNoMoreInteractions(service);
	}

	@Test
	void test_historico_ok() throws Exception {
		when(service.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1)).thenReturn(PaginacaoResponseDtoTest.getPaginacaoHistoricoTermo());

		mockMvc.perform(MockMvcRequestBuilders.get("/termos-condicoes/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO)));

		verify(service, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1);
		verifyNoMoreInteractions(service);

	}

	@Test
	public void test_historico_lista_vazia_ok() {
		doThrow(new HistoricoAtualizacaoNotFoundException("O termo selecionado não possui histórico de edições.")).when(service).findHistorys(any(), eq(0));

		HistoricoAtualizacaoNotFoundException exception = assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
			controller.viewHistoryById(any(), eq(0));
		});

		assertEquals("O termo selecionado não possui histórico de edições.", exception.getMessage());

		verify(service).findHistorys(any(), eq(0));

	}

}
