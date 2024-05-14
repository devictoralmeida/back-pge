package br.gov.ce.pge.mspgeoauth.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.security.TokenService;
import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.LoginService;
import br.gov.ce.pge.mspgeoauth.service.UsuarioService;

@Service
public class LoginServiceImpl implements LoginService {

	private final CustomAuthenticationProviderService custom;

	private final TokenService tokenService;

	private final UsuarioService service;
	
	public LoginServiceImpl(CustomAuthenticationProviderService custom, TokenService tokenService,
			UsuarioService service) {
		this.custom = custom;
		this.tokenService = tokenService;
		this.service = service;
	}

	@Override
	public String login(String login, String senha) {
		UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(login, senha);

		Usuario usuario = service.getPrincipalUsuario(this.custom.authenticate(usernamePasswordToken).getPrincipal(), login);

		var token = tokenService.generateToken(usuario);

		service.setUltimoLoginUsuario(usuario.getId());
		return token;
	}

}
