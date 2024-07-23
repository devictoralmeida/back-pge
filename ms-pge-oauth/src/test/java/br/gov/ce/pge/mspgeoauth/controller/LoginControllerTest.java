package br.gov.ce.pge.mspgeoauth.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeoauth.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.service.LoginService;
import br.gov.ce.pge.mspgeoauth.shared.exception.GlobalExceptionHandler;

import javax.servlet.http.HttpServletRequest;

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

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		doReturn(token).when(service).login(any(LoginDto.class), any(HttpServletRequest.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/login/portal-divida").contentType(MediaType.APPLICATION_JSON)
						.content("{ \"login\": \"username\", \"senha\": \"password\" }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(token))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
	}

	@Test
	void test_login_erro() throws JsonProcessingException {

		var loginDto = new LoginDto();
		loginDto.setLogin("username");
		loginDto.setSenha("password");

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		doThrow(new NegocioException(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR)).when(service).login(loginDto, request);

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.login(loginDto, request);
		});

		assertEquals(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR, exception.getMessage());

		verify(service).login(loginDto, request);

	}

	@Test
	void test_login_segundo_usuario() throws Exception {

		var loginDto = new LoginDto();
		loginDto.setLogin("usuario dois");
		loginDto.setSenha("senha dois");

		var usuario = new Usuario();
		usuario.setCpf("12345678902");

		var token = "token";

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		doReturn(token).when(service).login(any(LoginDto.class), any(HttpServletRequest.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/login/portal-divida").contentType(MediaType.APPLICATION_JSON)
						.content("{ \"login\": \"username\", \"senha\": \"password\" }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(token))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
	}

	@Test
	void test_login_segundo_usuario_erro() throws JsonProcessingException {

		var loginDto = new LoginDto();
		loginDto.setLogin("usuario dois");
		loginDto.setSenha("senha dois");

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		doThrow(new NegocioException(MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA)).when(service).login(loginDto, request);

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.login(loginDto, request);
		});

		assertEquals(MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA, exception.getMessage());

		verify(service).login(loginDto, request);

	}

	@Test
	void test_login_terceiro_usuario() throws Exception {

		var loginDto = new LoginDto();
		loginDto.setLogin("usuario três");
		loginDto.setSenha("senha três");

		var usuario = new Usuario();
		usuario.setCpf("12345678904");

		var token = "token";

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		doReturn(token).when(service).login(any(LoginDto.class), any(HttpServletRequest.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/login/portal-divida").contentType(MediaType.APPLICATION_JSON)
						.content("{ \"login\": \"username\", \"senha\": \"password\" }"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(token))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));
	}

	@Test
	void test_login_terceiro_usuario_erro() throws JsonProcessingException {

		var loginDto = new LoginDto();
		loginDto.setLogin("usuario três");
		loginDto.setSenha("senha três");

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		doThrow(new NegocioException(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA)).when(service).login(loginDto, request);

		NegocioException exception = assertThrows(NegocioException.class, () -> {
			controller.login(loginDto, request);
		});

		assertEquals(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA, exception.getMessage());

		verify(service).login(loginDto, request);

	}

	@Test
	void test_remover_sessao() throws Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();

		doNothing().when(service).removerSessao("test", request.getRemoteAddr());

		mockMvc.perform(MockMvcRequestBuilders.patch("/login/remocao-sessao/test").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200));

	}

}