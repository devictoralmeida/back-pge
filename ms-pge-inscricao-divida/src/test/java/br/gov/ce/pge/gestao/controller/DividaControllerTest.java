package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.*;
import br.gov.ce.pge.gestao.dto.response.AcaoJudicialDividaResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.DividaResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDtoTest;
import br.gov.ce.pge.gestao.service.*;
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

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith({MockitoExtension.class})
@Import(GlobalExceptionHandler.class)
class DividaControllerTest {
  @Mock
  GlobalExceptionHandler globalExceptionHandler;

  MockMvc mockMvc;

  @Mock
  private DividaService service;

  @Mock
  private DividaConsultaService consultaService;

  @InjectMocks
  private DividaController controller;

  @Mock
  private AcaoJudicialService acaoJudicialService;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
  }

  @Test
  void test_save_ok() throws Exception {
    doNothing().when(service).save(any());

    DividaRequestDto request = DividaRequestDtoTest.getDivida();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String jsonBody = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/divida/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_SAVE_SUCESSO + " de Inscrições na Dívida Ativa.")));

    verify(service, times(1)).save(any());
    verifyNoMoreInteractions(service);
  }

  @Test
  void test_save_retornar_bad_request_nenhum_devedor_enviado() throws Exception {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    request.setPessoas(null);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String jsonBody = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/divida/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, times(0)).save(any());
    verifyNoMoreInteractions(service);
  }

  @Test
  void test_save_retornar_bad_request_nenhum_debito_enviado() throws Exception {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    request.setDebitos(null);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    String jsonBody = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/divida/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());

    verify(service, times(0)).save(any());
    verifyNoMoreInteractions(service);
  }

  @Test
  void test_filter_divida_ok() throws Exception {

    when(consultaService.findDividaByFilter(any(), any(), any(), any())).thenReturn(PaginacaoResponseDtoTest.getDividaFilter());

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(DividaFilterRequestDtoTest.getRequest());

    mockMvc.perform(MockMvcRequestBuilders.post("/divida/filtros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO)));

    verify(consultaService, times(1)).findDividaByFilter(any(), any(), any(), any());
  }

  @Test
  void test_filter_divida_vazia_ok() throws Exception {

    when(consultaService.findDividaByFilter(any(), any(), any(), any())).thenReturn(PaginacaoResponseDtoTest.getDividaFilterVazio());

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(DividaFilterRequestDtoTest.getRequestFilterCgf());

    mockMvc.perform(MockMvcRequestBuilders.post("/divida/filtros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA)));

    verify(consultaService, times(1)).findDividaByFilter(any(), any(), any(), any());
  }

  @Test
  void test_find_by_id() throws Exception {

    when(consultaService.findById(any())).thenReturn(DividaResponseDtoTest.getResponse());

    mockMvc.perform(MockMvcRequestBuilders.get("/divida/{id}", "e93fd062-c46e-4dfa-ad87-67ab0300dd37")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO)))
            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"e93fd062-c46e-4dfa-ad87-67ab0300dd37\"}}"));

    verify(consultaService, times(1)).findById(any());
  }

  @Test
  void test_atualizar_fase() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(AtualizaFaseRequestDtoTest.getRequest());

    mockMvc.perform(MockMvcRequestBuilders.patch("/divida/atualiza-fase")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MSG_ATUALIZA_FASE)));

    verify(service, times(1)).atualizarFase(any());

  }

  @Test
  void test_find_by_numero_inscricao() throws Exception {
    when(consultaService.findByNumeroInscricao(any())).thenReturn(DividaResponseDtoTest.getResponse());

    mockMvc.perform(MockMvcRequestBuilders.get("/divida/inscricao/{id}", "2024000000001")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO)))
            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"e93fd062-c46e-4dfa-ad87-67ab0300dd37\"}}"));

    verify(consultaService, times(1)).findByNumeroInscricao(any());
  }

  @Test
  void test_find_acoes_judiciais() throws Exception {
    when(consultaService.findAcoesJudiciaisByDivida(any())).thenReturn(AcaoJudicialDividaResponseDtoTest.getAcaoJudicialDividaResponseDto());

    mockMvc.perform(MockMvcRequestBuilders.get("/divida/acao-judicial/{id}", "6fa3e795-0474-400c-8ef1-fa80262b3ae4")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO)))
            .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"6fa3e795-0474-400c-8ef1-fa80262b3ae4\"}}"));

    verify(consultaService, times(1)).findAcoesJudiciaisByDivida(any());
  }

  @Test
  void test_find_by_ids() throws Exception {

    when(consultaService.findByIds(any())).thenReturn(List.of(DividaResponseDtoTest.getResponse()));

    mockMvc.perform(MockMvcRequestBuilders.get("/divida/?ids=e93fd062-c46e-4dfa-ad87-67ab0300dd37")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

    verify(consultaService, times(1)).findByIds(any());
  }

  @Test
  void test_registrar_acao_judicial() throws Exception {

    UUID id = UUID.randomUUID();
    doNothing().when(service).registrarAcao(any(AcaoJudicialRequestDto.class), any(UUID.class));

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonBody = objectMapper.writeValueAsString(AcaoJudicialRequestDtoTest.getRequest());

    mockMvc.perform(MockMvcRequestBuilders.patch("/divida/acao-judicial/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MSG_ATUALIZA_FASE)));

    verify(service, times(1)).registrarAcao(any(AcaoJudicialRequestDto.class), eq(id));
  }

}
