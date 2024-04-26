package br.gov.ce.pge.mspgeportalcolaborador.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({ MockitoExtension.class })
class HealthCheckControllerTest {

	@InjectMocks
	HealthCheckController controller;

	MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	public void test_health_check() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/health-check").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}