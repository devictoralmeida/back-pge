package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LivroEletronicoFilterResponseDtoTest {
  public static LivroEletronicoFilterResponseDto getLivroEletronicoFilter() {

    var filter = new LivroEletronicoFilterResponseDto();
    filter.setPaginas(1);
    filter.setDataFechamento(null);
    filter.setDataAbertura(LocalDateTime.now());
    filter.setSituacao(SituacaoLivro.ABERTO);
    filter.setNome("Livro Teste");
    filter.setId("a6517828-5b79-45dd-9303-92b4ae3cce2b");

    return filter;
  }

  public static List<LivroEletronicoFilterResponseDto> getLivroEletronicoFilterList() {

    var filter = new LivroEletronicoFilterResponseDto();
    filter.setPaginas(1);
    filter.setDataFechamento(null);
    filter.setDataAbertura(LocalDateTime.now());
    filter.setSituacao(SituacaoLivro.ABERTO);
    filter.setNome("Livro Teste 2");
    filter.setId("c76b29eb-239f-4944-8862-4e83456323ec");

    return List.of(filter);
  }

  @Test
  void test_constructor_and_getters_setters() {
    String id = "123456";
    String nome = "Livro Teste";
    SituacaoLivro situacao = SituacaoLivro.ABERTO;
    LocalDateTime dataAbertura = LocalDateTime.now();
    LocalDateTime dataFechamento = LocalDateTime.now().plusDays(7);
    int paginas = 100;

    LivroEletronicoFilterResponseDto dto = new LivroEletronicoFilterResponseDto();
    dto.setId(id);
    dto.setNome(nome);
    dto.setSituacao(situacao);
    dto.setDataAbertura(dataAbertura);
    dto.setDataFechamento(dataFechamento);
    dto.setPaginas(paginas);

    asserts(id, nome, situacao, dataAbertura, dataFechamento, paginas, dto);
  }

  private void asserts(String id, String nome, SituacaoLivro situacao, LocalDateTime dataAbertura, LocalDateTime dataFechamento, int paginas, LivroEletronicoFilterResponseDto dto) {
    assertEquals(id, dto.getId());
    assertEquals(nome, dto.getNome());
    assertEquals(situacao, dto.getSituacao());
    assertEquals(dataAbertura, dto.getDataAbertura());
    assertEquals(dataFechamento, dto.getDataFechamento());
    assertEquals(paginas, dto.getPaginas());
  }

  @Test
  void testDefaultConstructor() {
    LivroEletronicoFilterResponseDto dto = new LivroEletronicoFilterResponseDto();

    assertNull(dto.getId());
    assertNull(dto.getNome());
    assertNull(dto.getSituacao());
    assertNull(dto.getDataAbertura());
    assertNull(dto.getDataFechamento());
    assertEquals(0, dto.getPaginas());
  }

}
