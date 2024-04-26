package br.gov.ce.pge.gestao.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.ce.pge.gestao.enums.SituacaoLivro;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_livro_eletronico")
@Getter @Setter
public class LivroEletronico {
	
	@Id
	@GeneratedValue
	@Column(name = "ci_livro_eletronico")
	private UUID id;
	
	@Column(name = "nm_livro_eletronico")
	private String nome;
	
	@Column(name = "ds_situacao_livro")
	@Enumerated(EnumType.STRING)
	private SituacaoLivro situacao;
	
	@Column(name = "dt_abertura_livro")
	private LocalDateTime dataAbertura;
	
	@Column(name = "dt_fechamento_livro")
	private LocalDateTime dataFechamento;
	
	@Column(name = "nm_usuario_responsavel")
	private String usuarioResponsavel;

}
