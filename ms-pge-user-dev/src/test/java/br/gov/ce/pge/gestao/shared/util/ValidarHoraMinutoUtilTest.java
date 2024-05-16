package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidarHoraMinutoUtilTest {
    
	@Test
    void test_horario_valido() {
        assertDoesNotThrow(() -> ValidarHoraMinutoUtil.validarHorario("12:34"));
    }

    @Test
    void test_horario_valido_tres_digitos_horas() {
        assertDoesNotThrow(() -> ValidarHoraMinutoUtil.validarHorario("123:34"));
    }

    @Test
    void test_quantidade_incorreta_dois_pontos() {
        assertThrows(NegocioException.class, () -> ValidarHoraMinutoUtil.validarHorario("12:34:56"));
    }

    @Test
    void test_minutos_com_mais_de_dois_digitos() {
        assertThrows(NegocioException.class, () -> ValidarHoraMinutoUtil.validarHorario("12:345"));
    }

    @Test
    void test_minutos_fora_do_intervalo() {
        assertThrows(NegocioException.class, () -> ValidarHoraMinutoUtil.validarHorario("12:60"));
    }

    @Test
    void test_com_letras() {
        assertThrows(NegocioException.class, () -> ValidarHoraMinutoUtil.validarHorario("asdasda"));
    }
}
