package br.gov.ce.pge.gestao.dto.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class UsuarioValidatorTest {

	@Test
	public void test_isValid_validData() {
		
		UsuarioRequestDto request = new UsuarioRequestDto();
		request.setNome("John Doe");
		request.setCpf("12345678901");
		request.setEmail("john.doe@sefaz.ce.gov.br");
		UsuarioValidator validator = new UsuarioValidator();

		boolean isValid = validator.isValid(request, null);

		assertTrue(isValid);
	}

	@Test
	public void test_invalid_cpf() {
		
		UsuarioRequestDto request = new UsuarioRequestDto();
		request.setCpf("abc");
		UsuarioValidator validator = new UsuarioValidator();

		NegocioException exception = assertThrows(NegocioException.class, () -> validator.isValid(request, null));
		assertEquals("o documento do usuário só pode conter número", exception.getMessage());
	}

	@Test
	public void test_invalid_email() {
		
		UsuarioRequestDto request = new UsuarioRequestDto();
		request.setEmail("email@gmail.com");
		UsuarioValidator validator = new UsuarioValidator();

		Exception exception = Assertions.assertThrows(NegocioException.class, () -> {
			validator.isValid(request, null);
		});
		
		assertEquals("E-mail inválido.", exception.getMessage());
	}
	
	@Test
	public void test_invalid_email_null() {
		
		UsuarioRequestDto request = new UsuarioRequestDto();
		request.setEmail(null);
		UsuarioValidator validator = new UsuarioValidator();

		boolean isValid = validator.isValid(request, null);
		
		assertTrue(isValid);
	}
}
