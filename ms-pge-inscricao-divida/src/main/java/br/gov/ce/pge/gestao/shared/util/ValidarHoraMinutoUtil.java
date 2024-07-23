package br.gov.ce.pge.gestao.shared.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class ValidarHoraMinutoUtil {

    private ValidarHoraMinutoUtil() {
    }

    public static void validarHorario(String horario) {

        Pattern pattern = Pattern.compile(SharedConstant.REGEX_HORARIO);
        Matcher matcher = pattern.matcher(horario);

        if (matcher.matches()) {
            return;
        }

        throw new NegocioException("ajustar mensagem");
    }

}
