package br.gov.ce.pge.mspgeoauth.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class ModuloTest {

    public static Modulo getModulo() {
        var modulo = new Modulo();
        modulo.setId(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"));
        modulo.setNome("origem debito");
        modulo.setPermissoes(Arrays.asList(PermissaoTest.getPermissao()));
        return modulo;
    }
    
    @Test
	public void teste_all_fields() {
		var model = getModulo();
		
		assertNotNull(model);
		assertNotNull(model.equals(getModulo()));
		assertNotNull(model.hashCode());
		assertEquals("origem debito", model.getNome());
		assertEquals(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"), model.getId());
	}

}
