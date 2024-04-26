package br.gov.ce.pge.mspgeportalcolaborador.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colaboradores")
public class Colaborador implements Serializable {

	private static final long serialVersionUID = -5653901545760201537L;

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf")
	private String cpf;

	@Transient
	private String area;

	@Column(name = "desvinculado")
	private boolean desvinculado;

	@Column(name = "data_desligamento")
	private LocalDate dataDesligamento;
}
