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
@Table(name = "tb_papel_pessoa_divida")
public class PapelPessoaDivida extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 7382299688625361004L;

  @Id
  @GeneratedValue
  @Column(name = "ci_papel_pessoa_divida")
  private UUID id;

  @OneToOne
  @JoinColumn(name = "cd_tipo_papel_pessoa_divida")
  private TipoPapelPessoaDivida tipoPapelPessoaDivida;

  @OneToOne
  @JoinColumn(name = "cd_qualificacao_corresponsavel")
  private QualificacaoCorresponsavel qualificacaoCorresponsavel;

  @OneToOne
  @JoinColumn(name = "cd_tipo_devedor")
  private TipoDevedor tipoDevedor;

}
