package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PessoaTest {

  public static Pessoa getPessoaDevedora() {

    Pessoa pessoa = new Pessoa();
    pessoa.setId(UUID.fromString("6c889dcf-c1d7-46c1-ba53-2c9d788836c8"));
    pessoa.setNomeRazaoSocial("Teste 1");
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaFisica());
    pessoa.setDocumento("04987612234");
    pessoa.setContatos(ContatoTest.getContatosList());
    pessoa.getDividaPessoas().add(DividaPessoaTest.getDividaPessoaDevedora());

    return pessoa;
  }

  public static Pessoa getPessoaSucessora() {

    Pessoa pessoa = new Pessoa();
    pessoa.setId(UUID.fromString("6c889dcf-c1d7-46c1-ba53-2c9d788836c9"));
    pessoa.setCgf("075035251");
    pessoa.setNomeRazaoSocial("Teste 2");
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaJuridica());
    pessoa.setDocumento("95614886000170");
    pessoa.getDividaPessoas().add(DividaPessoaTest.getDividaPessoaSucessora());

    return pessoa;
  }

  public static Pessoa getPessoaCorresponsavel() {

    Pessoa pessoa = new Pessoa();
    pessoa.setId(UUID.fromString("6c889dcf-c1d7-46c1-ba53-2c9d788836c7"));
    pessoa.setCgf("075035252");
    pessoa.setNomeRazaoSocial("Teste 3");
    pessoa.setTipoPessoa(TipoPessoaTest.getTipoPessoaJuridica());
    pessoa.setDocumento("53149639000108");
    pessoa.getDividaPessoas().add(DividaPessoaTest.getDividaPessoaCorresponsavel());

    return pessoa;
  }

  @Test
  void asserts() {
    Pessoa pessoa = getPessoaDevedora();

    assertNotNull(pessoa);
    assertEquals(UUID.fromString("6c889dcf-c1d7-46c1-ba53-2c9d788836c8"), pessoa.getId());
    assertEquals("Teste 1", pessoa.getNomeRazaoSocial());
    assertNotNull(pessoa.getTipoPessoa());
    assertNotNull(pessoa.getContatos());
    assertNotNull(pessoa.getDividaPessoas());
    assertEquals("04987612234", pessoa.getDocumento());
  }

  @Test
  void assertEndereco() {
    Pessoa pessoa = new Pessoa();
    pessoa.setEnderecos(Arrays.asList(EnderecoTest.getEndereco()));
    assertNotNull(pessoa.getEnderecos());
  }

}
