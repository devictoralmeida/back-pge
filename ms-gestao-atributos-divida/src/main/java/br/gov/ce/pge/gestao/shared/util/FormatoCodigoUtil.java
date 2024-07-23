package br.gov.ce.pge.gestao.shared.util;

public class FormatoCodigoUtil {

    private FormatoCodigoUtil() {}

    public static String preencherZerosAEsquerda(String codigo, String numeroDigitos) {
        int digitos = Integer.parseInt(numeroDigitos);
        int numCodigo = Integer.parseInt(codigo);
        String formatSpecifier = "%0" + digitos + "d";
        return String.format(formatSpecifier, numCodigo);
    }

}
