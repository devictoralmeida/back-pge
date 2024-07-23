package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDto;
import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDtoTest;
import br.gov.ce.pge.gestao.service.ConsultaPessoaService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith({MockitoExtension.class})
@Import(GlobalExceptionHandler.class)
class PessoaControllerTest {
  @Mock
  GlobalExceptionHandler globalExceptionHandler;

  MockMvc mockMvc;

  @Mock
  private ConsultaPessoaService service;

  @InjectMocks
  private PessoaController controller;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
  }

  @Test
  void test_consulta_documento_ok() throws Exception {
    when(service.findByDocumento(any())).thenReturn(PessoaResponseDtoTest.getPessoa());

    DocumentoRequestDto request = DocumentoRequestDtoTest.getDocumento();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String jsonBody = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/pessoa/documento")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO)));

    verify(service, times(1)).findByDocumento(any());
    verifyNoMoreInteractions(service);
  }

  @Test
  void test_consulta_documento_retorno_null() throws Exception {
    when(service.findByDocumento(any())).thenReturn(null);

    DocumentoRequestDto request = DocumentoRequestDtoTest.getDocumento();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String jsonBody = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/pessoa/documento")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_NAO_ENCONTRADO)));

    verify(service, times(1)).findByDocumento(any());
    verifyNoMoreInteractions(service);
  }
}
