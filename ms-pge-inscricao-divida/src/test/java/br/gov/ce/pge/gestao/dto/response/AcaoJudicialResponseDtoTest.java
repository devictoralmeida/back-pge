package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AcaoJudicialResponseDtoTest {
  public static AcaoJudicialResponseDto getDto() {
    AcaoJudicialResponseDto dto = new AcaoJudicialResponseDto();
    dto.setId(UUID.randomUUID());
    dto.setNumeroOrdemJudicial("OJ-2024-001");
    return dto;
  }

  @Test
  void teste_deve_corretamente_atribuir_acoes_judiciais() {
    AcaoJudicialResponseDto acaoJudicial = new AcaoJudicialResponseDto();
    List<AcaoJudicialResponseDto> expectedAcoesJudiciais = Collections.singletonList(acaoJudicial);
    AcaoJudicialDividaResponseDto dto = new AcaoJudicialDividaResponseDto();
    dto.setAcoesJudiciais(expectedAcoesJudiciais);

    assertEquals(expectedAcoesJudiciais, dto.getAcoesJudiciais());
    assertEquals(1, dto.getAcoesJudiciais().size());
    assertEquals(acaoJudicial, dto.getAcoesJudiciais().get(0));

  }
}
