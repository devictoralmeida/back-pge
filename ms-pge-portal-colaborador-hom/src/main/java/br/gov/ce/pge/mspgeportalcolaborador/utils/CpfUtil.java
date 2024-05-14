package br.gov.ce.pge.mspgeportalcolaborador.utils;

import java.text.MessageFormat;

public class CpfUtil {

	private CpfUtil() {

	}

	public static String formatarCpf(String cpf) {
		return MessageFormat.format("{0}.{1}.{2}-{3}", cpf.substring(0, 3), cpf.substring(3, 6), cpf.substring(6, 9),
				cpf.substring(9));
	}

	public static String cpfNumerico(String cpf) {
		return cpf.replaceAll("\\D+", "");
	}

}
