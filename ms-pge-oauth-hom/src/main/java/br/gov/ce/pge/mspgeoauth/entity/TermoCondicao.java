package br.gov.ce.pge.mspgeoauth.entity;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TermoCondicao {
	

	private UUID id;
	
	private Sistema sistema;
	
	private String versao;
	
	private String conteudo;
	
    private List<Usuario> usuarios;

}
