package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LivroEletronicoResponseDtoTest {

  public static LivroEletronicoResponseDto getLivroResponseDto() {
    var livroResponse = new LivroEletronicoResponseDto();
    livroResponse.setPaginas(1);
    livroResponse.setTotalLinhasUltimaPagina(1);
    livroResponse.setNome("Meu Livro Eletrônico");
    livroResponse.setDataAbertura(LocalDateTime.now());
    livroResponse.setSituacao(SituacaoLivro.ABERTO);
    livroResponse.setId(UUID.fromString("3b2469d6-8951-44be-afed-06c14824d300"));

    return livroResponse;
  }

  @Test
  void test_getter_and_setter() {
    LivroEletronicoResponseDto dto = new LivroEletronicoResponseDto();

    UUID id = UUID.randomUUID();
    String nome = "Meu Livro Eletrônico";
    SituacaoLivro situacao = SituacaoLivro.ABERTO;
    LocalDateTime dataAbertura = LocalDateTime.of(2024, Month.MAY, 6, 10, 0);
    LocalDateTime dataFechamento = LocalDateTime.of(2024, Month.MAY, 10, 15, 30);
    Integer paginas = 100;

    dto.setId(id);
    dto.setNome(nome);
    dto.setSituacao(situacao);
    dto.setDataAbertura(dataAbertura);
    dto.setDataFechamento(dataFechamento);
    dto.setPaginas(paginas);

    asserts(id, dto, nome, situacao, dataAbertura, dataFechamento, paginas);
  }

  @Test
  void test_no_args_constructor() {
    LivroEletronicoResponseDto dto = new LivroEletronicoResponseDto();
    assertNotNull(dto);
  }

  @Test
  void test_all_args_constructor() {
    UUID id = UUID.randomUUID();
    String nome = "Meu Livro Eletrônico";
    SituacaoLivro situacao = SituacaoLivro.ABERTO;
    LocalDateTime dataAbertura = LocalDateTime.of(2024, Month.MAY, 6, 10, 0);
    LocalDateTime dataFechamento = LocalDateTime.of(2024, Month.MAY, 10, 15, 30);
    Integer paginas = 100;

    LivroEletronicoResponseDto dto = new LivroEletronicoResponseDto(id, nome, situacao, dataAbertura, dataFechamento, paginas, null);

    asserts(id, dto, nome, situacao, dataAbertura, dataFechamento, paginas);
  }

  private void asserts(UUID id, LivroEletronicoResponseDto dto, String nome, SituacaoLivro situacao, LocalDateTime dataAbertura, LocalDateTime dataFechamento, Integer paginas) {
    assertEquals(id, dto.getId());
    assertEquals(nome, dto.getNome());
    assertEquals(situacao, dto.getSituacao());
    assertEquals(dataAbertura, dto.getDataAbertura());
    assertEquals(dataFechamento, dto.getDataFechamento());
    assertEquals(paginas, dto.getPaginas());
  }
}
