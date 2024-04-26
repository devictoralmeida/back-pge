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

public class ModuloRequestDtoTest {

	public static ModuloRequestDto getRequest() {
		var request = new ModuloRequestDto();
		request.setNome("origem debito");
		request.setPermissoes(List.of(UUID.randomUUID()));
		return request;
	}

	public static ModuloRequestDto getRequestUpdate() {
		var request = new ModuloRequestDto();
		request.setNome("origem debito update");
		request.setPermissoes(List.of(UUID.randomUUID()));
		return request;
	}

	public static ModuloRequestDto getRequestSemNome() {
		var request = new ModuloRequestDto();
		request.setNome(null);
		request.setPermissoes(List.of(UUID.randomUUID()));
		return request;
	}

	@Test
	public void teste_all_fields() {
		var request = new ModuloRequestDto();
		request.setNome("origem debito");
		request.setPermissoes(List.of(UUID.randomUUID()));

		assertNotNull(request);
		assertEquals("origem debito", request.getNome());
		assertEquals(1, request.getPermissoes().size());
	}
	
	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void test_nome_notBlank() {
        
        ModuloRequestDto dto = new ModuloRequestDto();
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<ModuloRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("favor informar o nome do módulo", violations.iterator().next().getMessage());
    }

    @Test
    public void test_nome_maxLength() {
        
        ModuloRequestDto dto = new ModuloRequestDto();
        dto.setNome("a".repeat(151)); // Exceeds max length
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<ModuloRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O campo nome deve conter no máximo 150 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void test_permissoes_notNull() {
        
        ModuloRequestDto dto = new ModuloRequestDto();
        dto.setNome("Teste de Módulo");

        Set<ConstraintViolation<ModuloRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("favor informar as permissões do módulo", violations.iterator().next().getMessage());
    }

    @Test
    public void test_permissoes_notEmpty() {
        
        ModuloRequestDto dto = new ModuloRequestDto();
        dto.setNome("Teste de Módulo");
        dto.setPermissoes(Collections.emptyList());

        Set<ConstraintViolation<ModuloRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("favor informar as permissões do módulo", violations.iterator().next().getMessage());
    }

    @Test
    public void test_ValidDto() {
        
        ModuloRequestDto dto = new ModuloRequestDto();
        dto.setNome("Teste de Módulo");
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<ModuloRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

}
