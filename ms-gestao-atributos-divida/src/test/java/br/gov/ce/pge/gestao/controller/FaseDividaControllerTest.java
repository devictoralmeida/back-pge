package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.FluxoFaseDividaResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoDtoTest;
import br.gov.ce.pge.gestao.service.FaseDividaService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
public class FaseDividaControllerTest {

    @InjectMocks
    FaseDividaController controller;

    @Mock
    FaseDividaService service;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
    }

    @Test
    public void test_cadastro_fase_divida_ok() throws Exception {
        when(service.save(any(), anyString())).thenReturn(FaseDividaResponseDtoTest.getResponse());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(FaseDividaRequestDtoTest.getRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/fase-divida/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("nomeUsuario", "Paulo")
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"ATIVA\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Fases da Dívida.\"}"));

        verify(service, times(1)).save(any(), anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_cadastro_error() throws Exception {
        doThrow(new NegocioException("O nome da fase é igual ao nome da fase junit já cadastrada.")).when(service).save(any(),anyString());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("nomeUsuario", "Paulo");

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            controller.save(FaseDividaRequestDtoTest.getRequest(), request);
        });

        assertEquals("O nome da fase é igual ao nome da fase junit já cadastrada.", exception.getMessage());

        verify(service).save(any(), anyString());
    }

    @Test
    public void test_consulta_por_id_ok() throws Exception {
        when(service.findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/fase-divida/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Fase da dívida encontrada\"}"));

        verify(service, times(1)).findById(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_list_all_ok() throws Exception {
        when(service.findAll()).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/fase-divida/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Fases da dívida encontradas\"}"));

        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_semelhantes_ok() throws Exception {
        when(service.findSemelhantes(any())).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/fase-divida/semelhantes")
                        .param("nome", "Teste Junit")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Fases da dívida semelhantes encontradas\"}"));

        verify(service, times(1)).findSemelhantes(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_update_fase_divida_ok() throws Exception {
        when(service.update(any(),any(),anyString())).thenReturn(FaseDividaResponseDtoTest.getResponseUpdate());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(FaseDividaRequestDtoTest.getRequestUpdate());

        mockMvc.perform(MockMvcRequestBuilders.put("/fase-divida/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("nomeUsuario", "Paulo")
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"situacao\": \"INATIVA\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"Fase Cadastrada Atualizada\"}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("O registro foi salvo com sucesso!")));

        verify(service, times(1)).update(any(),any(),anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_update_error() throws Exception {
        doThrow(new NegocioException("O nome da fase é igual ao nome da fase Teste Junit já cadastrada.")).when(service).update(any(), any(), anyString());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("nomeUsuario", "Paulo");

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            controller.update(UUID.randomUUID(), FaseDividaRequestDtoTest.getRequest(), request);
        });

        assertEquals("O nome da fase é igual ao nome da fase Teste Junit já cadastrada.", exception.getMessage());

        verify(service).update(any(), any(),anyString());
    }

    @Test
    public void test_cadastro_fase_divida_sem_nome_error() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(FaseDividaRequestDtoTest.getRequestSemNome());

        mockMvc.perform(MockMvcRequestBuilders.post("/fase-divida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).update(any(),any(),anyString());
    }

    @Test
    public void test_consulta_por_filtro_ok() throws Exception {
        when(service.findByFilter(any())).thenReturn(List.of(FaseDividaResponseDtoTest.getResponse()));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(FaseDividaFilterRequestDtoTest.getFilterRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/fase-divida/filtros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"))
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Fases da dívida encontrada com sucesso\"}"));

        verify(service, times(1)).findByFilter(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_filter_lista_vazia_ok() throws Exception {
        when(service.findByFilter(FaseDividaFilterRequestDtoTest.getFilterRequest())).thenReturn(Arrays.asList());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(FaseDividaFilterRequestDtoTest.getFilterRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/fase-divida/filtros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is("Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado.")));

        verify(service, times(1)).findByFilter(FaseDividaFilterRequestDtoTest.getFilterRequest());
    }

    @Test
    public void test_delete_ok() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/fase-divida/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
                        .contentType(MediaType.APPLICATION_JSON).header("nomeUsuario", "Paulo"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Fase da dívida deletada com sucesso\"}"));

        verify(service, times(1)).delete(any(),anyString());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_consulta_historico_por_id_ok() throws Exception {
        when(service.viewChangeHistoryById(any(UUID.class), eq(1), eq(10))).thenReturn(HistoricoAtualizacaoDtoTest.getResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/fase-divida/historico/{id}", "a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")
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
                .andExpect(jsonPath("$.mensagem").value("Históricos da fase da dívida encontrada"))
                .andDo(MockMvcResultHandlers.print());

        verify(service, times(1)).viewChangeHistoryById(any(UUID.class), eq(1), eq(10));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_get_fluxo_fases_ok() throws Exception {
        when(service.obterFluxoFase()).thenReturn(List.of(FluxoFaseDividaResponseDtoTest.getFluxoFase()));

        mockMvc.perform(MockMvcRequestBuilders.get("/fase-divida/fluxo-fase")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"Fases da dívida encontradas\"}"));

        verify(service, times(1)).obterFluxoFase();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void save_fluxo_ok() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(FaseDividaFilterRequestDtoTest.getFilterRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/fase-divida/fluxo-fase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().json("{\"mensagem\": \"O registro foi salvo com sucesso!\"}"));

        verify(service, times(1)).salvarFluxoFases(any());
        verifyNoMoreInteractions(service);
    }
}
