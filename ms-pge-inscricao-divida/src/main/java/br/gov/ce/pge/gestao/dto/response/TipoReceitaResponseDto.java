package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoReceitaResponseDto implements Serializable {

  private static final long serialVersionUID = 7433415198088528728L;

  private UUID id;

  private String codigo;

  private String descricao;

  private List<UUID> origemDebitos;

  private Natureza natureza;

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
