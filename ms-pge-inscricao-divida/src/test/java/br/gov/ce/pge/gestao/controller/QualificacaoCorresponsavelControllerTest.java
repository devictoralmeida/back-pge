package br.gov.ce.pge.gestao.controller;


import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.QualificacaoCorresponsavelTest;
import br.gov.ce.pge.gestao.service.QualificacaoCorresponsavelService;
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


@ExtendWith({MockitoExtension.class})
@Import(GlobalExceptionHandler.class)
class QualificacaoCorresponsavelControllerTest {
  MockMvc mockMvc;

  @Mock
  private QualificacaoCorresponsavelService service;

  @InjectMocks
  private QualificacaoCorresponsavelController controller;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
  }

  @Test
  void test_list_all_ok() throws Exception {
    when(service.findAll()).thenReturn(Arrays.asList(QualificacaoCorresponsavelTest.getQualificacao()));

    mockMvc.perform(MockMvcRequestBuilders.get("/qualificacao-corresponsavel")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

    verify(service, times(1)).findAll();
    verifyNoMoreInteractions(service);

  }
}