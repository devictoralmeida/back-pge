package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.ModuloDto;

public class ModuloTest {

	public static Modulo getModulo() {
		var modulo = new Modulo();
		modulo.setId(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"));
		modulo.setNome("origem debito");
		modulo.setDataCriacao(LocalDateTime.now());
		modulo.setPermissoes(PermissaoTest.getListPermissao());
		modulo.setNomeUsuarioCadastro("anônimo");
		return modulo;
	}
	
	public static ModuloDto getModuloDto() {
		var modulo = new ModuloDto();
		modulo.setId("5027c7f7-622b-4161-ac75-97c9110553f2");
		modulo.setNome("origem debito");
		modulo.setPermissoes(PermissaoTest.getListPermissaoDto());
		return modulo;
	}
	
	public static Modulo getModuloUpdate() {
		var modulo = new Modulo();
		modulo.setId(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"));
		modulo.setNome("origem debito update");
		modulo.setDataCriacao(LocalDateTime.now());
		modulo.setPermissoes(PermissaoTest.getListPermissao());
		modulo.setNomeUsuarioCadastro("anônimo");
		return modulo;
	}

	public static List<Modulo> getListModulo() {
		return List.of(getModulo());
	}
	
	public static List<ModuloDto> getListModuloDto() {
		return List.of(getModuloDto());
	}
	
	@Test
	public void teste_all_fields() {
		var model = getModulo();
		model.setDataAtualizacao(LocalDateTime.now());
		model.prePersist();
		model.preUpdate();
		
		assertNotNull(model);
		assertNotNull(model.getDataAtualizacao());
		assertNotNull(model.getDataCriacao());
		assertNotNull(model.equals(getModuloUpdate()));
		assertNotNull(model.equals(getModulo()));
		assertNotNull(model.hashCode());
		assertEquals(getModulo().getNome(), model.getNome());
		assertEquals(getModulo().getId(), model.getId());
		assertEquals(getModulo().getPermissoes(), model.getPermissoes());
		assertEquals("anônimo", model.getNomeUsuarioAtualizacao());
		assertEquals("anônimo", model.getNomeUsuarioCadastro());
	}
}
