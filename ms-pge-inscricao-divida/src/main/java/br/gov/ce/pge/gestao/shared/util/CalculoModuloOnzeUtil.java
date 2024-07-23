package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;

public class CalculoModuloOnzeUtil {

    private CalculoModuloOnzeUtil() {}

    public static Integer getDigito(String numero) {

        int soma = SharedConstant.SOMA_INICIO;
        int peso = SharedConstant.PESO_INICIAL;

        for (int i = numero.length() - SharedConstant.INCREMENTO; i >= SharedConstant.SOMA_INICIO; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));

            soma += digito * peso;

            peso += SharedConstant.INCREMENTO;

            if(peso > SharedConstant.LIMITE_PESO) {
                peso = SharedConstant.PESO_INICIAL;
            }
        }

        int resto = soma % SharedConstant.NUMERO_MODULO;

        return (resto < SharedConstant.PESO_INICIAL) ? SharedConstant.SOMA_INICIO : SharedConstant.NUMERO_MODULO - resto;
    }

}
