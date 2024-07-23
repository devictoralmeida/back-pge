package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoFase;

public class MotivoAtualizacaoFaseMapperToModel {
  private MotivoAtualizacaoFaseMapperToModel() {
  }

  public static MotivoAtualizacaoFase criarMotivoFaseInicial() {
    MotivoAtualizacaoFase motivo = new MotivoAtualizacaoFase();
    motivo.setNome(SharedConstant.FASE_INICIAL);
    return motivo;
  }
}
