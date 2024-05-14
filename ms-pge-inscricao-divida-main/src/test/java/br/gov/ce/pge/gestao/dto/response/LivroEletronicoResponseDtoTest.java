package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LivroEletronicoResponseDtoTest {
  @Test
  void testGetterAndSetter() {
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

    assertEquals(id, dto.getId());
    assertEquals(nome, dto.getNome());
    assertEquals(situacao, dto.getSituacao());
    assertEquals(dataAbertura, dto.getDataAbertura());
    assertEquals(dataFechamento, dto.getDataFechamento());
    assertEquals(paginas, dto.getPaginas());
  }

  @Test
  void testNoArgsConstructor() {
    LivroEletronicoResponseDto dto = new LivroEletronicoResponseDto();
    assertNotNull(dto);
  }

  @Test
  void testAllArgsConstructor() {
    UUID id = UUID.randomUUID();
    String nome = "Meu Livro Eletrônico";
    SituacaoLivro situacao = SituacaoLivro.ABERTO;
    LocalDateTime dataAbertura = LocalDateTime.of(2024, Month.MAY, 6, 10, 0);
    LocalDateTime dataFechamento = LocalDateTime.of(2024, Month.MAY, 10, 15, 30);
    Integer paginas = 100;

    LivroEletronicoResponseDto dto = new LivroEletronicoResponseDto(id, nome, situacao, dataAbertura, dataFechamento, paginas);

    assertEquals(id, dto.getId());
    assertEquals(nome, dto.getNome());
    assertEquals(situacao, dto.getSituacao());
    assertEquals(dataAbertura, dto.getDataAbertura());
    assertEquals(dataFechamento, dto.getDataFechamento());
    assertEquals(paginas, dto.getPaginas());
  }
}
