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
public class EnderecoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 2948749703527945746L;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_CEP)
  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS_POSITIVOS, message = MessageCommonsConstants.MSG_ERRO_CEP_APENAS_NUMEROS)
  @Size(min = 8, max = 8, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CEP_INVALIDO)
  private String cep;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_LOGRADOURO)
  @Size(max = 300, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_LOGRADOURO_INVALIDO)
  private String logradouro;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_NUMERO)
  @Size(max = 6, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_INVALIDO)
  private String numero;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_BAIRRO)
  @Size(max = 250, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_BAIRRO_INVALIDO)
  private String bairro;

  @Size(max = 300, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_COMPLEMENTO_INVALIDO)
  private String complemento;

  @Size(max = 250, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_DISTRITO_INVALIDO)
  private String distrito;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_MUNICIPIO)
  private String municipio;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_UF)
  @Size(min = 2, max = 2, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_UF_INVALIDO)
  private String uf;

  private Boolean principal = true;

}
