package br.gov.ce.pge.gestao.shared.util;


import br.gov.ce.pge.gestao.constants.SharedConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    private DateUtil() {}

    public static String formatDate(String date, String pattern) {
        try{

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            LocalDateTime parsedDate    = LocalDateTime.ofInstant(dateFormat.parse(handle(date)).toInstant(), ZoneId.of(SharedConstant.ZONE_ID));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return parsedDate.format(formatter);

        } catch (ParseException e) {
            return null;
        }
    }

    private static String handle(String date) {
        return date.substring(SharedConstant.INDICE_INICIAL, date.indexOf('.'));
    }

}
