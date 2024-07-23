package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrigemDebitoResponseDto implements Serializable {

  private static final long serialVersionUID = 3096440334995439816L;

  private UUID id;

  private String nome;

  private String descricao;

  @Enumerated(EnumType.STRING)
  private Situacao situacao;

  private LocalDateTime dataCriacao;

  private LocalDateTime dataAtualizacao;

  public boolean isAtiva() {
    return Situacao.ATIVA.equals(situacao);
  }

  public boolean isInativa() {
    return Situacao.INATIVA.equals(situacao);
  }

}
