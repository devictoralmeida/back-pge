package br.gov.ce.pge.mspgeapigateway.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConfigTest {

	@Test
	void teste_getIdentificador() {

		String identificador = "testIdentificador";
		Config config = new Config(identificador);

		String result = config.getIdentificador();

		assertEquals(identificador, result);
	}

}
