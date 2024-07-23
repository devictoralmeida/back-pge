package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_fase_divida")
public class FaseDivida extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 6963717820232205784L;

  @Id
  @GeneratedValue
  @Column(name = "ci_fase_divida")
  private UUID id;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @Column(name = "dt_fase_divida")
  private LocalDateTime dataFase = LocalDateTime.now();

  @Column(name = "nm_anexo")
  private String nomeAnexo;

  @Column(name = "ds_observacao", columnDefinition = "TEXT", nullable = false)
  private String observacao;

  @Column(name = "cd_fase", nullable = false)
  private UUID idFase;

  @Column(name = "fl_fase_anterior")
  private Boolean faseAnterior = false;

  @Column(name = "fl_fase_atual")
  private Boolean faseAtual = false;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "faseDivida")
  private List<AcaoJudicial> acoesJudiciais;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_motivo_atualizacao_fase")
  private MotivoAtualizacaoFase motivoAtualizacaoFase;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "cd_divida", nullable = false)
  private Divida divida;
}
