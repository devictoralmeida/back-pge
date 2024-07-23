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
@Table(name = "tb_tipo_pessoa")
public class TipoPessoa extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 7382299688625361004L;

  @Id
  @GeneratedValue
  @Column(name = "ci_tipo_pessoa")
  private UUID id;

  @Column(name = "nm_tipo_pessoa", nullable = false, length = 20)
  private String nome;

}
