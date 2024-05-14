package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebitoResponseDtoTest {

  public static DebitoResponseDto get_debito() {
    var dto = new DebitoResponseDto();
    dto.setReferenciaInicial("01/2024");
    dto.setReferenciaFinal("10/2024");
    dto.setDataVencimento(LocalDate.parse("2024-03-30"));
    dto.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
    dto.setValorPrincipal(BigDecimal.valueOf(150));
    dto.setValorMulta(BigDecimal.valueOf(50));

    return dto;
  }

  public static List<DebitoResponseDto> getDebitos() {
    return Arrays.asList(get_debito());
  }

  @Test
  void teste_debito() {
    var dto = get_debito();
    assertEquals("01/2024", dto.getReferenciaInicial());
    assertEquals("10/2024", dto.getReferenciaFinal());
    assertEquals(LocalDate.parse("2024-03-30"), dto.getDataVencimento());
    assertEquals(LocalDate.parse("2024-03-30"), dto.getDataConstituicaoDefinitivaCredito());
    assertEquals(BigDecimal.valueOf(150), dto.getValorPrincipal());
    assertEquals(BigDecimal.valueOf(50), dto.getValorMulta());
  }

}
