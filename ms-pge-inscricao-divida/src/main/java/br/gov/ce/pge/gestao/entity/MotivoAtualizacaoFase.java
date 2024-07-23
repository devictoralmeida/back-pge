package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_motivo_atualizacao_fase")
public class MotivoAtualizacaoFase extends AuditoriaUser implements Serializable {

    private static final long serialVersionUID = -6093990508714376712L;

    @Id
    @GeneratedValue
    @Column(name = "ci_motivo_atualizacao_fase")
    private UUID id;

    @Column(name = "nm_motivo_atualizacao_fase", length = 30, nullable = false)
    private String nome;
}
