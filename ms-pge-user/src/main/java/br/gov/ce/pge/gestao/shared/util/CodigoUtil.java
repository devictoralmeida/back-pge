package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;

import java.security.SecureRandom;

public class CodigoUtil {
	
	private CodigoUtil() {}
	
	public static String gerarCodigoSeisDigitos() {
		int numeroAleatorio = new SecureRandom().nextInt(SharedConstant.NUMERO_MAXIMO) + SharedConstant.NUMERO_MINIMO;
		return String.valueOf(numeroAleatorio);
	}

}
