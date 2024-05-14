package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PerfilAcessoRequestDto implements Serializable {
	
	private static final long serialVersionUID = -4638227352686319171L;
	
	@NotBlank(message = "Preencher o campo nome")
	@Size(max = 150, message = "O campo nome deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotNull(message = "Preencher a situação")
	private Situacao situacao;

	@NotNull(message = "Preencher o campo sistema")
	@NotEmpty(message = "Preencher o campo sistema")
	private List<UUID> sistemas;
	
	@NotNull(message = "Preencher o campo permissões")
	@NotEmpty(message = "Preencher o campo permissões")
	private List<UUID> permissoes;

}
