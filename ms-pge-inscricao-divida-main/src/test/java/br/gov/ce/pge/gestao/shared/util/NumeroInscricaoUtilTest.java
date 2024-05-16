package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumeroInscricaoUtilTest {

    @Test
    void test_calcular_numero_inscricao() {

        String numero = "2024000009996";

        String numeroInscricao = NumeroInscricaoUtil.formatarNumeroInscricao(numero);

        assertEquals("202400001000" + CalculoModuloOnzeUtil.getDigito("202400001000"), numeroInscricao);

    }

    @Test
    void test_calcular_outro_numero_inscricao() {

        String numero = "2024000000016";

        String numeroInscricao = NumeroInscricaoUtil.formatarNumeroInscricao(numero);

        assertEquals("202400000002" + CalculoModuloOnzeUtil.getDigito("202400000002"), numeroInscricao);

    }

    @Test
    void test_calcular_numero_grande_inscricao() {

        String numero = "2024000090006";

        String numeroInscricao = NumeroInscricaoUtil.formatarNumeroInscricao(numero);

        assertEquals("202400009001" + CalculoModuloOnzeUtil.getDigito("202400009001"), numeroInscricao);

    }

    @Test
    void test_calcular_outro_numero_grande_inscricao() {

        String numero = "2024000090096";

        String numeroInscricao = NumeroInscricaoUtil.formatarNumeroInscricao(numero);

        assertEquals("202400009010" + CalculoModuloOnzeUtil.getDigito("202400009010"), numeroInscricao);

    }

    @Test
    void test_calcular_numero_aleatorio() {

        String numero = "2024000098456";

        String numeroInscricao = NumeroInscricaoUtil.formatarNumeroInscricao(numero);

        assertEquals("202400009846" + CalculoModuloOnzeUtil.getDigito("202400009846"), numeroInscricao);

    }

    @Test
    void test_calcular_outro_numero_aleatorio() {

        String numero = "2024000098496";

        String numeroInscricao = NumeroInscricaoUtil.formatarNumeroInscricao(numero);

        assertEquals("202400009850" + CalculoModuloOnzeUtil.getDigito("202400009850"), numeroInscricao);

    }

    @Test
    void test_numero_limite_atingido() {
        String numero = "2024999999996";

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            NumeroInscricaoUtil.formatarNumeroInscricao(numero);
        });

        assertEquals(MessageCommonsContanst.ATINGIU_LIMITE, exception.getMessage());
    }

}
