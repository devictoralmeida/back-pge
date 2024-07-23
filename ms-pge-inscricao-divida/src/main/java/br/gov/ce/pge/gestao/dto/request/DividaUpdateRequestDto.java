package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class DividaUpdateRequestDto extends DividaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -3807601790556119551L;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_MOTIVO_ALTERACAO_INSCRICAO)
  @Size(min = 200, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_MINIMO_MOTIVO_ALTERACAO_INSCRICAO)
  private String motivo;

  private UUID idAcaoJudicial;

  private String nomeAnexo;
}
