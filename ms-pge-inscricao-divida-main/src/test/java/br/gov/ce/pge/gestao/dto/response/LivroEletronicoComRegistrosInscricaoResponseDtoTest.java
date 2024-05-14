package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivroEletronicoComRegistrosInscricaoResponseDtoTest {
  public static LivroEletronicoComRegistrosInscricaoResponseDto getLivroEletronicoComRegistrosInscricao() {
    LivroEletronicoComRegistrosInscricaoResponseDto dto = new LivroEletronicoComRegistrosInscricaoResponseDto();
    UUID id = UUID.fromString(("c4095434-f704-4209-be74-3d42d519d438"));
    String nome = "2024";
    SituacaoLivro situacao = SituacaoLivro.ABERTO;
    LocalDateTime dataAbertura = LocalDateTime.now();
    LocalDateTime dataFechamento = null;

    List<RegistroInscricaoResponseDto> registros = List.of(
            new RegistroInscricaoResponseDto(UUID.randomUUID(), "documento", "cgf", "nome", "origemDebito", BigDecimal.ONE, "usuario", LocalDateTime.now())
    );

    dto.setId(id);
    dto.setNome(nome);
    dto.setSituacao(situacao);
    dto.setDataAbertura(dataAbertura);
    dto.setDataFechamento(dataFechamento);
    dto.setRegistros(registros);

    return dto;
  }

  @Test
  void testGetterSetter() {
    LivroEletronicoComRegistrosInscricaoResponseDto dto = new LivroEletronicoComRegistrosInscricaoResponseDto();
    UUID id = UUID.randomUUID();
    String nome = "Livro de Teste";
    SituacaoLivro situacao = SituacaoLivro.ABERTO;
    LocalDateTime dataAbertura = LocalDateTime.now();
    LocalDateTime dataFechamento = LocalDateTime.now();
    List<RegistroInscricaoResponseDto> registros = List.of(
            new RegistroInscricaoResponseDto(UUID.randomUUID(), "documento", "cgf", "nome", "origemDebito", BigDecimal.ONE, "usuario", LocalDateTime.now())
    );

    dto.setId(id);
    dto.setNome(nome);
    dto.setSituacao(situacao);
    dto.setDataAbertura(dataAbertura);
    dto.setDataFechamento(dataFechamento);
    dto.setRegistros(registros);

    assertEquals(id, dto.getId());
    assertEquals(nome, dto.getNome());
    assertEquals(situacao, dto.getSituacao());
    assertEquals(dataAbertura, dto.getDataAbertura());
    assertEquals(dataFechamento, dto.getDataFechamento());
    assertEquals(registros, dto.getRegistros());
  }
}
