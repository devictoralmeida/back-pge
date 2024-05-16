package br.gov.ce.pge.gestao.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
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
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDtoTest;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.UsuarioInternoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.UsuarioResponseDtoTest;
import br.gov.ce.pge.gestao.entity.RequisicaoLogar;
import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.service.EnviarEmailService;
import br.gov.ce.pge.gestao.service.PortalColaboradorService;
import br.gov.ce.pge.gestao.service.RequisicaoLogarService;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;
import br.gov.ce.pge.gestao.service.UsuarioService;
import br.gov.ce.pge.gestao.shared.exception.GlobalExceptionHandler;
import br.gov.ce.pge.gestao.shared.exception.HistoricoAtualizacaoNotFoundException;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class UsuarioControllerTest {

    @InjectMocks
    UsuarioController controller;

    @Mock
    UsuarioService service;

    @Mock
    UsuarioConsultaService consultaService;

    @Mock
    PortalColaboradorService portalService;

    @Mock
    EnviarEmailService enviarEmailService;

    @Mock
    RequisicaoLogarService logarService;

    @Mock
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
    }

    @Test
    void test_save_ok() throws Exception {
        doNothing().when(service).save(any());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioRequestDtoTest.getRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO + " de Usuário")));

        verify(service, times(1)).save(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void test_save_usuario_sem_nome_error() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioRequestDtoTest.getRequestSemNome());

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(service, times(0)).save(any());
    }

    @Test
    void test_consulta_por_id_ok() throws Exception {
        when(consultaService.findById(any())).thenReturn(UsuarioResponseDtoTest.getUsuarioResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{id}", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"c4095434-f704-4209-be74-3d42d519d438\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"teste usuario\"}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

        verify(consultaService, times(1)).findById(any());
        verifyNoMoreInteractions(consultaService);
    }

    @Test
    void test_delete_ok() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/usuario/{id}", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO)));

        verify(service, times(1)).delete(any());
        verifyNoMoreInteractions(service);

    }

    @Test
    void test_update_ok() throws Exception {
        doNothing().when(service).update(any(),any());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioRequestDtoTest.getRequest());

        mockMvc.perform(MockMvcRequestBuilders.put("/usuario/{id}", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

        verify(service, times(1)).update(any(),any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void test_filter_ok() throws Exception {
        when(consultaService.findByFilter(UsuarioFilterRequestDtoTest.getRequestFilter(),1, "nome")).thenReturn(PaginacaoResponseDtoTest.getPaginacaoFilterUsuario());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioFilterRequestDtoTest.getRequestFilter());

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/filtros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO)));

        verify(consultaService, times(1)).findByFilter(UsuarioFilterRequestDtoTest.getRequestFilter(),1, "nome");

    }

    @Test
    void test_filter_lista_vazia_ok() throws Exception {
        when(consultaService.findByFilter(UsuarioFilterRequestDtoTest.getRequestFilter(),1, "nome")).thenReturn(PaginacaoResponseDto.fromResultado(List.of(), 1, 1, 1));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(PerfilAcessoFilterRequestDtoTest.getRequestFilter());

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/filtros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA)));

        verify(consultaService, times(1)).findByFilter(UsuarioFilterRequestDtoTest.getRequestFilter(),1, "nome");

    }

    @Test
    void test_historico_ok() throws Exception {
        when(consultaService.findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1)).thenReturn(PaginacaoResponseDtoTest.getPaginacaoHistoricoUsuario());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/historico/{id}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO)));

        verify(consultaService, times(1)).findHistorys(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"),1);
        verifyNoMoreInteractions(consultaService);

    }

    @Test
    public void test_historico_lista_vazia_ok() {
        doThrow(new HistoricoAtualizacaoNotFoundException("O usuário selecionado não possui histórico de edições.")).when(consultaService).findHistorys(any(), eq(0));

        HistoricoAtualizacaoNotFoundException exception = assertThrows(HistoricoAtualizacaoNotFoundException.class, () -> {
            controller.viewHistoryById(any(), eq(0));
        });

        assertEquals("O usuário selecionado não possui histórico de edições.", exception.getMessage());

        verify(consultaService).findHistorys(any(), eq(0));

    }

    @Test
    void test_consulta_por_cpf_portal_colaborador() throws Exception {
        when(portalService.getColaborador(any())).thenReturn(UsuarioInternoResponseDtoTest.getUsuarioInterno());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/interno/{cpf}", "00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"area\": \"area teste\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"teste usuario\"}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_CPF_SUCESSO)));

        verify(portalService, times(1)).getColaborador(any());
        verifyNoMoreInteractions(portalService);
    }

    @Test
    void test_consulta_por_cpf() throws Exception {
        when(consultaService.findByCpf(any())).thenReturn(UsuarioTest.getUsuario());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/cpf/{cpf}", "00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"area\": \"area teste\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"teste usuario\"}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

        verify(consultaService, times(1)).findByCpf(any());
        verifyNoMoreInteractions(consultaService);
    }

    @Test
    void test_consulta_por_cpf_nao_encontrado() throws Exception {
        when(consultaService.findByCpf(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/cpf/{cpf}", "00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_USUARIO_NAO_ENCONTRADO)));

        verify(consultaService, times(1)).findByCpf(any());
        verifyNoMoreInteractions(consultaService);
    }

    @Test
    void test_consulta_por_identificador() throws Exception {
        when(consultaService.findByIdentificador(any())).thenReturn(UsuarioTest.getUsuario());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/buscar/{id}", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"c4095434-f704-4209-be74-3d42d519d438\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"nome\": \"teste usuario\"}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

        verify(consultaService, times(1)).findByIdentificador(any());
        verifyNoMoreInteractions(consultaService);
    }

    @Test
    void test_consulta_por_identificador_nao_encontrado() throws Exception {
        when(consultaService.findByIdentificador(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/buscar/{id}", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_USUARIO_NAO_ENCONTRADO)));

        verify(consultaService, times(1)).findByIdentificador(any());
        verifyNoMoreInteractions(consultaService);
    }

    @Test
    void test_request_logar() throws Exception {
        doNothing().when(logarService).save(any(UUID.class), eq(false));

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{id}/requisicao-logar/?sucesso=false", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

        verify(logarService, times(1)).save(any(UUID.class), eq(false));
        verifyNoMoreInteractions(logarService);
    }

    @Test
    void test_requests_logins() throws Exception {
        when(logarService.getRequestByUserId(any())).thenReturn(Arrays.asList(any(RequisicaoLogar.class)));

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{id}/requisicoes-login", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

        verify(logarService, times(1)).getRequestByUserId(any());
        verifyNoMoreInteractions(logarService);
    }

    @Test
    void test_bloqueio_login() throws Exception {
        doNothing().when(service).bloquearUsuario(any());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{id}/bloqueio", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

        verify(service, times(1)).bloquearUsuario(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void test_set_ultimo_acesso() throws Exception {
        doNothing().when(service).ultimoAcesso(any());

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{id}/ultimo-acesso", "c4095434-f704-4209-be74-3d42d519d438")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

        verify(service, times(1)).ultimoAcesso(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void test_aceitar_termo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/{id}/aceite-termo/{nomeSistema}}", "c4095434-f704-4209-be74-3d42d519d438", "Portal da Dívida Ativa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));
    }

}
