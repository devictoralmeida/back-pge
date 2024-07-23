package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class DividaPessoaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -4897207563781259977L;

  private Boolean declaracaoAusenciaContatos = false;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_PAPEL_PESSOA_DIVIDA)
  private UUID idPapelPessoa;

  private UUID idQualificacaoCorresponsavel;

  private UUID idTipoDevedor;

}
