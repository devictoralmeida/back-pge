package br.gov.ce.pge.gestao.mappers.todto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.AcaoJudicial;
import br.gov.ce.pge.gestao.dto.response.AcaoJudicialDividaResponseDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

class AcaoJudicialDividaMapperToDtoTest {
  @Test
  void deve_converter_divida_sem_acoes_judiciais_para_dto() {
    Divida divida = new Divida();
    divida.setId(UUID.randomUUID());
    divida.setAcoesJudiciais(Collections.emptyList());

    AcaoJudicialDividaResponseDto resultado = AcaoJudicialDividaMapperToDto.toDto(divida);

    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(divida.getId(), resultado.getId());
    Assertions.assertTrue(resultado.getAcoesJudiciais().isEmpty());
  }

  @Test
  void deve_converter_divida_com_acoes_judiciais_para_dto() {
    Divida divida = new Divida();
    divida.setId(UUID.randomUUID());

    AcaoJudicial acaoJudicial1 = new AcaoJudicial();
    acaoJudicial1.setId(UUID.randomUUID());
    AcaoJudicial acaoJudicial2 = new AcaoJudicial();
    acaoJudicial2.setId(UUID.randomUUID());

    divida.setAcoesJudiciais(Arrays.asList(acaoJudicial1, acaoJudicial2));

    AcaoJudicialDividaResponseDto resultado = AcaoJudicialDividaMapperToDto.toDto(divida);

    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(divida.getId(), resultado.getId());
    Assertions.assertFalse(resultado.getAcoesJudiciais().isEmpty());
    Assertions.assertEquals(2, resultado.getAcoesJudiciais().size());
  }
}
