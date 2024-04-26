package br.gov.ce.pge.gestao.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.ce.pge.gestao.enums.TipoContato;
import br.gov.ce.pge.gestao.enums.TipoPessoa;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_devedor")
public class Devedor extends AuditoriaUser {

	private static final long serialVersionUID = -6175237103600932903L;

	@Id
	@GeneratedValue
	@Column(name = "ci_devedor")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(name = "ds_tipo_pessoa_devedor", nullable = false)
	private TipoPessoa tipoPessoa;

	@Column(name = "nr_documento_devedor", length = 14, nullable = false)
	private String documento;

	@Column(name = "nm_devedor", nullable = false)
	private String nomeRazaoSocial;

	@Column(name = "nr_cgf_devedor")
	private String cgf;

	@Enumerated(EnumType.STRING)
	@Column(name = "ds_tipo_contato_devedor")
	private TipoContato tipoContato;

	@Column(name = "nr_contato_devedor")
	private String contato;

	@Column(name = "ds_email_devedor")
	private String email;

	@Column(name = "nr_cep_devedor", nullable = false)
	private String cep;

	@Column(name = "ds_logradouro_devedor", nullable = false)
	private String logradouro;

	@Column(name = "nr_endereco_devedor", nullable = false)
	private String numero;

	@Column(name = "ds_bairro_devedor", nullable = false)
	private String bairro;

	@Column(name = "ds_complemento_devedor")
	private String complemento;

	@Column(name = "ds_distrito_devedor")
	private String distrito;

	@Column(name = "ds_municipio_devedor", nullable = false)
	private String municipio;

	@Column(name = "ds_uf_devedor", nullable = false)
	private String uf;

}
