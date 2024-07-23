package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EnderecoTest {

  public static Endereco getEndereco() {
    Endereco endereco = new Endereco();
    endereco.setBairro("Centro");
    endereco.setCep("60332230");
    endereco.setComplemento("Casa");
    endereco.setDistrito("DF");
    endereco.setLogradouro("Rua Teste");
    endereco.setMunicipio("Teste");
    endereco.setPrincipal(Boolean.TRUE);
    endereco.setUf("TE");
    endereco.setNumero("50");
    endereco.setId(UUID.fromString("8c7501b1-b2f5-4b30-a3b0-b8f92feb0a63"));
    endereco.setPessoa(PessoaTest.getPessoaDevedora());

    return endereco;
  }

  public static Endereco getEnderecoDiferente() {
    Endereco endereco = new Endereco();
    endereco.setBairro("Centro 2");
    endereco.setCep("60332231");
    endereco.setComplemento("Casaa");
    endereco.setDistrito("CE");
    endereco.setLogradouro("Rua Teste 2");
    endereco.setMunicipio("Teste 2");
    endereco.setPrincipal(Boolean.TRUE);
    endereco.setUf("CE");
    endereco.setNumero("51");
    endereco.setId(UUID.fromString("8c7501b1-b2f5-4b30-a3b0-b8f92feb0a64"));
    endereco.setPessoa(PessoaTest.getPessoaDevedora());

    return endereco;
  }

  @Test
  void teste_endereco() {

    Endereco endereco = getEndereco();

    assertNotNull(endereco);
    assertEquals("Teste", endereco.getMunicipio());
    assertEquals("TE", endereco.getUf());
    assertEquals("50", endereco.getNumero());
    assertEquals(Boolean.TRUE, endereco.getPrincipal());
    assertEquals("Rua Teste", endereco.getLogradouro());
    assertEquals("DF", endereco.getDistrito());
    assertEquals("Casa", endereco.getComplemento());
    assertEquals("60332230", endereco.getCep());
    assertEquals("Centro", endereco.getBairro());
    assertEquals(UUID.fromString("8c7501b1-b2f5-4b30-a3b0-b8f92feb0a63"), endereco.getId());
    assertNotNull(endereco.getPessoa());
  }

}
