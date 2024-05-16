package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UsuarioCadastroSenhaRequestDtoTest {

	public static UsuarioCadastroSenhaRequestDto getUsuarioCadastroSenha() {
		var usuario = new UsuarioCadastroSenhaRequestDto();
		usuario.setSenha("123");
		usuario.setConfirmarSenha("123");

		return usuario;
	}

	public static UsuarioCadastroSenhaRequestDto getUsuarioCadastroSenhaValida() {
		var usuario = new UsuarioCadastroSenhaRequestDto();
		usuario.setSenha("1$3Ab123");
		usuario.setConfirmarSenha("1$3Ab123");

		return usuario;
	}

	public static UsuarioCadastroSenhaRequestDto getUsuarioAlterarSenhaJaInformada() {
		var usuario = new UsuarioCadastroSenhaRequestDto();
		usuario.setSenha("123456");
		usuario.setConfirmarSenha("123456");

		return usuario;
	}

	@Test
	public void test_getter_and_setter() {
		
		UsuarioCadastroSenhaRequestDto dto = new UsuarioCadastroSenhaRequestDto();

		dto.setSenha("password");
		dto.setConfirmarSenha("password");

		assertEquals("password", dto.getSenha());
		assertEquals("password", dto.getConfirmarSenha());
	}

	@Test
	public void test_serializable() {
		
		UsuarioCadastroSenhaRequestDto dto = new UsuarioCadastroSenhaRequestDto();

		assertTrue(dto instanceof java.io.Serializable);
	}

}
