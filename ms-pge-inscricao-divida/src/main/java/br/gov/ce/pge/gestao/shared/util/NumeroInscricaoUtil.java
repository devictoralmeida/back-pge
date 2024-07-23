package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

import java.time.LocalDate;

public class NumeroInscricaoUtil {

    private NumeroInscricaoUtil() {}

    public static String formatarNumeroInscricao(String numero) {

        int anoAtual = LocalDate.now().getYear();

        var numeroInscricao = "";

        if(numero.isEmpty()) {
            numeroInscricao = anoAtual + SharedConstant.NUMERO_INSCRICAO_INICIAL;
        } else {
            String substring = numero.substring(SharedConstant.INICIO_INDEX, SharedConstant.LIMITE_INDEX);

            String numeroSomado = somarNumero(substring);

            numeroInscricao = anoAtual + numeroSomado;
        }

        return numeroInscricao + CalculoModuloOnzeUtil.getDigito(numeroInscricao);
    }

    private static String somarNumero(String numero) {

        StringBuilder sb = new StringBuilder(numero.substring(4));

        if(SharedConstant.NUMERO_LIMITE.equals(sb.toString())) {
            throw new NegocioException(MessageCommonsConstants.ATINGIU_LIMITE);
        }

        int index = sb.length() - SharedConstant.INCREMENTO;

        boolean incrementado = false;

        while (index > SharedConstant.INICIO_INDEX && !incrementado) {
            if (sb.charAt(index) == SharedConstant.LIMITE_DIGITO_NUMERO) {
                while (sb.charAt(index) == SharedConstant.LIMITE_DIGITO_NUMERO && sb.charAt(index - 1) == SharedConstant.LIMITE_DIGITO_NUMERO) {
                    index--;
                }
                if (sb.charAt(index) == SharedConstant.LIMITE_DIGITO_NUMERO) {
                    sb.setCharAt(index - SharedConstant.INCREMENTO, (char) (sb.charAt(index - SharedConstant.INCREMENTO) + SharedConstant.INCREMENTO));
                    sb.replace(index, sb.length(), SharedConstant.INICIO_DIGITO_NUMERO.repeat(sb.length() - index));
                    incrementado = true;
                }
            } else {
                sb.setCharAt(index, (char) (sb.charAt(index) + SharedConstant.INCREMENTO));
                incrementado = true;
            }
        }

        return sb.toString();
    }

}
