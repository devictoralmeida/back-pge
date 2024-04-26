package br.gov.ce.pge.gestao.shared.util;

import java.util.regex.Pattern;

public class ValidateDadosUtil {

	private  ValidateDadosUtil() {}
	
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

}
