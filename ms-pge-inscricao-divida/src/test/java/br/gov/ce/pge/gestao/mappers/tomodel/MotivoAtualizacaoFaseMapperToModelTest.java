package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoFase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MotivoAtualizacaoFaseMapperToModelTest {
  @Test
  void teste_criar_motivo_fase_inicial() {
    MotivoAtualizacaoFase motivo = MotivoAtualizacaoFaseMapperToModel.criarMotivoFaseInicial();
    assertEquals(SharedConstant.FASE_INICIAL, motivo.getNome());
  }
}
