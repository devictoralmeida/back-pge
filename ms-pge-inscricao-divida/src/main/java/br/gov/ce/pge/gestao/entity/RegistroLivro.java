package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_registro_livro")
@Getter @Setter
public class RegistroLivro extends AuditoriaUser implements Serializable {
	@Serial
	private static final long serialVersionUID = 1197264897627589436L;

	@Id
	@GeneratedValue
	@Column(name = "ci_registro_livro")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "cd_livro_eletronico")
	private LivroEletronico livro;

	@OneToOne
	@JoinColumn(name = "cd_divida")
	private Divida divida;

	@Column(name = "nr_pagina")
	private Integer pagina;

	@Column(name = "nr_linha")
	private Integer linha;

}
