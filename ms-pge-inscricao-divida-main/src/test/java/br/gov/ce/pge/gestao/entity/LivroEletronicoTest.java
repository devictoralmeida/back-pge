package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LivroEletronicoTest {
  @Test
  void testMapping() {
    LivroEletronico livro = new LivroEletronico();
    livro.setId(UUID.randomUUID());
    livro.setNome("Meu Livro");
    livro.setSituacao(SituacaoLivro.ABERTO);
    LocalDateTime dataAbertura = LocalDateTime.now();
    livro.setDataAbertura(dataAbertura);
    LocalDateTime dataFechamento = LocalDateTime.now().plusDays(1);
    livro.setDataFechamento(dataFechamento);
    livro.setUsuarioResponsavel("Usuario1");

    RegistroLivro registroLivro1 = new RegistroLivro();
    RegistroLivro registroLivro2 = new RegistroLivro();
    List<RegistroLivro> registros = new ArrayList<>();
    registros.add(registroLivro1);
    registros.add(registroLivro2);

    livro.setRegistros(registros);

    assertNotNull(livro);
    assertNotNull(livro.getId());
    assertEquals("Meu Livro", livro.getNome());
    assertEquals(SituacaoLivro.ABERTO, livro.getSituacao());
    assertEquals(dataAbertura, livro.getDataAbertura());
    assertEquals(dataFechamento, livro.getDataFechamento());
    assertEquals("Usuario1", livro.getUsuarioResponsavel());
    assertEquals(registros, livro.getRegistros());
  }
}
