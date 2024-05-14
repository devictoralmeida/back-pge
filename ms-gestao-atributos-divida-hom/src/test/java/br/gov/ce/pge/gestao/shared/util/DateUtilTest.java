package br.gov.ce.pge.gestao.shared.util;

import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

class DateUtilTest {

	@Test
    public void test_format_date_catch_parse_exception() {

        String invalidDate = "2024-01-99T12:34:56.789";
        String pattern = "dd/MM/yyyy";

        String formatDate = DateUtil.formatDate(invalidDate, pattern);
        
        assertNull(formatDate);
    }

}
