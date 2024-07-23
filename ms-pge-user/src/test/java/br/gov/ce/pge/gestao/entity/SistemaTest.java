package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.gov.ce.pge.gestao.dto.response.SistemaDto;

@ExtendWith({ MockitoExtension.class })
public class SistemaTest {

	public static Sistema getSistema() {

		var sistema = new Sistema();
		sistema.setId(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b"));
		sistema.setNome("Portal da Dívida Ativa");
		sistema.setModulos(ModuloTest.getListModulo());
		sistema.setDataCriacao(LocalDateTime.now());
		sistema.setNomeUsuarioCadastro("anônimo");
		return sistema;
	}

	public static Sistema getSistemaPortalOrigens() {

		var sistema = new Sistema();
		sistema.setId(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b"));
		sistema.setNome("Portal das Origens");
		sistema.setModulos(ModuloTest.getListModulo());
		sistema.setDataCriacao(LocalDateTime.now());
		sistema.setNomeUsuarioCadastro("anônimo");
		return sistema;
	}

	public static Sistema getSistemaOther() {

		var sistema = new Sistema();
		sistema.setId(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41989a"));
		sistema.setNome("Portal");
		sistema.setModulos(ModuloTest.getListModulo());
		sistema.setDataCriacao(LocalDateTime.now());
		sistema.setNomeUsuarioCadastro("anônimo");
		return sistema;
	}

	public static SistemaDto getSistemaDto() {

		var sistema = new SistemaDto();
		sistema.setId("f08cc600-1268-4e35-8279-63ecef41454b");
		sistema.setNome("Portal da Dívida Ativa");
		sistema.setModulos(ModuloTest.getListModuloDto());
		return sistema;
	}

	public static Sistema getSistemaSemModulo() {

		var sistema = new Sistema();
		sistema.setId(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b"));
		sistema.setNome("Portal da Dívida Ativa");
		sistema.setModulos(null);
		sistema.setDataCriacao(LocalDateTime.now());
		sistema.setNomeUsuarioCadastro("anônimo");
		return sistema;
	}

	public static Sistema getSistemaUpdate() {

		var sistema = new Sistema();
		sistema.setId(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b"));
		sistema.setNome("portal divida ativa update");
		sistema.setModulos(ModuloTest.getListModulo());
		sistema.setDataCriacao(LocalDateTime.now());
		sistema.setDataAtualizacao(LocalDateTime.now());
		sistema.setNomeUsuarioCadastro("anônimo");
		sistema.setNomeUsuarioAtualizacao("anônimo");
		return sistema;
	}

	public static List<Sistema> getListSistema() {
		return List.of(getSistema());
	}

	public static List<SistemaDto> getListSistemaDto() {
		return List.of(getSistemaDto());
	}

	@Test
	public void teste_all_fields() {
		var model = getSistema();
		model.setDataAtualizacao(LocalDateTime.now());
		model.prePersist();
		model.preUpdate();

		assertNotNull(model);
		assertNotNull(model.getDataAtualizacao());
		assertNotNull(model.getDataCriacao());
		assertNotNull(model.hashCode());
		assertNotNull(model.equals(getSistemaSemModulo()));
		assertEquals(getSistema().getNome(), model.getNome());
		assertEquals(getSistema().getId(), model.getId());
		assertEquals(getSistema().getModulos().toString(), model.getModulos().toString());
		assertEquals(null, model.getNomeUsuarioAtualizacao());
		assertEquals(null, model.getNomeUsuarioCadastro());
	}

	@Test
	public void test_equals_sameObject() {
		Sistema sistema = new Sistema();
		assertTrue(sistema.equals(sistema));
	}

	@Test
	public void test_equals_nullObject() {
		Sistema sistema = new Sistema();
		assertFalse(sistema.equals(null));
	}

	@Test
	public void test_equals_different_class() {
		Sistema sistema = new Sistema();
		assertFalse(sistema.equals("NotASistemaObject"));
	}

	@Test
	public void test_equals_differentId() {
		Sistema sistema1 = new Sistema();
		sistema1.setId(UUID.randomUUID());
		Sistema sistema2 = new Sistema();
		sistema2.setId(UUID.randomUUID());
		assertFalse(sistema1.equals(sistema2));
	}

	@Test
	public void test_equals_sameId() {
		UUID id = UUID.randomUUID();
		Sistema sistema1 = new Sistema();
		sistema1.setId(id);
		Sistema sistema2 = new Sistema();
		sistema2.setId(id);
		assertTrue(sistema1.equals(sistema2));
	}

}
