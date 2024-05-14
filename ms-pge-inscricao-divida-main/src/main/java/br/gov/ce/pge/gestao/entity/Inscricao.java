package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.StatusInscricao;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_inscricao")
@Getter
@Setter
public class Inscricao extends AuditoriaUser {

    private static final long serialVersionUID = -8355166602093189914L;

    @Id
    @GeneratedValue
    @Column(name = "ci_inscricao")
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_devedor", referencedColumnName = "ci_devedor")
    private Devedor devedor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_divida", referencedColumnName = "ci_divida")
    private Divida divida;

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inscricao", orphanRemoval = true)
    private List<Corresponsavel> corresponsaveis;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inscricao", orphanRemoval = true)
    private List<Debito> debitos;

    @Column(name = "ds_status_inscricao")
    @Enumerated(EnumType.STRING)
    private StatusInscricao status;
}
