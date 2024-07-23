package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DividaPessoaTest {

  public static DividaPessoa getDividaPessoaFilter() {
    DividaPessoa divida = new DividaPessoa();
    divida.setId(UUID.fromString("98e0a5f2-b784-4b54-86ea-0d119590d5c4"));
    divida.setPapelPessoaDivida(PapelPessoaDividaTest.getPapelPessoaSucessora());
    divida.setPessoa(PessoaTest.getPessoaSucessora());
    return divida;
  }

  public static DividaPessoa getDividaPessoaDevedora() {
    DividaPessoa divida = new DividaPessoa();
    divida.setId(UUID.fromString("98e0a5f2-b784-4b54-86ea-0d119590d5c4"));
    divida.setPapelPessoaDivida(PapelPessoaDividaTest.getPapelPessoaDevedora());
    return divida;
  }

  public static DividaPessoa getDividaPessoaSucessora() {

    DividaPessoa divida = new DividaPessoa();
    divida.setId(UUID.fromString("eadbdd19-0429-419a-82b7-99ddc564d8f6"));
    divida.setPapelPessoaDivida(PapelPessoaDividaTest.getPapelPessoaSucessora());
    divida.setDataDeclaracaoAusenciaContato(LocalDateTime.now());
    return divida;
  }

  public static DividaPessoa getDividaPessoaCorresponsavel() {

    DividaPessoa divida = new DividaPessoa();
    divida.setId(UUID.fromString("713957a2-87c1-42af-8e25-95b4c834836a"));
    divida.setPapelPessoaDivida(PapelPessoaDividaTest.getPapelPessoaCorresponsavel());
    divida.setDataDeclaracaoAusenciaContato(LocalDateTime.now());
    return divida;
  }

  @Test
  void asserts() {
    DividaPessoa dividaPessoa = getDividaPessoaSucessora();
    assertEquals(UUID.fromString("eadbdd19-0429-419a-82b7-99ddc564d8f6"), dividaPessoa.getId());
    assertNotNull(dividaPessoa.getPapelPessoaDivida());
    assertNotNull(dividaPessoa.getDataDeclaracaoAusenciaContato());
  }

  @Test
  void assert_divida() {
    DividaPessoa dividaPessoa = new DividaPessoa();
    dividaPessoa.setPessoa(PessoaTest.getPessoaDevedora());
    dividaPessoa.setDivida(DividaTest.getDivida());

    assertNotNull(dividaPessoa.getDivida());
    assertNotNull(dividaPessoa.getPessoa());
  }

}
