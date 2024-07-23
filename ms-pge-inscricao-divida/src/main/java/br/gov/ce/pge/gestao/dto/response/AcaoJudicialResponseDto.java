package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AcaoJudicialResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 8448942745105985475L;


  private UUID id;
  private String numeroOrdemJudicial;

  public AcaoJudicialResponseDto(AcaoJudicial entity) {
    id = entity.getId();
    numeroOrdemJudicial = entity.getNumeroOrdemJudicial();
  }
}
