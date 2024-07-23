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
@Table(name = "tb_tipo_acao_judicial")
public class TipoAcaoJudicial extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = -8495877846903877811L;

  @Id
  @GeneratedValue
  @Column(name = "ci_tipo_acao_judicial")
  private UUID id;

  @Column(name = "tp_acao_judicial", nullable = false)
  private String tipo;
}
