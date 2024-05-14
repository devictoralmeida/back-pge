package br.gov.ce.pge.mspgeoauth.entity;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.mspgeoauth.enums.Situacao;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PerfilAcesso {
	
	private UUID id;
	
	private String nome;
	
	private Situacao situacao;
	
	private List<Sistema> sistemas;
	
	private List<Permissao> permissoes;


}
