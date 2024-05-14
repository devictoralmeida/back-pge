package br.gov.ce.pge.gestao.shared.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);


    private DateUtil() {}

    private static final String ZONE_ID = "America/Sao_Paulo";

    public static String formatDate(String date, String pattern) {
        try{

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            LocalDateTime parsedDate    = LocalDateTime.ofInstant(dateFormat.parse(handle(date)).toInstant(), ZoneId.of(ZONE_ID));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return parsedDate.format(formatter);

        } catch (ParseException e) {
            LOGGER.error("Error ao converter data: {}", e.getMessage());
            return null;
        }
    }

    private static String handle(String date) {
        return date.substring(0, date.indexOf('.'));
    }

}
