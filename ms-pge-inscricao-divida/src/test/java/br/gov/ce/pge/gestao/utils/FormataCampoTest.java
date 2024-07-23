package br.gov.ce.pge.gestao.utils;

import br.gov.ce.pge.gestao.shared.util.FormataCampo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormataCampoTest {

    @Test
    void test_formata_campo() {

        String formatado = FormataCampo.formataData("2024-07-02");

        assertEquals("2024-07-02", formatado);

    }

    @Test
    void test_formata_campo_like() {

        String formatado = FormataCampo.formataData("Teste Nome");

        assertEquals("%TESTE NOME%", formatado);

    }
}
