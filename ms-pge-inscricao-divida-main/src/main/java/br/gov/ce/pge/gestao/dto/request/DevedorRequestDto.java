package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class DevedorRequestDto implements Serializable {

  private static final long serialVersionUID = -5176385379374791879L;

  private UUID id;

  @NotNull(message = "Favor informar o Tipo Pessoa.")
  private TipoPessoa tipoPessoa;

  @Size(min = 11, max = 14, message = "O documento deve conter {min} ou {max} caracteres.")
  @NotBlank(message = "Favor informar o Documento.")
  private String documento;

  @NotBlank(message = "Favor informar o nome ou razão social.")
  private String nomeRazaoSocial;

  private String cgf;

  private TipoContato tipoContato;

  private String contato;

  private String email;

  @NotBlank(message = "Favor informar o Cep.")
  private String cep;

  @NotBlank(message = "Favor informar o Logradouro.")
  private String logradouro;

  @NotBlank(message = "Favor informar o Número.")
  private String numero;

  @NotBlank(message = "Favor informar o Bairro.")
  private String bairro;

  private String complemento;

  private String distrito;

  @NotBlank(message = "Favor informar o Municípo.")
  private String municipio;

  @NotBlank(message = "Favor informar o UF.")
  private String uf;

}
