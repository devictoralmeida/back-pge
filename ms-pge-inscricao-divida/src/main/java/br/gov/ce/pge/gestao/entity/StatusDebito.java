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
@Table(name = "tb_status_debito")
public class StatusDebito extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  @Column(name = "ci_status_debito")
  private UUID id;

  @Column(name = "nm_status_debito", length = 20, nullable = false)
  private String nome;

}
