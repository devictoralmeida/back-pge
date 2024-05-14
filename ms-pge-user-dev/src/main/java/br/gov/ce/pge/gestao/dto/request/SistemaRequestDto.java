package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SistemaRequestDto implements Serializable {
	
	private static final long serialVersionUID = -7824006417715313624L;
	
	@NotBlank(message = "favor informar o nome do sistema")
	@Size(max = 150, message = "O campo nome deve conter no máximo {max} caracteres")
	private String nome;
	
	private List<UUID> modulos;

}
