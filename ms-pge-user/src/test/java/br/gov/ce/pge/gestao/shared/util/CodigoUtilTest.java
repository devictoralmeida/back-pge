package br.gov.ce.pge.gestao.shared.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CodigoUtilTest {

	@Test
	public void teste_gerar_codigo_seis_digitos() {
		String codigo = CodigoUtil.gerarCodigoSeisDigitos();
		assertNotNull(codigo);
		assertEquals(6, codigo.length());
		assertTrue(isNumeric(codigo));
	}

	private boolean isNumeric(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

}
