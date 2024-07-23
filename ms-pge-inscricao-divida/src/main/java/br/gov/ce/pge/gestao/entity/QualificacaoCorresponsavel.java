package br.gov.ce.pge.gestao.entity;


import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_qualificacao_corresponsavel")
public class QualificacaoCorresponsavel extends AuditoriaUser {
  @Serial
  private static final long serialVersionUID = 7382299688625361004L;

  @Id
  @GeneratedValue
  @Column(name = "ci_qualificacao_corresponsavel")
  private UUID id;

  @Column(name = "nm_qualificacao_corresponsavel", length = 30, nullable = false)
  private String nome;

}
