package br.gov.ce.pge.gestao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "tb_codigo_verificacao")
public class CodigoVerificacao {

    @Id
    @GeneratedValue
    @Column(name = "ci_codigo_verificacao")
    private UUID id;

    @Column(name = "ds_codigo")
    private String codigo;

    @Column(name = "dt_expiracao")
    @JsonIgnore
    private LocalDateTime dataExpiracao;

}
