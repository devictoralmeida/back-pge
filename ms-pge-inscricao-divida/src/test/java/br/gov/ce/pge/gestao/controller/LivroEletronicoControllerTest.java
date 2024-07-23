package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class LivroEletronicoControllerTest {

    @Mock
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    @Mock
    private LivroEletronicoService service;

    @Mock
    private RegistroLivroService registroLivroService;

    @InjectMocks
    private LivroEletronicoController controller;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
    }

    @Test
    void test_filter_lista_vazia_ok() throws Exception {
        when(service.findByFilter(any())).thenReturn(Collections.emptyList());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(LivroEletronicoFilterRequestDtoTest.getRequestFilter());

        mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/filtros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA)));

        verify(service, times(1)).findByFilter(any());
    }

    @Test
    void test_filter_lista_nao_vazia_ok() throws Exception {

        when(service.findByFilter(any())).thenReturn(LivroEletronicoFilterResponseDtoTest.get_livro_eletronico_filters_list());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(LivroEletronicoFilterRequestDtoTest.getRequestFilter());

        mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/filtros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO)));

        verify(service, times(1)).findByFilter(any());
    }

    @Test
    void test_find_by_id_livro() throws Exception {

        when(service.findById(any())).thenReturn(LivroEletronicoResponseDtoTest.get_livro_eletronico_response());

        mockMvc.perform(MockMvcRequestBuilders.get("/livro-eletronico/{id}", "3b2469d6-8951-44be-afed-06c14824d300")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO)))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"3b2469d6-8951-44be-afed-06c14824d300\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"Meu Livro Eletrônico\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"ABERTO\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"paginas\": 1}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"totalLinhasUltimaPagina\": 10}}"));

        verify(service, times(1)).findById(any());
    }

    @Test
    void test_inicia_livro() throws Exception {

        doNothing().when(service).abertura();

        mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/inicia"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.LIVRO_INICIADO)));;

        verify(service, times(1)).abertura();
    }

    @Test
    void test_fecha_livro() throws Exception {

        doNothing().when(service).fechamento();

        mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/fecha"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.LIVRO_FECHADO)));;

        verify(service, times(1)).fechamento();
    }

    @Test
    void test_filter_registro_livro_ok() throws Exception {

        when(registroLivroService.findByFilterRegistroInscricao(any(), any(), any(), any())).thenReturn(PaginacaoResponseDtoTest.getPaginacaoFilterRegistroInscricaoLinha());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(RegistroLivroFilterRequestDtoTest.getRequestFilterLinha());

        mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/filtros-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO)));

        verify(registroLivroService, times(1)).findByFilterRegistroInscricao(any(), any(), any(), any());
    }

    @Test
    void test_filter_registro_livro_vazia_ok() throws Exception {

        when(registroLivroService.findByFilterRegistroInscricao(any(), any(), any(), any())).thenReturn(PaginacaoResponseDtoTest.getPaginacaoFilterRegistroInscricaoVazio());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(RegistroLivroFilterRequestDtoTest.getRequestFilterLinha());

        mockMvc.perform(MockMvcRequestBuilders.post("/livro-eletronico/filtros-registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA)));

        verify(registroLivroService, times(1)).findByFilterRegistroInscricao(any(), any(), any(), any());
    }
}
