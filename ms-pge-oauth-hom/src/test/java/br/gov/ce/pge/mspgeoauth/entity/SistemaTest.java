package br.gov.ce.pge.mspgeoauth.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class SistemaTest {

	public static Sistema getSistema() {

		var sistema = new Sistema();
		sistema.setId(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b"));
		sistema.setNome("Portal da Dívida Ativa");
		sistema.setModulos(Arrays.asList(ModuloTest.getModulo()));
		return sistema;
	}

	@Test
	public void test_equals_same_object() {
		Sistema sistema = new Sistema();
		assertTrue(sistema.equals(sistema));
	}

	@Test
	public void test_equals_null_object() {
		Sistema sistema = new Sistema();
		assertFalse(sistema.equals(null));
	}

	@Test
	public void test_equals_different_id() {
		Sistema sistema1 = new Sistema();
		sistema1.setId(UUID.randomUUID());
		Sistema sistema2 = new Sistema();
		sistema2.setId(UUID.randomUUID());
		assertFalse(sistema1.equals(sistema2));
	}

}
