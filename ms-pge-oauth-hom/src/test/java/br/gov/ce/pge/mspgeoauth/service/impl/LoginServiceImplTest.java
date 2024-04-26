package br.gov.ce.pge.mspgeoauth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
	
	@Test
	public void test_login() {
		var usuario = new Usuario();
		usuario.setCpf("12345678901");
		
		var token = "token";
		
		when(custom.authenticate(any(UsernamePasswordAuthenticationToken.class)))
		.thenReturn(new UsernamePasswordAuthenticationToken(usuario, null));
		
		when(usuarioService.getPrincipalUsuario(any(), any())).thenReturn(UsuarioTest.getUsuarioExterno());
		when(tokenService.generateToken(any())).thenReturn(token);
		
		String response = service.login("05459813360", "teste");
		
		assertEquals(token, response);
	}


}
