package br.gov.ce.pge.gestao.entity;


import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_tipo_papel_pessoa_divida")
public class TipoPapelPessoaDivida extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = -4260525844072511931L;

  @Id
  @GeneratedValue
  @Column(name = "ci_tipo_papel_pessoa_divida")
  private UUID id;

  @Column(name = "tp_papel_pessoa_divida", length = 20, nullable = false)
  private String tipoPapel;


}
