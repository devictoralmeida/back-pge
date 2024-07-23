package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.DividaTest;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FaseDividaMapperToModelTest {
  @Test
  void teste_criar_fase_inicial() {
    Divida divida = DividaTest.getDivida();
    FaseDivida faseDivida = FaseDividaMapperToModel.criarFaseInicial(divida);

    assertEquals(SharedConstant.FASE_INICIAL, faseDivida.getObservacao());
    assertEquals(true, faseDivida.getFaseAtual());
    assertEquals(false, faseDivida.getFaseAnterior());
    assertNotNull(faseDivida.getDivida());
  }

}
