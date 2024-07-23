package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AcaoJudicialDividaResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -6152443198511374966L;

  private UUID id;

  private List<AcaoJudicialResponseDto> acoesJudiciais;
}
