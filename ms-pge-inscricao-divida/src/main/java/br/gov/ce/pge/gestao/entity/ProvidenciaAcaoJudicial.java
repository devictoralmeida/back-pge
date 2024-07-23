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
@Table(name = "tb_providencia_acao_judicial")
public class ProvidenciaAcaoJudicial extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = -3330741284082581812L;

  @Id
  @GeneratedValue
  @Column(name = "ci_providencia_acao_judicial")
  private UUID id;

  @Column(name = "nm_providencia_acao_judicial", nullable = false)
  private String nome;
}
