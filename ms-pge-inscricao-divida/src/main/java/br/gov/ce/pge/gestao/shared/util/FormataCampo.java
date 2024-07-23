package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Pattern;

public class FormataCampo {

    private static final Pattern DATE_PATTERN = Pattern.compile(SharedConstant.REGEX_DATA_DIA_MES_ANO);
    private static final Pattern NUMERIC_PATTERN = Pattern.compile(SharedConstant.REGEX_NUMERICO);
    private static final Pattern NUMBER_PATTERN = Pattern.compile(SharedConstant.REGEX_APENAS_NUMEROS);

    private FormataCampo() {}

    public static String formataData(String consultaGenerica) {

        if (DATE_PATTERN.matcher(consultaGenerica).matches() || NUMERIC_PATTERN.matcher(consultaGenerica).matches()) {
            return consultaGenerica;
        }

        return "%" + consultaGenerica.toUpperCase() + "%";
    }

    public static void definirConsultaGenerica(Map<String, Object> map, RegistroLivroFilterRequestDto filtro) {

        if(filtro.getConsultaGenerica() != null) {

            if (DATE_PATTERN.matcher(filtro.getConsultaGenerica()).matches()) {
                map.put("dataRegistro", LocalDate.parse(filtro.getConsultaGenerica()));
                map.remove(SharedConstant.CAMPO_CONSULTA_GENERICA);
            }

            if(!NUMBER_PATTERN.matcher(filtro.getConsultaGenerica()).matches()
                    && !DATE_PATTERN.matcher(filtro.getConsultaGenerica()).matches()
                    && !NUMERIC_PATTERN.matcher(filtro.getConsultaGenerica()).matches()) {
                map.put("nomeRazaoSocial", "%" + filtro.getConsultaGenerica().toUpperCase() + "%");
                map.put("nomeUsuario", "%" + filtro.getConsultaGenerica().toUpperCase() + "%");
                map.remove(SharedConstant.CAMPO_CONSULTA_GENERICA);
            }

            if(map.get("nomeRazaoSocial") == null && map.get("dataRegistro") == null && map.get("nomeUsuario") == null) {
                map.put(SharedConstant.CAMPO_CONSULTA_GENERICA, filtro.getConsultaGenerica());
            }

        }

    }

    public static void definirConsultaGenericaInscricao(Map<String, Object> map, DividaFilterRequestDto filtro) {

        if(filtro.getConsultaGenerica() != null) {

            if(!NUMBER_PATTERN.matcher(filtro.getConsultaGenerica()).matches()
                    && !DATE_PATTERN.matcher(filtro.getConsultaGenerica()).matches()
                    && !NUMERIC_PATTERN.matcher(filtro.getConsultaGenerica()).matches()) {
                map.put("nomeDevedor", "%" + filtro.getConsultaGenerica().toUpperCase() + "%");
                map.remove(SharedConstant.CAMPO_CONSULTA_GENERICA);
            }

            if(map.get("nomeDevedor") == null) {
                map.put(SharedConstant.CAMPO_CONSULTA_GENERICA, filtro.getConsultaGenerica());
            }
        }

    }
}
