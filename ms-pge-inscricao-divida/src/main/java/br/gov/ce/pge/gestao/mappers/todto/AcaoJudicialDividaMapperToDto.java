package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.AcaoJudicialDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.AcaoJudicialResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;

import java.util.ArrayList;
import java.util.List;

public class AcaoJudicialDividaMapperToDto {
  private AcaoJudicialDividaMapperToDto() {
  }

  public static AcaoJudicialDividaResponseDto toDto(Divida divida) {
    AcaoJudicialDividaResponseDto acaoJudicialDividaResponseDto = new AcaoJudicialDividaResponseDto();
    acaoJudicialDividaResponseDto.setId(divida.getId());

    List<AcaoJudicialResponseDto> acoesJudiciais = new ArrayList<>();
    divida.getAcoesJudiciais().forEach(acaoJudicial -> {
      AcaoJudicialResponseDto acaoJudicialResponseDto = new AcaoJudicialResponseDto(acaoJudicial);
      acoesJudiciais.add(acaoJudicialResponseDto);
    });

    acaoJudicialDividaResponseDto.setAcoesJudiciais(acoesJudiciais);
    return acaoJudicialDividaResponseDto;
  }
}
