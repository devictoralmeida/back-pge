package br.gov.ce.pge.mspgeportalcolaborador.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ MockitoExtension.class })
public class CpfUtilTest {

	@Test
	void test_cpf_formatado() {
		String cpf = "12345678909";

		String cpfFormatado = CpfUtil.formatarCpf(cpf);

		assertEquals("123.456.789-09", cpfFormatado);
	}

	@Test
	void test_cpf_numero() {
		String cpf = "123.456.789-09";

		String cpfFormatado = CpfUtil.cpfNumerico(cpf);

		assertEquals("12345678909", cpfFormatado);
	}

}
