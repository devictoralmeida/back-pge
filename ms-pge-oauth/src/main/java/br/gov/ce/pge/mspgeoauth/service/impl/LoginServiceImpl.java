package br.gov.ce.pge.mspgeoauth.service.impl;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.entity.IpUsuario;
import br.gov.ce.pge.mspgeoauth.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.security.TokenService;
import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.LoginService;
import br.gov.ce.pge.mspgeoauth.service.UsuarioService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

	private final CustomAuthenticationProviderService custom;

	private final TokenService tokenService;

	private final UsuarioService service;

	private final RedisService redisService;

	@Autowired
	public LoginServiceImpl(CustomAuthenticationProviderService custom, TokenService tokenService,
							UsuarioService service, RedisService redisService) {
		this.custom = custom;
		this.tokenService = tokenService;
		this.service = service;
		this.redisService = redisService;
	}

	@Override
	public String login(LoginDto loginDto, HttpServletRequest request) throws JsonProcessingException {

		this.custom.validarDispositivo(loginDto, request);

		UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getSenha());

		Usuario usuario = service.getPrincipalUsuario(this.custom.authenticate(usernamePasswordToken).getPrincipal(), loginDto.getLogin());

		String token = tokenService.generateToken(usuario);

		service.setUltimoLoginUsuario(usuario.getId());

		this.redisService.inserirUserIp(new IpUsuario(usuario.getNome(), request.getRemoteAddr(), token));

		return token;
	}

	@Override
	public void removerSessao(String nomeUsuario, String ip) throws JsonProcessingException {
		this.redisService.removerUserIp(nomeUsuario, ip);
	}

}
