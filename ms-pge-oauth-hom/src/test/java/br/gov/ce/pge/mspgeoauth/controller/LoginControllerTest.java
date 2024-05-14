package br.gov.ce.pge.mspgeoauth.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.service.LoginService;
import br.gov.ce.pge.mspgeoauth.shared.exception.GlobalExceptionHandler;

@ExtendWith({ MockitoExtension.class })
@Import(GlobalExceptionHandler.class)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private LoginService service;

	@InjectMocks
	LoginController controller;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(print()).build();
	}

	@Test
	void test_login() throws Exception {

		var loginDto = new LoginDto();
		loginDto.setLogin("username");
		loginDto.setSenha("password");

		var usuario = new Usuario();
		usuario.setCpf("12345678901");

		var token = "token";

		when(service.login(anyString(), anyString())).thenReturn(token);

		mockMvc.perform(MockMvcRequestBuilders.post("/login/portal-divida").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"login\": \"username\", \"senha\": \"password\" }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(token))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
	}

}
