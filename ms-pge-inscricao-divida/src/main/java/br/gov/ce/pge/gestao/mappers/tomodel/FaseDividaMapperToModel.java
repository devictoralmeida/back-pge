package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.FaseDivida;

import java.util.UUID;

public class FaseDividaMapperToModel {
  private FaseDividaMapperToModel() {
  }

  public static FaseDivida criarFaseInicial(Divida divida) {
    FaseDivida faseDivida = new FaseDivida();
    faseDivida.setObservacao(SharedConstant.FASE_INICIAL);
    faseDivida.setMotivoAtualizacaoFase(MotivoAtualizacaoFaseMapperToModel.criarMotivoFaseInicial());
    faseDivida.setDivida(divida);
    faseDivida.setIdFase(UUID.fromString(("11a3ee52-5757-440d-a11d-c69c42d3936b")));
    faseDivida.setFaseAtual(true);
    return faseDivida;
  }
}
