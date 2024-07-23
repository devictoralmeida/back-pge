package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

public class PermissaoRequestDtoTest {

	public static PermissaoRequestDto getRequest() {
		var request = new PermissaoRequestDto();
		request.setNome("cadastrar");
		return request;
	}

	public static PermissaoRequestDto getRequestUpdate() {
		var request = new PermissaoRequestDto();
		request.setNome("cadastrar update");
		return request;
	}

	public static PermissaoRequestDto getRequestSemNome() {
		var request = new PermissaoRequestDto();
		request.setNome(null);
		return request;
	}

	@Test
	public void all_fields() {
		var request = new PermissaoRequestDto();
		request.setNome("cadastrar");

		assertNotNull(request);
		assertEquals("cadastrar", request.getNome());
	}

	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = factory.getValidator();

	@Test
	public void test_nome_notBlank() {
		
		PermissaoRequestDto dto = new PermissaoRequestDto();

		Set<ConstraintViolation<PermissaoRequestDto>> violations = validator.validate(dto);

		assertFalse(violations.isEmpty());
		assertEquals("favor informar o nome da permissão", violations.iterator().next().getMessage());
	}

	@Test
	public void test_nome_maxLength() {
		
		PermissaoRequestDto dto = new PermissaoRequestDto();
		dto.setNome("a".repeat(151)); // Exceeds max length

		Set<ConstraintViolation<PermissaoRequestDto>> violations = validator.validate(dto);

		assertFalse(violations.isEmpty());
		assertEquals("O campo nome deve conter no máximo 150 caracteres", violations.iterator().next().getMessage());
	}

	@Test
	public void test_ValidDto() {
		
		PermissaoRequestDto dto = new PermissaoRequestDto();
		dto.setNome("Permissão de teste");

		Set<ConstraintViolation<PermissaoRequestDto>> violations = validator.validate(dto);

		assertTrue(violations.isEmpty());
	}

}
