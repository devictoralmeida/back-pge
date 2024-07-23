package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_acao_judicial")
public class AcaoJudicial extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 747056978302337549L;

  @Id
  @GeneratedValue
  @Column(name = "ci_acao_judicial")
  private UUID id;

  @Column(name = "nr_ordem_judicial", nullable = false)
  private String numeroOrdemJudicial;

  @Column(name = "nm_juizo", nullable = false)
  private String juizo;

  @Column(name = "nm_reu", nullable = false)
  private String reu;

  @Column(name = "nm_autor", nullable = false)
  private String autor;

  @Column(name = "nm_anexo", nullable = false)
  private String nomeAnexo;

  @Column(name = "ds_observacao", columnDefinition = "TEXT")
  private String observacao;

  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_divida", nullable = false)
  private Divida divida;

  @ManyToOne
  @JoinColumn(name = "cd_fase_divida", nullable = false)
  private FaseDivida faseDivida;

  @Column(name = "dt_acao_judicial")
  private LocalDate dataAcaoJudicial;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_tipo_acao_judicial", nullable = false)
  private TipoAcaoJudicial tipoAcaoJudicial;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_providencia_acao_judicial", nullable = false)
  private ProvidenciaAcaoJudicial providenciaAcaoJudicial;
}
