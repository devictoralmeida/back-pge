package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.LivroEletronicoComRegistrosInscricaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.enums.SituacaoLivro;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LivroEletronicoComRegistrosInscricaotoDto {

  public static LivroEletronicoComRegistrosInscricaoResponseDto mapToDto(List<Object[]> results) {
    LivroEletronicoComRegistrosInscricaoResponseDto livroDto = new LivroEletronicoComRegistrosInscricaoResponseDto();
    List<RegistroInscricaoResponseDto> registros = new ArrayList<>();

    for (Object[] result : results) {
      if (livroDto.getId() == null) {
        livroDto.setId((UUID) result[0]);
        livroDto.setNome((String) result[1]);
        livroDto.setSituacao((SituacaoLivro) result[2]);
        livroDto.setDataAbertura((LocalDateTime) result[3]);
        livroDto.setDataFechamento((LocalDateTime) result[4]);
      }

      RegistroInscricaoResponseDto registroDto = getRegistroInscricaoResponseDto(result);

      registros.add(registroDto);
    }

    livroDto.setRegistros(registros);
    return livroDto;
  }

  private static RegistroInscricaoResponseDto getRegistroInscricaoResponseDto(Object[] result) {
    RegistroInscricaoResponseDto registroDto = new RegistroInscricaoResponseDto();
    registroDto.setNumeroInscricao(result[5].toString());
    registroDto.setDocumento((String) result[6]);
    registroDto.setCgf((String) result[7]);
    registroDto.setNomeRazaoSocial((String) result[8]);
    registroDto.setOrigemDebito((String) result[9]);
    BigDecimal valorPrincipal = (BigDecimal) result[10];

    registroDto.setNomeUsuario((String) result[11]);
    registroDto.setDataRegistro((LocalDateTime) result[12]);
    BigDecimal valorMulta = (BigDecimal) result[13];
    registroDto.setValorPrincipal(valorPrincipal.add(valorMulta));
    return registroDto;
  }

}
