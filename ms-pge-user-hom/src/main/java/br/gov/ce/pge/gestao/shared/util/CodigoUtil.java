package br.gov.ce.pge.gestao.shared.util;

import java.security.SecureRandom;

public class CodigoUtil {
	
	private CodigoUtil() {}
	
	public static String gerarCodigoSeisDigitos() {
		int numeroAleatorio = new SecureRandom().nextInt(900000) + 100000;
		return String.valueOf(numeroAleatorio);
	}

}
