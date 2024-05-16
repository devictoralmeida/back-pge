package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.CondicaoAcessoRequestDtoTest;
import br.gov.ce.pge.gestao.entity.CondicaoAcessoTest;
import br.gov.ce.pge.gestao.service.CondicaoAcessoService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith({MockitoExtension.class})
@Import(GlobalExceptionHandler.class)
class CondicaoAcessoControllerTest {
    @InjectMocks
    CondicaoAcessoController controller;

    @Mock
    CondicaoAcessoService service;

    @Mock
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
    }

    @Test
    public void test_save_ok() throws Exception {
        when(service.save(any())).thenReturn(CondicaoAcessoTest.getCondicaoAcesso());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(CondicaoAcessoRequestDtoTest.getRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/condicao-acesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"bloqueioAutomatico\": 1}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"alteracaoSenha\": 1}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"encerramentoSessao\": \"12:34\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"tentativasInvalidas\": 1}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"senhasCadastradas\": 1}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO)));

        verify(service, times(1)).save(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_list_all() throws Exception {
        when(service.findAll()).thenReturn(CondicaoAcessoTest.getListCondicaoAcesso());

        mockMvc.perform(MockMvcRequestBuilders.get("/condicao-acesso/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_update() throws Exception {
        when(service.update(any(), any())).thenReturn(CondicaoAcessoTest.getCondicaoAcessoUpdate());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(CondicaoAcessoRequestDtoTest.getRequestUpdate());

        mockMvc.perform(MockMvcRequestBuilders.put("/condicao-acesso/{id}", "5027c7f7-622b-4161-ac75-97c9110553f2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"5027c7f7-622b-4161-ac75-97c9110553f2\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"bloqueioAutomatico\": 2}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"alteracaoSenha\": 14}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"encerramentoSessao\": \"43:21\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"tentativasInvalidas\": 3}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"senhasCadastradas\": 15}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

        verify(service, times(1)).update(any(), any());
        verifyNoMoreInteractions(service);
    }
}