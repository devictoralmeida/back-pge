package br.gov.ce.pge.gestao.shared.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
public class FormatoCodigoUtilTest {
    @Test
    void test() {
        assertEquals("00001", FormatoCodigoUtil.preencherZerosAEsquerda("1", "5"));
    }

}
