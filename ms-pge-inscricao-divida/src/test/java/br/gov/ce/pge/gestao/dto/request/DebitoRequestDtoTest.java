package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.StatusDebitoTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitoRequestDtoTest {
  public static DebitoRequestDto getDebito() {
    DebitoRequestDto dto = new DebitoRequestDto();
    dto.setReferenciaInicial("01/2024");
    dto.setReferenciaFinal("03/2024");
    dto.setDataVencimento(LocalDate.parse("2024-03-29"));
    dto.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
    dto.setValorPrincipal(BigDecimal.valueOf(150));
    dto.setValorMulta(BigDecimal.valueOf(50));
    dto.setDataInicioAtualizacaoMonetaria(LocalDate.parse("2024-03-25"));
    dto.setIdStatusDebito(StatusDebitoTest.getStatusDebito().getId());

    return dto;
  }

  public static List<DebitoRequestDto> getDebitosList() {
    DebitoRequestDto dto = new DebitoRequestDto();
    dto.setReferenciaInicial("01/2024");
    dto.setReferenciaFinal("02/2024");
    dto.setDataVencimento(LocalDate.parse("2024-03-29"));
    dto.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
    dto.setValorPrincipal(BigDecimal.valueOf(200));
    dto.setValorMulta(BigDecimal.valueOf(100));
    dto.setDataInicioAtualizacaoMonetaria(LocalDate.parse("2024-03-25"));
    dto.setIdStatusDebito(StatusDebitoTest.getStatusDebito().getId());

    List<DebitoRequestDto> list = new ArrayList<>();
    list.add(getDebito());
    list.add(dto);

    return list;
  }

  public static List<DebitoRequestDto> getDebitosListDiferente() {
    DebitoRequestDto dto = new DebitoRequestDto();
    dto.setReferenciaInicial("04/2024");
    dto.setReferenciaFinal("05/2024");
    dto.setDataVencimento(LocalDate.parse("2024-03-27"));
    dto.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-28"));
    dto.setValorPrincipal(BigDecimal.valueOf(200));
    dto.setValorMulta(BigDecimal.valueOf(70));
    dto.setDataInicioAtualizacaoMonetaria(LocalDate.parse("2024-03-25"));
    dto.setIdStatusDebito(StatusDebitoTest.getStatusDebito().getId());

    List<DebitoRequestDto> list = new ArrayList<>();
    list.add(getDebito());
    list.add(dto);

    return list;
  }

  @Test
  void teste_debito() {
    DebitoRequestDto dto = getDebito();
    assertEquals("01/2024", dto.getReferenciaInicial());
    assertEquals("03/2024", dto.getReferenciaFinal());
    assertEquals(LocalDate.parse("2024-03-29"), dto.getDataVencimento());
    assertEquals(LocalDate.parse("2024-03-30"), dto.getDataConstituicaoDefinitivaCredito());
    assertEquals(BigDecimal.valueOf(150), dto.getValorPrincipal());
    assertEquals(BigDecimal.valueOf(50), dto.getValorMulta());
  }

}
