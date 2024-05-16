package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.LivroEletronicoResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import br.gov.ce.pge.gestao.entity.RegistroLivro;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LivroEletronicoMapperToDtoTest {

  @Test
  void test_converter() {
    LivroEletronico livroEletronico = new LivroEletronico();
    livroEletronico.setId(UUID.randomUUID());
    livroEletronico.setNome("Livro Exemplo");
    livroEletronico.setSituacao(SituacaoLivro.ABERTO);
    livroEletronico.setDataAbertura(LocalDateTime.now().minusDays(5));
    livroEletronico.setDataFechamento(LocalDateTime.now());
    List<RegistroLivro> registros = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      RegistroLivro registro = new RegistroLivro();
      registro.setId(UUID.randomUUID());
      registro.setDataCriacao(LocalDateTime.now());
      registros.add(registro);
    }
    livroEletronico.setRegistros(registros);

    LivroEletronicoResponseDto dto = LivroEletronicoMapperToDto.converter(livroEletronico);

    asserts(livroEletronico, dto);

    int quantidadeRegistros = livroEletronico.getRegistros().size();
    int registrosPorPagina = 50;
    Integer paginas = (int) Math.ceil((double) quantidadeRegistros / registrosPorPagina);

    assertEquals(paginas, dto.getPaginas());
  }

  private void asserts(LivroEletronico livroEletronico, LivroEletronicoResponseDto dto) {
    assertNotNull(dto);

    assertEquals(livroEletronico.getId(), dto.getId());
    assertEquals(livroEletronico.getNome(), dto.getNome());
    assertEquals(livroEletronico.getSituacao(), dto.getSituacao());
    assertEquals(livroEletronico.getDataAbertura(), dto.getDataAbertura());
    assertEquals(livroEletronico.getDataFechamento(), dto.getDataFechamento());
  }
}
