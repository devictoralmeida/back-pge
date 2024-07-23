package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoPapelPessoaDividaTest {

  public static TipoPapelPessoaDivida getTipoPapelPessoaDevedor() {
    TipoPapelPessoaDivida tipoPapelPessoaDivida = new TipoPapelPessoaDivida();
    tipoPapelPessoaDivida.setId(UUID.fromString("1e451c33-ae09-4912-a293-6a30f6d04116"));
    tipoPapelPessoaDivida.setTipoPapel("Devedor");
    return tipoPapelPessoaDivida;
  }

  public static TipoPapelPessoaDivida getTipoPapelPessoaSucessor() {
    TipoPapelPessoaDivida tipoPapelPessoaDivida = new TipoPapelPessoaDivida();
    tipoPapelPessoaDivida.setId(UUID.fromString("353d0b0b-ac4e-4615-aa2a-b7c9cdf4ea59"));
    tipoPapelPessoaDivida.setTipoPapel("Sucessor");
    return tipoPapelPessoaDivida;
  }

  public static TipoPapelPessoaDivida getTipoPapelPessoaCorresponsavel() {
    TipoPapelPessoaDivida tipoPapelPessoaDivida = new TipoPapelPessoaDivida();
    tipoPapelPessoaDivida.setId(UUID.fromString("9f26c891-1659-494a-a13e-407e11084410"));
    tipoPapelPessoaDivida.setTipoPapel("Corresponsavel");
    return tipoPapelPessoaDivida;
  }

  @Test
  void asserts() {
    TipoPapelPessoaDivida tipoPapelPessoaDividaDevedor = getTipoPapelPessoaDevedor();

    assertEquals(UUID.fromString("1e451c33-ae09-4912-a293-6a30f6d04116"), tipoPapelPessoaDividaDevedor.getId());
    assertEquals("Devedor", tipoPapelPessoaDividaDevedor.getTipoPapel());
  }
}
