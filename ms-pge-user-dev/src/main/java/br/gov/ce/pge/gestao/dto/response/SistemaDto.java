package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SistemaDto implements Serializable {
	
	private static final long serialVersionUID = -5393103489571519932L;

	private String id;
	
	private String nome;
	
	private List<ModuloDto> modulos;
}
