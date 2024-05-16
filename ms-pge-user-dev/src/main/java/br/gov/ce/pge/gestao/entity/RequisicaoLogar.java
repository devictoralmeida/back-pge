package br.gov.ce.pge.gestao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "tb_requisicao_logar")
public class RequisicaoLogar implements Serializable {

    private static final long serialVersionUID = 1401436287555415236L;

    @Id
    @GeneratedValue
    @Column(name = "ci_requisicao_logar")
    private UUID id;

    @Column(name = "dt_hora_requisicao")
    private LocalDateTime horaRequisicao;

    @Column(name = "fl_sucesso")
    private boolean sucesso;

    @OneToOne
    @JoinColumn(name = "cd_usuario")
    private Usuario usuario;
}
