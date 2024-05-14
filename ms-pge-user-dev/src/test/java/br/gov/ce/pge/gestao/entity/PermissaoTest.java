package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.PermissaoDto;

public class PermissaoTest {

	public static Permissao getPermissao() {
		var permissao = new Permissao();
		permissao.setId(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640"));
		permissao.setNome("cadastrar");
		permissao.setDataCriacao(LocalDateTime.now());
		permissao.setNomeUsuarioCadastro("anônimo");
		permissao.setCodigoIdentificador("ORD_CADASTRAR");
		return permissao;
	}
	
	public static PermissaoDto getPermissaoDto() {
		var permissao = new PermissaoDto();
		permissao.setId("e038f9db-5bfe-4a32-aff1-ad51f67b0640");
		permissao.setNome("cadastrar");
		return permissao;
	}
	
	public static Permissao getPermissaoSemSistema() {
		var permissao = new Permissao();
		permissao.setId(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0999"));
		permissao.setNome("cadastrar");
		permissao.setDataCriacao(LocalDateTime.now());
		permissao.setNomeUsuarioCadastro("anônimo");
		return permissao;
	}
	
	public static Permissao getPermissaoUpdate() {
		var permissao = new Permissao();
		permissao.setId(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640"));
		permissao.setNome("cadastrar update");
		permissao.setDataCriacao(LocalDateTime.now());
		permissao.setDataAtualizacao(LocalDateTime.now());
		permissao.setNomeUsuarioCadastro("anônimo");
		return permissao;
	}
	
	public static List<Permissao> getListPermissao() {
		return List.of(getPermissao());
	}
	
	public static List<PermissaoDto> getListPermissaoDto() {
		return List.of(getPermissaoDto());
	}
	
	public static List<Permissao> getListPermissaoSemSistema() {
		return List.of(getPermissaoSemSistema());
	}

	@Test
	public void teste_all_fields() {
		var model = getPermissao();
		model.setDataAtualizacao(LocalDateTime.now());
		model.prePersist();
		model.preUpdate();
		
		assertNotNull(model);
		assertNotNull(model.getDataAtualizacao());
		assertNotNull(model.getDataCriacao());
		assertNotNull(model.toString());
		assertNotNull(model.hashCode());
		assertEquals(getPermissao().getNome(), model.getNome());
		assertEquals(getPermissao().getId(), model.getId());
		assertEquals("anônimo", model.getNomeUsuarioAtualizacao());
		assertEquals("anônimo", model.getNomeUsuarioCadastro());
		assertEquals("ORD_CADASTRAR", model.getCodigoIdentificador());
	}
	
	@Test
    public void test_equals_same_object() {
        Permissao permissao = new Permissao();
        assertTrue(permissao.equals(permissao));
    }

    @Test
    public void test_equals_NullObject() {
        Permissao permissao = new Permissao();
        assertFalse(permissao.equals(null));
    }

    @Test
    public void test_equals_different_class() {
        Permissao permissao = new Permissao();
        assertFalse(permissao.equals("NotAPermissaoObject"));
    }

    @Test
    public void test_equals_different_Id() {
        Permissao permissao1 = new Permissao();
        permissao1.setId(UUID.randomUUID());
        Permissao permissao2 = new Permissao();
        permissao2.setId(UUID.randomUUID());
        assertFalse(permissao1.equals(permissao2));
    }

    @Test
    public void test_equals_different_nome() {
        Permissao permissao1 = new Permissao();
        permissao1.setId(UUID.randomUUID());
        permissao1.setNome("Nome1");
        Permissao permissao2 = new Permissao();
        permissao2.setId(permissao1.getId());
        permissao2.setNome("Nome2");
        assertFalse(permissao1.equals(permissao2));
    }

    @Test
    public void test_equals_sameId_and_nome() {
        UUID id = UUID.randomUUID();
        Permissao permissao1 = new Permissao();
        permissao1.setId(id);
        permissao1.setNome("Nome");
        Permissao permissao2 = new Permissao();
        permissao2.setId(id);
        permissao2.setNome("Nome");
        assertTrue(permissao1.equals(permissao2));
    }
}
