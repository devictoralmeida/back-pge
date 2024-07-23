package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PapelPessoaDividaTest {
  public static PapelPessoaDivida getPapelPessoaDevedora() {
    PapelPessoaDivida papelPessoaDivida = new PapelPessoaDivida();
    papelPessoaDivida.setId(UUID.fromString("6c31c99b-7db4-4bb6-8042-703939960060"));
    papelPessoaDivida.setTipoPapelPessoaDivida(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor());
    papelPessoaDivida.setTipoDevedor(TipoDevedorTest.getTipoDevedor());

    return papelPessoaDivida;
  }

  public static PapelPessoaDivida getPapelPessoaSucessora() {
    PapelPessoaDivida papelPessoaDivida = new PapelPessoaDivida();
    papelPessoaDivida.setId(UUID.fromString("97faac03-f198-465e-a714-f98c0ca8bb96"));
    papelPessoaDivida.setTipoPapelPessoaDivida(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor());

    return papelPessoaDivida;
  }

  public static PapelPessoaDivida getPapelPessoaCorresponsavel() {
    PapelPessoaDivida papelPessoaDivida = new PapelPessoaDivida();
    papelPessoaDivida.setId(UUID.fromString("9603b562-68af-438b-8700-01aadc667c13"));
    papelPessoaDivida.setTipoPapelPessoaDivida(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel());
    papelPessoaDivida.setQualificacaoCorresponsavel(QualificacaoCorresponsavelTest.getQualificacao());

    return papelPessoaDivida;
  }

  @Test
  void asserts() {
    PapelPessoaDivida papelPessoa = getPapelPessoaDevedora();

    assertEquals(UUID.fromString("6c31c99b-7db4-4bb6-8042-703939960060"), papelPessoa.getId());
    assertEquals(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId(), papelPessoa.getTipoPapelPessoaDivida().getId());
    assertNotNull(papelPessoa.getTipoDevedor());
  }

}
