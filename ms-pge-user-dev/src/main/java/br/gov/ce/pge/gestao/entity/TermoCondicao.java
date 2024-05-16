package br.gov.ce.pge.gestao.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_termo_condicao")
@Getter @Setter
public class TermoCondicao extends AuditoriaUser {
	
	private static final long serialVersionUID = 8698680117592075649L;

	@Id
	@GeneratedValue
	@Column(name = "ci_termo_condicao")
	private UUID id;
	
	@OneToOne
	@JoinColumn(name = "cd_sistema")
	private Sistema sistema;
	
	@Column(name = "ds_versao")
	private String versao;
	
	@Column(name = "ds_conteudo")
	private String conteudo;
	
	@ManyToMany(mappedBy = "termos")
    private List<Usuario> usuarios;

}
