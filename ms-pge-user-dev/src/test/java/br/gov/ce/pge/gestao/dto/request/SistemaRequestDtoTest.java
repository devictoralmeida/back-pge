package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

public class SistemaRequestDtoTest {

	public static SistemaRequestDto getRequest() {
		var request = new SistemaRequestDto();
		request.setNome("portal divida ativa");
		request.setModulos(List.of(UUID.randomUUID()));
		return request;
	}

	public static SistemaRequestDto getRequestSemModulo() {
		var request = new SistemaRequestDto();
		request.setNome("portal divida ativa");
		request.setModulos(null);
		return request;
	}

	public static SistemaRequestDto getRequestUpdate() {
		var request = new SistemaRequestDto();
		request.setNome("portal divida ativa update");
		request.setModulos(List.of(UUID.randomUUID()));
		return request;
	}

	public static SistemaRequestDto getRequestSemNome() {
		var request = new SistemaRequestDto();
		request.setNome(null);
		request.setModulos(List.of(UUID.randomUUID()));
		return request;
	}

	public void all_fields() {
		var request = new SistemaRequestDto();
		request.setNome("portal divida ativa");
		request.setModulos(List.of(UUID.randomUUID()));

		assertNotNull(request);
		assertEquals("portal divida ativa", request.getNome());
		assertEquals(1, request.getModulos().size());
	}

	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = factory.getValidator();

	@Test
	public void test_nome_notBlank() {

		SistemaRequestDto dto = new SistemaRequestDto();
		dto.setModulos(Collections.singletonList(UUID.randomUUID()));

		Set<ConstraintViolation<SistemaRequestDto>> violations = validator.validate(dto);

		assertFalse(violations.isEmpty());
		assertEquals("favor informar o nome do sistema", violations.iterator().next().getMessage());
	}

	@Test
	public void test_nome_maxLength() {
		
		SistemaRequestDto dto = new SistemaRequestDto();
		dto.setNome("a".repeat(151)); // Exceeds max length
		dto.setModulos(Collections.singletonList(UUID.randomUUID()));

		Set<ConstraintViolation<SistemaRequestDto>> violations = validator.validate(dto);

		assertFalse(violations.isEmpty());
		assertEquals("O campo nome deve conter no máximo 150 caracteres", violations.iterator().next().getMessage());
	}

	@Test
	public void test_ValidDto() {
		
		SistemaRequestDto dto = new SistemaRequestDto();
		dto.setNome("Sistema Teste");
		dto.setModulos(Collections.singletonList(UUID.randomUUID()));

		Set<ConstraintViolation<SistemaRequestDto>> violations = validator.validate(dto);

		assertTrue(violations.isEmpty());
	}

	@Test
	public void test_getNome() {
		SistemaRequestDto request = getRequest();
		assertEquals("portal divida ativa", request.getNome());
	}

	@Test
	public void test_setNome() {
		SistemaRequestDto request = getRequest();
		request.setNome("novo nome");
		assertEquals("novo nome", request.getNome());
	}

	@Test
	public void test_setModulos() {
		SistemaRequestDto request = getRequest();
		List<UUID> newModulos = List.of(UUID.randomUUID(), UUID.randomUUID());
		request.setModulos(newModulos);
		assertEquals(newModulos, request.getModulos());
	}

	@Test
	public void test_getRequest() {
		SistemaRequestDto request = SistemaRequestDtoTest.getRequest();
		assertNotNull(request);
		assertEquals("portal divida ativa", request.getNome());
		assertFalse(request.getModulos().isEmpty());
	}
}
