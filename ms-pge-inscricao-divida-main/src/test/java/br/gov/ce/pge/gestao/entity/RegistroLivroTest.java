package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegistroLivroTest {
  @Test
  void testMapping() {
    LivroEletronico livro = new LivroEletronico();
    livro.setId(UUID.randomUUID());
    livro.setNome("Livro Teste");

    Inscricao inscricao = new Inscricao();
    inscricao.setId(UUID.randomUUID());

    RegistroLivro registroLivro = new RegistroLivro();

    registroLivro.setId(UUID.randomUUID());
    registroLivro.setLivro(livro);
    registroLivro.setInscricao(inscricao);

    assertNotNull(registroLivro);
    assertNotNull(registroLivro.getId());
    assertNotNull(registroLivro.getLivro());
    assertNotNull(registroLivro.getLivro().getId());
    assertNotNull(registroLivro.getInscricao());
    assertNotNull(registroLivro.getInscricao().getId());
  }

}
