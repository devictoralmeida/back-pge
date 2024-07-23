package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ContatoTest {

  public static Contato getContatoEmail() {
    Contato contato = new Contato();
    contato.setId(UUID.fromString("6f0ee30c-e876-46c4-b79f-2a9fa48d1b24"));
    contato.setTipoContato(TipoContatoTest.getTipoContatoEmail());
    contato.setValorContato("teste@gmail.com");

    return contato;
  }

  public static Contato getContatoCelular() {
    Contato contato = new Contato();
    contato.setId(UUID.fromString("ab4fd11c-d9d5-40a6-9ce9-c71c42e7fdc7"));
    contato.setTipoContato(TipoContatoTest.getTipoContatoCelular());
    contato.setValorContato("85999999999");
    contato.setNumeroDdiContato("55");
    return contato;
  }

  public static List<Contato> getContatosList() {
    return Arrays.asList(ContatoTest.getContatoEmail(), ContatoTest.getContatoCelular());
  }

  @Test
  void asserts() {
    Contato contato = getContatoEmail();

    assertEquals(UUID.fromString("6f0ee30c-e876-46c4-b79f-2a9fa48d1b24"), contato.getId());
    assertNotNull(contato.getTipoContato());
    assertEquals("teste@gmail.com", contato.getValorContato());
    assertNull(contato.getNumeroDdiContato());
  }

  @Test
  void assert_contato_pessoa() {
    Contato contato = new Contato();
    contato.setPessoa(PessoaTest.getPessoaDevedora());
    assertNotNull(contato.getPessoa());
  }

}
