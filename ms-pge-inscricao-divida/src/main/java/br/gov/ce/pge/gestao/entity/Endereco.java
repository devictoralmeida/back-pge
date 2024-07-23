package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.dto.request.EnderecoRequestDto;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_endereco")
public class Endereco extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 7382299688625361004L;

  @Id
  @GeneratedValue
  @Column(name = "ci_endereco")
  private UUID id;

  @Column(name = "nr_cep", nullable = false)
  private String cep;

  @Column(name = "ds_logradouro", nullable = false)
  private String logradouro;

  @Column(name = "nr_endereco", nullable = false)
  private String numero;

  @Column(name = "ds_bairro", nullable = false)
  private String bairro;

  @Column(name = "ds_complemento")
  private String complemento;

  @Column(name = "ds_distrito")
  private String distrito;

  @Column(name = "ds_municipio", nullable = false)
  private String municipio;

  @Column(name = "ds_uf", nullable = false)
  private String uf;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_pessoa", nullable = false)
  private Pessoa pessoa;

  @Column(name = "fl_principal")
  private Boolean principal = false;

  public Endereco(EnderecoRequestDto request, Pessoa pessoa) {
    cep = request.getCep();
    logradouro = request.getLogradouro();
    numero = request.getNumero();
    bairro = request.getBairro();
    complemento = request.getComplemento();
    distrito = request.getDistrito();
    municipio = request.getMunicipio();
    uf = request.getUf();
    this.pessoa = pessoa;
    principal = request.getPrincipal();
  }
}
