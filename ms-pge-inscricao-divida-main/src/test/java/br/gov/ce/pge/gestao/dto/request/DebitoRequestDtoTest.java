package br.gov.ce.pge.gestao.dto.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitoRequestDtoTest {

    public static DebitoRequestDto getDebito() {
        var dto = new DebitoRequestDto();
        dto.setReferenciaInicial("01/2024");
        dto.setReferenciaFinal("10/2024");
        dto.setDataVencimento(LocalDate.parse("2024-03-30"));
        dto.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
        dto.setValorPrincipal(BigDecimal.valueOf(150));
        dto.setValorMulta(BigDecimal.valueOf(50));

        return dto;
    }

    public static List<DebitoRequestDto> get_debitos_list() {
        return List.of(getDebito());
    }

    @Test
    void teste_debito() {
        var dto = getDebito();
        assertEquals("01/2024", dto.getReferenciaInicial());
        assertEquals("10/2024", dto.getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-30"), dto.getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), dto.getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), dto.getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), dto.getValorMulta());
    }

}
