package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class DocumentoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 2517660021032605376L;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DOCUMENTO)
  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_DOCUMENTO_APENAS_NUMEROS)
  @Size(min = 11, max = 14, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_DOCUMENTO_INVALIDO)
  private String documento;

  public DocumentoRequestDto(String documento) {
    this.documento = documento;
  }

  public DocumentoRequestDto() {
  }
}
