package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModuloDto implements Serializable {
	
	private static final long serialVersionUID = -7428397446934874930L;

	private String id;
	
	private String nome;
	
	private List<PermissaoDto> permissoes;
	
}
