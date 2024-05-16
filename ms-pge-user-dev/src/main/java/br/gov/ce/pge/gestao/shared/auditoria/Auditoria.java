package br.gov.ce.pge.gestao.shared.auditoria;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_auditoria")
@RevisionEntity(CustomRevisionListener.class)
@Getter @Setter
@ToString(callSuper = true)
public class Auditoria {

    @Id
    @RevisionNumber
    @GeneratedValue
    @Column(name = "ci_auditoria", nullable = false)
    private Long id;

    @RevisionTimestamp
    @Column(name = "nr_timestamp")
    @JsonIgnore
    private long timestamp;

    @Column(name = "dt_movimento")
    @JsonIgnore
    private LocalDateTime dataMovimento;

    @Column(name = "nm_usuario")
    private String nomeUsuario;

    @Column(name = "ds_dados_antigos", length = 2048)
    private String dadosAntigos;

}
