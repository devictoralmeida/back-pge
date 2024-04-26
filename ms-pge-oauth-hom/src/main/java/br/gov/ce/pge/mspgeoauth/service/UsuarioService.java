package br.gov.ce.pge.mspgeoauth.service;

import java.util.UUID;

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
	
	public UsuarioService(UsuarioFeingClient client) {
		this.client = client;
	}
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		var usuario = this.client.findByCpf(cpf);
		return usuario.getBody().getData();
	}

    public Usuario getPrincipalUsuario(Object principal, String login) {
		if (principal instanceof Usuario) {
			return (Usuario) principal;
		}else {
			ResponseEntity<ResponseDto<Usuario>> usuarioResponse = client.findUser(login);
			return usuarioResponse.getBody().getData();
		}
    }

	public void setUltimoLoginUsuario(UUID id) {
		client.setUltimoLogin(id);
	}
}
