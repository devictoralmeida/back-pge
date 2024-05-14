package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PermissaoRequestDto implements Serializable{

	private static final long serialVersionUID = -4733532164832265331L;
	
	@NotBlank(message = "favor informar o nome da permissão")
	@Size(max = 150, message = "O campo nome deve conter no máximo {max} caracteres")
	private String nome;
}
