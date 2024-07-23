package br.gov.ce.pge.gestao.shared.auditoria;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditoriaUser implements Serializable {

    private static final long serialVersionUID = 7885505487338928731L;

    @Column(name = "dt_criacao", nullable = false, updatable = false)
    @CreatedDate
    @JsonIgnore
    private LocalDateTime dataCriacao;

    @Column(name = "dt_atualizacao")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @Column(name = "nm_usuario_cadastro")
    private String nomeUsuarioCadastro;

    @JsonIgnore
    @Column(name = "nm_usuario_atualizacao")
    private String nomeUsuarioAtualizacao;
    
    @Transient
    private String nomeUsuario;
    
    @PreUpdate
    public void preUpdate() {
        setNomeUsuarioAtualizacao(getNomeUsuario());
    }

    @PrePersist
    public void prePersist() {
        setNomeUsuarioCadastro(getNomeUsuario());
    }

}