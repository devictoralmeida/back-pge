package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissaoDto implements Serializable {
	
	private static final long serialVersionUID = 5517457619178251239L;

	private String id;
	
	private String nome;

	private String codigoIdentificador;
}
