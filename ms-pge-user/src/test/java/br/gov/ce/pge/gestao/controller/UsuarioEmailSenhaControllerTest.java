package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDtoTest;
import br.gov.ce.pge.gestao.service.UsuarioEmailSenhaService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
class UsuarioEmailSenhaControllerTest {

    @InjectMocks
    UsuarioEmailSenhaController controller;

    @Mock
    UsuarioEmailSenhaService service;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
    }

    @Test
    void test_update_password() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenhaValida());

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/{id}/alteracao-senha/", "c4095434-f704-4209-be74-3d42d519d438")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

    }

    @Test
    void test_validacao_codigo() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{id}/validacao-codigo/{codigo}", "c4095434-f704-4209-be74-3d42d519d438", "123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));
    }

    @Test
    void test_recover_password() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenhaValida());

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/{id}/recuperacao-senha/{token}", "4931259c-2f03-4542-9f58-97a26f64d3d8", "f9a2b13d-284d-4e2f-adea-d044aff51b4a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));
    }

    @Test
    void test_create_password() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(UsuarioCadastroSenhaRequestDtoTest.getUsuarioCadastroSenhaValida());

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuario/{id}/senha/{token}", "a597aa9e-0a05-48de-adcb-a7a1b631aedd", "f9a2b13d-284d-4e2f-adea-d044aff51b4a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));
    }

    @Test
    void test_send_email_recovery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/usuario/{email}/email-recuperacao/{nomeSistema}", "email@gov.br", "Portal Da Dívida Ativa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_EMAIL_RECUPERACAO_SENHA)));
    }
}
