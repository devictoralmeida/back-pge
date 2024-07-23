package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.AcaoJudicialTest;
import br.gov.ce.pge.gestao.service.AcaoJudicialService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
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

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith({MockitoExtension.class})
@Import(GlobalExceptionHandler.class)
class AcaoJudicialControllerTest {
  MockMvc mockMvc;

  @Mock
  private AcaoJudicialService service;

  @InjectMocks
  private AcaoJudicialController controller;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
  }

  @Test
  void test_list_all_ok() throws Exception {
    when(service.findAll()).thenReturn(Arrays.asList(AcaoJudicialTest.getAcaoJudicial()));

    mockMvc.perform(MockMvcRequestBuilders.get("/acao-judicial")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

    verify(service, times(1)).findAll();
    verifyNoMoreInteractions(service);

  }

  @Test
  void test_find_by_id() throws Exception {

    when(service.findByIdModel(any())).thenReturn(AcaoJudicialTest.getAcaoJudicial());

    mockMvc.perform(MockMvcRequestBuilders.get("/acao-judicial/{id}", "4b5746a6-b42b-4af3-ae09-2bbf820a3479")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO)))
            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"4b5746a6-b42b-4af3-ae09-2bbf820a3479\"}}"));

    verify(service, times(1)).findByIdModel(any());
  }
}
