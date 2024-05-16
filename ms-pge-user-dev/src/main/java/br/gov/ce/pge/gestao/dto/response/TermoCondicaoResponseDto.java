package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TermoCondicaoResponseDto implements Serializable {

	private static final long serialVersionUID = 2781226911716607854L;
	
	private String id;
	
	private String nomeSistema;
	
	private String versao;
	
	private String conteudo;
	
	private LocalDateTime dataCriacao;
	
	private String nomeUsuario;

}
