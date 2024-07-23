package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.dto.request.DividaUpdateRequestDto;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_motivo_atualizacao_divida")
public class MotivoAtualizacaoDivida extends AuditoriaUser implements Serializable {
  @Serial
  private static final long serialVersionUID = 399986031500488868L;

  @Id
  @GeneratedValue
  @Column(name = "ci_motivo_atualizacao_divida")
  private UUID id;

  @Column(name = "ds_motivo_atualizacao_divida", columnDefinition = "TEXT", nullable = false)
  private String motivo;

  @Column(name = "nm_anexo")
  private String nomeAnexo;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "cd_divida", nullable = false)
  private Divida divida;

  public MotivoAtualizacaoDivida() {
  }


  public MotivoAtualizacaoDivida(Divida divida, DividaUpdateRequestDto dividaUpdateRequestDto) {
    this.divida = divida;
    nomeAnexo = dividaUpdateRequestDto.getNomeAnexo();
    motivo = dividaUpdateRequestDto.getMotivo();
  }
}
