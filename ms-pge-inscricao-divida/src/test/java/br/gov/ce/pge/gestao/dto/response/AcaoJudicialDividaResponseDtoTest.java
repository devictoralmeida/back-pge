package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.DividaTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

public class AcaoJudicialDividaResponseDtoTest {

  public static AcaoJudicialDividaResponseDto getAcaoJudicialDividaResponseDto() {
    AcaoJudicialDividaResponseDto acaoJudicialDividaResponseDto = new AcaoJudicialDividaResponseDto();
    acaoJudicialDividaResponseDto.setId(DividaTest.getDividaComAcoesJudiciais().getId());
    acaoJudicialDividaResponseDto.setAcoesJudiciais(Collections.singletonList(AcaoJudicialResponseDtoTest.getDto()));
    return acaoJudicialDividaResponseDto;
  }

  @Test
  void deve_corretamente_atribuir_id() {
    UUID expectedId = UUID.randomUUID();
    AcaoJudicialDividaResponseDto dto = new AcaoJudicialDividaResponseDto();
    dto.setId(expectedId);

    Assertions.assertEquals(expectedId, dto.getId());
  }

  @Test
  void deve_corretamente_atribuir_acoes_judiciais() {
    AcaoJudicialResponseDto acaoJudicial1 = new AcaoJudicialResponseDto();
    AcaoJudicialResponseDto acaoJudicial2 = new AcaoJudicialResponseDto();
    List<AcaoJudicialResponseDto> expectedAcoesJudiciais = Arrays.asList(acaoJudicial1, acaoJudicial2);
    AcaoJudicialDividaResponseDto dto = new AcaoJudicialDividaResponseDto();
    dto.setAcoesJudiciais(expectedAcoesJudiciais);

    Assertions.assertEquals(expectedAcoesJudiciais, dto.getAcoesJudiciais());
  }

  @Test
  void deve_retornar_lista_imutavel_de_acoes_judiciais() {
    AcaoJudicialDividaResponseDto dto = new AcaoJudicialDividaResponseDto();
    dto.setAcoesJudiciais(Collections.singletonList(new AcaoJudicialResponseDto()));

    Assertions.assertThrows(UnsupportedOperationException.class, () -> dto.getAcoesJudiciais().add(new AcaoJudicialResponseDto()));
  }
}
