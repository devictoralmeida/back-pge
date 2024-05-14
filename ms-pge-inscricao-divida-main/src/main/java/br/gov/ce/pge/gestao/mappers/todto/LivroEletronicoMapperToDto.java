package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.LivroEletronicoResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;

public class LivroEletronicoMapperToDto {
  private LivroEletronicoMapperToDto() {

  }

  public static LivroEletronicoResponseDto converter(LivroEletronico livroEletronico) {
    var dto = new LivroEletronicoResponseDto();
    dto.setId(livroEletronico.getId());
    dto.setNome(livroEletronico.getNome());
    dto.setSituacao(livroEletronico.getSituacao());
    dto.setDataAbertura(livroEletronico.getDataAbertura());
    dto.setDataFechamento(livroEletronico.getDataFechamento());

    int quantidadeRegistros = livroEletronico.getRegistros().size();
    int registrosPorPagina = 50;

    Integer paginas = (int) Math.ceil((double) quantidadeRegistros / registrosPorPagina);
    dto.setPaginas(paginas);
    return dto;
  }
}
