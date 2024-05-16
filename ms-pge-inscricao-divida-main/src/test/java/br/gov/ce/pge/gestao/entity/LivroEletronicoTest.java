package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LivroEletronicoTest {

  public static LivroEletronico getLivro() {
    var livro = new LivroEletronico();
    livro.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
    livro.setNome("Meu Livro");
    livro.setSituacao(SituacaoLivro.ABERTO);
    livro.setDataAbertura(LocalDateTime.now());
    livro.setDataFechamento(LocalDateTime.now().plusDays(1));
    livro.setUsuarioResponsavel("Usuario1");

    livro.setRegistros(Arrays.asList(RegistroLivroTest.getRegistroLivro()));

    return livro;
  }

  public static LivroEletronico getLivroSemRegistro() {
    var livro = new LivroEletronico();
    livro.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
    livro.setNome("Meu Livro");
    livro.setSituacao(SituacaoLivro.ABERTO);
    livro.setDataAbertura(LocalDateTime.now());
    livro.setDataFechamento(LocalDateTime.now().plusDays(1));
    livro.setUsuarioResponsavel("Usuario1");

    livro.setRegistros(Arrays.asList());

    return livro;
  }

  @Test
  void test_mapping() throws JsonProcessingException {
    LivroEletronico livro = this.getLivro();

    RegistroLivro registroLivro1 = new RegistroLivro();
    RegistroLivro registroLivro2 = new RegistroLivro();
    List<RegistroLivro> registros = new ArrayList<>();
    registros.add(registroLivro1);
    registros.add(registroLivro2);

    livro.setRegistros(registros);

    asserts(livro, registros, livro.toStringMapper());
  }

  private void asserts(LivroEletronico livro, List<RegistroLivro> registros, String toString) {
    assertNotNull(livro);
    assertNotNull(toString);
    assertNotNull(livro.getId());
    assertEquals("Meu Livro", livro.getNome());
    assertEquals(SituacaoLivro.ABERTO, livro.getSituacao());
    assertEquals(livro.getDataAbertura(), livro.getDataAbertura());
    assertEquals(livro.getDataFechamento(), livro.getDataFechamento());
    assertEquals("Usuario1", livro.getUsuarioResponsavel());
    assertEquals(registros, livro.getRegistros());
  }
}
