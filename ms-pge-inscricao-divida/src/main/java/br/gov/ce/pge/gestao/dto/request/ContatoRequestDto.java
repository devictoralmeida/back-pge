package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ContatoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 4265570482826489178L;

  private UUID id;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_CONTATO)
  private UUID idTipoContato;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_VALOR_CONTATO)
  private String valorContato;

  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_NUMERO_DDI_CONTATO_APENAS_NUMEROS)
  @Size(max = SharedConstant.TAMANHO_MAXIMO_DDI_CONTATO, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_DDI_CONTATO_INVALIDO)
  private String numeroDdiContato;

  public void verificaContatoNumericoAndDdi() {
    verificaContatoNumerico();
    verificaNumeroDdiContato();
  }

  public void verificaNumeroDdiContato() {
    if (numeroDdiContato == null) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_NUMERO_DDI_CONTATO_OBRIGATORIO);
    }

    if (IdsConstants.ID_TIPO_CONTATO_EMAIL.equals(idTipoContato)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_NUMERO_DDI_CONTATO_ID_TIPO_CONTATO_EMAIL);
    }
  }

  public void verificaContatoNumerico() {
    if (!valorContato.matches(SharedConstant.REGEX_APENAS_NUMEROS)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_NUMERO_CONTATO_APENAS_NUMEROS);
    }

    int tamanhoValorContato = valorContato.length();

    if (tamanhoValorContato < SharedConstant.TAMANHO_MINIMO_CONTATO_NUMERICO || tamanhoValorContato > SharedConstant.TAMANHO_MAXIMO_CONTATO_NUMERICO) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_CONTATO_INVALIDO);
    }
  }


  public void verificaEmail() {
    if (!valorContato.matches(SharedConstant.REGEX_EMAIL)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_EMAIL_INVALIDO);
    }

    if (valorContato.length() > SharedConstant.TAMANHO_MAXIMO_EMAIL) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_TAMANHO_EMAIL_INVALIDO);
    }

    if (numeroDdiContato != null) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_NUMERO_DDI_CONTATO_ID_TIPO_CONTATO_EMAIL);
    }
  }
}
