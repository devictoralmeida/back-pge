package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDtoTest;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class LivroEletronicoControllerTest {

  @Mock
  GlobalExceptionHandler globalExceptionHandler;

  MockMvc mockMvc;

  @Mock
  private LivroEletronicoService service;

  @InjectMocks
  private LivroEletronicoController controller;

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).alwaysDo(print()).build();
  }

  @Test
  void test_filter_lista_vazia_ok() throws Exception {
    when(this.service.findByFilter(any())).thenReturn(Collections.emptyList());

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(LivroEletronicoFilterRequestDtoTest.getRequestFilter());

    this.mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/filtros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA)));

    verify(this.service, times(1)).findByFilter(any());
  }

  @Test
  void test_filter_lista_nao_vazia_ok() throws Exception {

    when(this.service.findByFilter(any())).thenReturn(LivroEletronicoFilterResponseDtoTest.getLivroEletronicoFilterList());

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(LivroEletronicoFilterRequestDtoTest.getRequestFilter());

    this.mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/filtros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO)));

    verify(this.service, times(1)).findByFilter(any());
  }

}
