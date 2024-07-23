package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_divida_pessoa")
public class DividaPessoa extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 7382299688625361004L;

  @Id
  @GeneratedValue
  @Column(name = "ci_divida_pessoa")
  private UUID id;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @Column(name = "dt_declaracao_ausencia_contato")
  private LocalDateTime dataDeclaracaoAusenciaContato;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_pessoa", nullable = false)
  private Pessoa pessoa;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cd_papel_pessoa_divida", nullable = false)
  @JsonManagedReference
  private PapelPessoaDivida papelPessoaDivida;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "cd_divida", nullable = false)
  private Divida divida;

  @Transient
  public Boolean isPapelSucessorDivida() {
	  return IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_SUCESSOR.equals(getPapelPessoaDivida().getTipoPapelPessoaDivida().getId());
  }

}
