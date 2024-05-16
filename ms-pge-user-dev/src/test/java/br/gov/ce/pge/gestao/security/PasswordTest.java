package br.gov.ce.pge.gestao.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;

class PasswordTest {

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	void test_encripty() {
		String senha = Password.encripty("senha");
		assertNotNull(senha);
	}

	@Test
	void test_equals_password() {
		Password.equalsPassword("senha", "senha");
	}

	@Test
	void test_not_equals_password() {
		Exception error = Assertions.assertThrows(NegocioException.class, () -> {
			Password.equalsPassword("senha", "senh@");
		});

		assertEquals("As senhas não correspondem. Verifique o conteúdo digitado e tente novamente.", error.getMessage());
	}

}
