package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicialTest;
import br.gov.ce.pge.gestao.entity.TipoAcaoJudicialTest;
import br.gov.ce.pge.gestao.service.TipoAcaoJudicialService;
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

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith({MockitoExtension.class})
@Import(GlobalExceptionHandler.class)
public class TipoAcaoJudicialControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private TipoAcaoJudicialController controller;

    @Mock
    private TipoAcaoJudicialService service;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
    }

    @Test
    void test_find_all() throws Exception {

        when(service.findAll()).thenReturn(List.of(TipoAcaoJudicialTest.getTipoAcaoJudicial()));

        mockMvc.perform(MockMvcRequestBuilders.get("/tipo-acao-judicial/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem", is(MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO)));

        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }

}
