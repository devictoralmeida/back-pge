package br.gov.ce.pge.mspgeoauth.service;

import java.util.UUID;

import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeoauth.shared.exception.NegocioException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.mspgeoauth.client.UsuarioFeingClient;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService {

	private final UsuarioFeingClient client;

	@Autowired
	public UsuarioService(UsuarioFeingClient client) {
		this.client = client;
	}

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		return getUsuarioByCpf(cpf);
	}

	public Usuario getPrincipalUsuario(Object principal, String login) {
		if (principal instanceof Usuario) {
			return (Usuario) principal;
		}else {
			return getUsuario(login);
		}
	}

	private Usuario getUsuario(String username) {
		try {
			ResponseEntity<ResponseDto<Usuario>> usuarioResponse = client.findUser(username);
			return usuarioResponse.getBody().getData();
		} catch (FeignException feignException) {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_REQUISICAO_USUARIO);
		}
	}

	private Usuario getUsuarioByCpf(String cpf) {
		try {
			ResponseEntity<ResponseDto<Usuario>> usuarioResponse = client.findByCpf(cpf);
			return usuarioResponse.getBody().getData();
		} catch (FeignException feignException) {
			throw new NegocioException(MessageCommonsContanst.MENSAGEM_ERRO_REQUISICAO_USUARIO);
		}
	}

	public void setUltimoLoginUsuario(UUID id) {
		client.setUltimoLogin(id);
	}
}