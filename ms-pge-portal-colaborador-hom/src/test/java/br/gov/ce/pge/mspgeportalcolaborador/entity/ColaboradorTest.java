package br.gov.ce.pge.mspgeportalcolaborador.entity;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColaboradorTest {

	public static Colaborador getColaborador() {
		var colaborador = new Colaborador();
		colaborador.setNome("João");
		colaborador.setCpf("054.598.133-60");
		colaborador.setArea("ti");
		colaborador.setDesvinculado(true);

		return colaborador;
	}

	@Test
	public void test_colaborador() {
		var colaborador = getColaborador();
		assertEquals("João", colaborador.getNome());
		assertEquals("ti", colaborador.getArea());
		assertEquals("054.598.133-60", colaborador.getCpf());
		assertEquals(true, colaborador.isDesvinculado());
	}

    public static List<Colaborador> getListColaborador() {
        return Arrays.asList(getColaborador());
    }

}
