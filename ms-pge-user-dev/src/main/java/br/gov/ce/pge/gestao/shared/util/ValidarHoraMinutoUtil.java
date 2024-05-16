package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidarHoraMinutoUtil {

    private ValidarHoraMinutoUtil() {
    }

    public static void validarHorario(String horario) {

        String regex = "^(\\d+):([0-5]\\d)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(horario);

        if (matcher.matches()) {
            return;
        }

        throw new NegocioException("Formato de hora inválido, precisa ser HH:MM");
    }

}
