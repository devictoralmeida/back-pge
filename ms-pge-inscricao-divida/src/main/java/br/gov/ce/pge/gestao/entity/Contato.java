package br.gov.ce.pge.gestao.entity;

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
@Table(name = "tb_contato")
public class Contato extends AuditoriaUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_contato")
    private UUID id;

    @Column(name = "vl_contato", nullable = false)
    private String valorContato;

    @Column(name = "nr_ddi_contato", length = 4)
    private String numeroDdiContato;

    @OneToOne
    @JoinColumn(name = "cd_tipo_contato", nullable = false)
    private TipoContato tipoContato;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_pessoa", nullable = false)
    private Pessoa pessoa;

}
