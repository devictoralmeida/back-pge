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

import br.gov.ce.pge.gestao.enums.Situacao;

public class PerfilAcessoRequestDtoTest {

	public static PerfilAcessoRequestDto getRequest() {
		var request = new PerfilAcessoRequestDto();

		request.setNome("ADMIN");
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")));
		return request;
	}
	
	public static PerfilAcessoRequestDto getRequestUpdate() {
		var request = new PerfilAcessoRequestDto();

		request.setNome("ADMIN");
		request.setSituacao(Situacao.INATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")));
		return request;
	}
	
	public static PerfilAcessoRequestDto getRequestUpdate_com_outro_nome() {
		var request = new PerfilAcessoRequestDto();

		request.setNome("ADMIN update");
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")));
		return request;
	}
	
	public static PerfilAcessoRequestDto getRequestUpdateERROR() {
		var request = new PerfilAcessoRequestDto();

		request.setNome("ADMIN2");
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")));
		return request;
	}
	
	public static PerfilAcessoRequestDto getRequestSemNome() {
		var request = new PerfilAcessoRequestDto();

		request.setNome(null);
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")));
		return request;
	}
	
	public static PerfilAcessoRequestDto getRequestComPermissaoSemSistemaAssociado() {
		var request = new PerfilAcessoRequestDto();

		request.setNome("ADMIN");
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999")));
		return request;
	}
	
	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void test_nome_notBlank() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setSituacao(Situacao.ATIVA);
        dto.setSistemas(Collections.singletonList(UUID.randomUUID()));
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Preencher o campo nome", violations.iterator().next().getMessage());
    }

    @Test
    public void test_nome_maxLength() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("a".repeat(151)); // Exceeds max length
        dto.setSituacao(Situacao.ATIVA);
        dto.setSistemas(Collections.singletonList(UUID.randomUUID()));
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("O campo nome deve conter no máximo 150 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void test_situacao_notNull() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("Perfil de Acesso Teste");
        dto.setSistemas(Collections.singletonList(UUID.randomUUID()));
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Preencher a situação", violations.iterator().next().getMessage());
    }

    @Test
    public void test_sistemas_notNull() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("Perfil de Acesso Teste");
        dto.setSituacao(Situacao.ATIVA);
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Preencher o campo sistema", violations.iterator().next().getMessage());
    }

    @Test
    public void test_sistemas_notEmpty() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("Perfil de Acesso Teste");
        dto.setSituacao(Situacao.ATIVA);
        dto.setSistemas(Collections.emptyList());
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Preencher o campo sistema", violations.iterator().next().getMessage());
    }

    @Test
    public void test_permissoes_notNull() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("Perfil de Acesso Teste");
        dto.setSituacao(Situacao.ATIVA);
        dto.setSistemas(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Preencher o campo permissões", violations.iterator().next().getMessage());
    }

    @Test
    public void test_permissoes_notEmpty() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("Perfil de Acesso Teste");
        dto.setSituacao(Situacao.ATIVA);
        dto.setSistemas(Collections.singletonList(UUID.randomUUID()));
        dto.setPermissoes(Collections.emptyList());

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("Preencher o campo permissões", violations.iterator().next().getMessage());
    }

    @Test
    public void test_validDto() {
        
        PerfilAcessoRequestDto dto = new PerfilAcessoRequestDto();
        dto.setNome("Perfil de Acesso Teste");
        dto.setSituacao(Situacao.INATIVA);
        dto.setSistemas(Collections.singletonList(UUID.randomUUID()));
        dto.setPermissoes(Collections.singletonList(UUID.randomUUID()));

        Set<ConstraintViolation<PerfilAcessoRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }
	
	@Test
	public void teste_all_fields() {
		var request = new PerfilAcessoRequestDto();

		request.setNome("ADMIN");
		request.setSituacao(Situacao.ATIVA);
		request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
		request.setPermissoes(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")));
		
		assertNotNull(request);
		assertEquals("ADMIN", request.getNome());
		assertEquals(Situacao.ATIVA, request.getSituacao());
		assertEquals(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")), request.getSistemas());
		assertEquals(List.of(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640")), request.getPermissoes());
	}
}
