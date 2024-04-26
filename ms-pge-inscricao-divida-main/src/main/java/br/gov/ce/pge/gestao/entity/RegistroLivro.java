package br.gov.ce.pge.gestao.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_registro_livro")
@Getter @Setter
public class RegistroLivro extends AuditoriaUser {
	
	private static final long serialVersionUID = 1197264897627589436L;

	@Id
	@GeneratedValue
	@Column(name = "ci_registro_livro")
	private UUID id;
	
	@ManyToOne()
	@JoinColumn(name = "cd_livro_eletronico")
	private LivroEletronico livro;
	
	@OneToOne
	@JoinColumn(name = "cd_inscricao")
	private Inscricao inscricao;
	
}
