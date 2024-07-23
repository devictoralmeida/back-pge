package br.gov.ce.pge.mspgeoauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.entity.UsuarioTest;
import br.gov.ce.pge.mspgeoauth.security.TokenService;
import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.UsuarioService;

@ExtendWith({ MockitoExtension.class })
class LoginServiceImplTest {

	@InjectMocks
	private LoginServiceImpl service;

	@Mock
	private CustomAuthenticationProviderService custom;

	@Mock
	private TokenService tokenService;

	@Mock
	private UsuarioService usuarioService;

	@Mock
	private RedisServiceImpl redisService;

	@Test
	public void test_login() throws JsonProcessingException {
		var usuario = new Usuario();
		usuario.setCpf("12345678901");

		var token = "token";

		LoginDto loginDto = new LoginDto();
		loginDto.setLogin("05459813360");
		loginDto.setSenha("teste");

		when(custom.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(new UsernamePasswordAuthenticationToken(usuario, null));

		when(usuarioService.getPrincipalUsuario(any(), any())).thenReturn(UsuarioTest.getUsuarioExterno());
		when(tokenService.generateToken(any())).thenReturn(token);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		String response = service.login(loginDto, request);

		assertEquals(token, response);
	}

	@Test
	public void test_login_outro_usuario() throws JsonProcessingException {
		var usuario = new Usuario();
		usuario.setCpf("04987613301");

		var token = "response_token";

		LoginDto loginDto = new LoginDto();
		loginDto.setLogin("05459813360");
		loginDto.setSenha("teste");

		when(custom.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(new UsernamePasswordAuthenticationToken(usuario, null));

		when(usuarioService.getPrincipalUsuario(any(), any())).thenReturn(UsuarioTest.getUsuarioExterno());
		when(tokenService.generateToken(any())).thenReturn(token);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		String response = service.login(loginDto, request);

		assertEquals(token, response);
	}

	@Test
	public void test_login_ultimo_usuario() throws JsonProcessingException {
		var usuario = new Usuario();
		usuario.setCpf("05487613301");

		var token = "response_token_ultimo";

		LoginDto loginDto = new LoginDto();
		loginDto.setLogin("05459813360");
		loginDto.setSenha("teste");

		when(custom.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(new UsernamePasswordAuthenticationToken(usuario, null));

		when(usuarioService.getPrincipalUsuario(any(), any())).thenReturn(UsuarioTest.getUsuarioExterno());
		when(tokenService.generateToken(any())).thenReturn(token);

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("0.0.1.1");

		String response = service.login(loginDto, request);

		assertEquals(token, response);
	}

	@Test
	void test_remover_sessao() throws JsonProcessingException {

		doNothing().when(redisService).removerUserIp(any(), any());

		service.removerSessao(any(), any());

	}

}