package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MotivoAtualizacaoDividaTest {
  public static MotivoAtualizacaoDivida getMotivoAtualizacao() {
    MotivoAtualizacaoDivida motivoAtualizacaoDivida = new MotivoAtualizacaoDivida();
    motivoAtualizacaoDivida.setId(UUID.fromString("9daa2753-6756-4fbb-b72f-90c020b78c2d"));
    motivoAtualizacaoDivida.setMotivo("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

    return motivoAtualizacaoDivida;
  }

  @Test
  void asserts() {
    MotivoAtualizacaoDivida motivo = getMotivoAtualizacao();

    assertEquals(UUID.fromString("9daa2753-6756-4fbb-b72f-90c020b78c2d"), motivo.getId());
    assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", motivo.getMotivo());
  }
}
