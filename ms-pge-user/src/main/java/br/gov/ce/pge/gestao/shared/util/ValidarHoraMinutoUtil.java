package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarHoraMinutoUtil {

    private ValidarHoraMinutoUtil() {
    }

    public static void validarHorario(String horario) {

        Pattern pattern = Pattern.compile(SharedConstant.REGEX_VALIDA_HORA);
        Matcher matcher = pattern.matcher(horario);

        if (matcher.matches()) {
            return;
        }

        throw new NegocioException(MessageCommonsContanst.FORMATO_HORA_INVALIDO);
    }

}
