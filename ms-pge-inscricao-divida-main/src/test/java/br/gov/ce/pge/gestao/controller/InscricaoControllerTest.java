package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDtoTest;
import br.gov.ce.pge.gestao.dto.response.InscricaoResponseDtoTest;
import br.gov.ce.pge.gestao.service.InscricaoService;
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
class InscricaoControllerTest {
    @Mock
    GlobalExceptionHandler globalExceptionHandler;

    MockMvc mockMvc;

    @Mock
    private InscricaoService service;

    @InjectMocks
    private InscricaoController controller;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).alwaysDo(print()).build();
    }

    @Test
    void test_save_ok() throws Exception {
        doNothing().when(service).save(any());

        InscricaoRequestDto request = InscricaoRequestDtoTest.get_inscricao_completa();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/inscricao/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO)));

        verify(service, times(1)).save(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void test_update_ok() throws Exception {
        doNothing().when(service).update(any(), any());

        InscricaoRequestDto request = InscricaoRequestDtoTest.get_inscricao_completa();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String jsonBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/inscricao/{id}", "c8bf3eff-2d65-46f7-bb6a-8f4c93be7886")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO)));

        verify(service, times(1)).update(any(), any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void test_consulta_por_id_ok() throws Exception {
        when(service.findById(any())).thenReturn(InscricaoResponseDtoTest.getIncricaoCompleta());

        mockMvc.perform(MockMvcRequestBuilders.get("/inscricao/{id}", "c8bf3eff-2d65-46f7-bb6a-8f4c93be7886")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"id\": \"c8bf3eff-2d65-46f7-bb6a-8f4c93be7886\"}}"))
                .andExpect(MockMvcResultMatchers.content().json("{\"data\": {\"status\": \"EM_ANALISE\"}}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO)));

        verify(service, times(1)).findById(any());
        verifyNoMoreInteractions(service);
    }
}
