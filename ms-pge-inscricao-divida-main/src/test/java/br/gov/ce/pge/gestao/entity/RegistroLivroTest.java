package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegistroLivroTest {

  public static RegistroLivro getRegistroLivro() {
    var registroLivro = new RegistroLivro();

    registroLivro.setId(UUID.fromString("2375f1e0-4fc5-44a2-b549-5cf348891f2e"));

    registroLivro.setInscricao(InscricaoTest.get_inscricao_completa());

    return registroLivro;
  }

  @Test
  void test_mapping() {
    LivroEletronico livro = new LivroEletronico();
    livro.setId(UUID.randomUUID());
    livro.setNome("Livro Teste");

    Inscricao inscricao = new Inscricao();
    inscricao.setId(UUID.randomUUID());

    RegistroLivro registroLivro = new RegistroLivro();

    registroLivro.setId(UUID.randomUUID());
    registroLivro.setLivro(livro);
    registroLivro.setInscricao(inscricao);

    asserts(registroLivro);
  }

  private void asserts(RegistroLivro registroLivro) {
    assertNotNull(registroLivro);
    assertNotNull(registroLivro.getId());
    assertNotNull(registroLivro.getLivro());
    assertNotNull(registroLivro.getLivro().getId());
    assertNotNull(registroLivro.getInscricao());
    assertNotNull(registroLivro.getInscricao().getId());
  }

}
