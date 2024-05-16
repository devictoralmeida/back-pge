package br.gov.ce.pge.gestao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "tb_recuperacao_senha")
public class RequisicaoRecuperarSenha implements Serializable {

    private static final long serialVersionUID = 8983494929625524619L;

    @Id
    @GeneratedValue
    @Column(name = "ci_recuperacao_senha")
    private UUID id;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "dt_hora_requisicao")
    private LocalDateTime horaRequisicao;
}
