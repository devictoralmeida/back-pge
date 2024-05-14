package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_corresponsavel")
public class Corresponsavel extends AuditoriaUser {

    private static final long serialVersionUID = 7382299688625361004L;

    @Id
    @GeneratedValue
    @Column(name = "ci_corresponsavel")
    private UUID id;

    @Column(name = "ds_tipo_pessoa_corresponsavel", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Column(name = "nr_documento_corresponsavel", length = 14, nullable = false)
    private String documento;

    @Column(name = "nm_corresponsavel", nullable = false, length = 250)
    private String nomeRazaoSocial;

    @Column(name = "nr_cgf_corresponsavel")
    private String cgf;

    @Column(name = "ds_tipo_contato_corresponsavel")
    @Enumerated(EnumType.STRING)
    private TipoContato tipoContato;

    @Column(name = "nr_contato_corresponsavel")
    private String contato;

    @Column(name = "ds_email_corresponsavel")
    private String email;

    @Column(name = "ds_qualificacao_corresponsavel")
    private String qualificacao;

    @Column(name = "nr_cep_corresponsavel", nullable = false)
    private String cep;

    @Column(name = "ds_logradouro_corresponsavel", nullable = false)
    private String logradouro;

    @Column(name = "nr_endereco_corresponsavel", nullable = false)
    private String numero;

    @Column(name = "ds_bairro_corresponsavel", nullable = false)
    private String bairro;

    @Column(name = "ds_complemento_corresponsavel")
    private String complemento;

    @Column(name = "ds_distrito_corresponsavel")
    private String distrito;

    @Column(name = "ds_municipio_corresponsavel", nullable = false)
    private String municipio;

    @Column(name = "ds_uf_corresponsavel", nullable = false)
    private String uf;

    @ManyToOne
    @JoinColumn(name = "cd_inscricao")
    private Inscricao inscricao;

}
