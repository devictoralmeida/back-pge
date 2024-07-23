package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;

import java.util.regex.Pattern;

public class ValidateDadosUtil {
	
	private ValidateDadosUtil() {}
	
	public static boolean contemApenasLetras(String texto) {
	    return Pattern.matches(SharedConstant.REGEX_APENAS_LETRAS, texto);
	}
	
	public static boolean contemApenasNumeros(String texto) {
        return Pattern.matches(SharedConstant.REGEX_APENAS_NUMEROS, texto);
    }
	
	public static boolean contemApenasLetrasEnumero(String texto) {
	    return Pattern.matches(SharedConstant.REGEX_APENAS_LETRAS_NUMEROS, texto);
	}
	
	public static boolean emailDominioGov(String email) {
		String dominio = email.split("@")[SharedConstant.SEGUNDO_INDICE];
		return dominio.contains(SharedConstant.DOMINIO_MINUSCULO) || dominio.contains(SharedConstant.DOMINIO_MAIUSCULO);
	}

	public static boolean senhaValid(String senha) {
		return Pattern.matches(SharedConstant.REGEX_VALIDA_SENHA, senha);
	}

}
