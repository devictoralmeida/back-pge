package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ModuloRequestDto implements Serializable {
	
	private static final long serialVersionUID = 2846229222226828635L;
	
	@NotBlank(message = "favor informar o nome do módulo")
	@Size(max = 150, message = "O campo nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotNull(message = "favor informar as permissões do módulo")
	@NotEmpty(message = "favor informar as permissões do módulo")
	private List<UUID> permissoes;

}
