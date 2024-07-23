package br.gov.ce.pge.eurekaserver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EurekaServerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void contextLoads_other() {
		// Verifica se o contexto da aplicação é carregado corretamente

		EurekaServerApplication.main(new String[] {});
		assertTrue(true); // Se a aplicação inicializar sem erros, o teste passará

	}

}
