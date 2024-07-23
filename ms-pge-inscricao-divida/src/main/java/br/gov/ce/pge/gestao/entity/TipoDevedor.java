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
@Table(name = "tb_tipo_devedor")
public class TipoDevedor extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 7382299688625361004L;

  @Id
  @GeneratedValue
  @Column(name = "ci_tipo_devedor")
  private UUID id;

  @Column(name = "nm_tipo_devedor", length = 30, nullable = false)
  private String nome;

}
