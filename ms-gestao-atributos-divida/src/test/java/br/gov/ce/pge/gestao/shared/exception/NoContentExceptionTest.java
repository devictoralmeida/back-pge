package br.gov.ce.pge.gestao.shared.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoContentExceptionTest {

	@Test
	void test() {
		Assertions.assertThrows(NoContentException.class, () -> {
			throw new NoContentException("teste");
		});
	}

}
