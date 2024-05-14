package br.gov.ce.pge.gestao.shared.util;

import java.util.regex.Pattern;

public class ValidateDadosUtil {
	
	private ValidateDadosUtil() {}
	
	public static boolean contemApenasLetras(String texto) {
	    String regex = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";
	    return Pattern.matches(regex, texto);
	}
	
	public static boolean contemApenasNumeros(String texto) {
		String regex = "^\\d+$";
        return Pattern.matches(regex, texto);
    }
	
	public static boolean contemApenasLetrasEnumero(String texto) {
		String regex = "[\\p{L}0-9\\s-.,]+";
	    return Pattern.matches(regex, texto);
	}
	
	public static boolean emailDominioGov(String email) {
		String dominio = email.split("@")[1];
		return dominio.contains("gov.br") || dominio.contains("GOV.BR");
	}

	public static boolean senhaValid(String senha) {
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$";
		return Pattern.matches(regex, senha);
	}

}
