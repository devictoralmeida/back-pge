package br.gov.ce.pge.mspgeportalcolaborador.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.mspgeportalcolaborador.dto.ColaboradorResponseDtoTest;
import br.gov.ce.pge.mspgeportalcolaborador.service.ColaboradorService;
import br.gov.ce.pge.mspgeportalcolaborador.shared.exception.ColaboradorNotFoundException;

@ExtendWith({ MockitoExtension.class })
class ColaboradorControllerTest {

	@InjectMocks
	ColaboradorController controller;

	@Mock
	ColaboradorService service;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	void test_consulta_por_cpf_ok() throws Exception {
		when(service.findByCpf(any())).thenReturn(ColaboradorResponseDtoTest.getColaboradorResponse());

		mockMvc.perform(
				MockMvcRequestBuilders.get("/colaborador/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("{\"nome\": \"João\"}"));

		verify(service, times(1)).findByCpf(any());
		verifyNoMoreInteractions(service);
	}

	@Test
	void test_consulta_por_cpf_nao_encontrado() throws Exception {

		when(service.findByCpf("00000000000"))
				.thenThrow(new ColaboradorNotFoundException("Colaborador não encontrado!"));

		mockMvc.perform(
				MockMvcRequestBuilders.get("/colaborador/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().json("{\"success\": false}"))
				.andExpect(MockMvcResultMatchers.content().json("{\"message\": \"Colaborador não encontrado!\"}"));
	}

    @Test
    void test_consulta_por_lista_cpf_ok() throws Exception {
        when(service.findByListCpfs(any())).thenReturn(ColaboradorResponseDtoTest.getListColaboradorResponse());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(Arrays.asList("00000000000"));

        mockMvc.perform(MockMvcRequestBuilders.post("/colaborador/busca-cpfs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(service, times(1)).findByListCpfs(any());
        verifyNoMoreInteractions(service);
    }
    
    @Test
	void test_busca_cpfs_nao_encontrado() throws Exception {

		when(service.findByListCpfs(Arrays.asList("00000000000")))
				.thenThrow(new ColaboradorNotFoundException("Colaborador não encontrado!"));

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(List.of("00000000000"));
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/colaborador/busca-cpfs")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonBody))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().json("{\"success\": false}"))
				.andExpect(MockMvcResultMatchers.content().json("{\"message\": \"Colaborador não encontrado!\"}"));
	}

}
