package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.Endereco;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class EnderecoResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 4594588790207150136L;

  private UUID id;

  private String cep;

  private String logradouro;

  private String numero;

  private String bairro;

  private String complemento;

  private String distrito;

  private String municipio;

  private String uf;

  public EnderecoResponseDto(Endereco endereco) {
    this.id = endereco.getId();
    this.cep = endereco.getCep();
    this.logradouro = endereco.getLogradouro();
    this.numero = endereco.getNumero();
    this.bairro = endereco.getBairro();
    this.complemento = endereco.getComplemento();
    this.distrito = endereco.getDistrito();
    this.municipio = endereco.getMunicipio();
    this.uf = endereco.getUf();
  }
}
