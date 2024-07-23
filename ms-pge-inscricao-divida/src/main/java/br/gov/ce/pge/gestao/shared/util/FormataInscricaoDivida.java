package br.gov.ce.pge.gestao.shared.util;

public class FormataInscricaoDivida {
	
	public static String formatarNumero(String numero) {
        
        if (numero == null || numero.length() != 13) {
            throw new IllegalArgumentException("O número deve ter 13 dígitos.");
        }

        String ano = numero.substring(0, 4);
        String sequencial = numero.substring(4, 12);
        String digito = numero.substring(12);

        return String.format("%s.%s-%s", ano, sequencial, digito);
    }

}
