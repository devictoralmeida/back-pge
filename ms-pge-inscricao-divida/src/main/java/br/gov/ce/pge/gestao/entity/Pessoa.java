package br.gov.ce.pge.gestao.entity;


import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_pessoa")
public class Pessoa extends AuditoriaUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 7382299688625361004L;

    @Id
    @GeneratedValue
    @Column(name = "ci_pessoa")
    private UUID id;

    @Column(name = "nr_documento_pessoa", length = 14, nullable = false)
    private String documento;

    @Column(name = "nm_pessoa", nullable = false, length = 250)
    private String nomeRazaoSocial;

    @Column(name = "nr_cgf_pessoa", length = 14)
    private String cgf;

    @OneToOne
    @JoinColumn(name = "cd_tipo_pessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<DividaPessoa> dividaPessoas = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Contato> contatos = new ArrayList<>();

}
