package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.dto.validator.DescricaoValid;
import br.gov.ce.pge.gestao.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OrigemDebitoRequestDto implements Serializable {

	private static final long serialVersionUID = 8572571376082949217L;

	@Schema(name = "id", description = "Origem Débito id", accessMode = Schema.AccessMode.READ_ONLY)
	private UUID id;
	
	@NotBlank(message = "Favor informar o nome da origem do débito")
	@Size(max = 100, message = "o nome da origem deve conter no máximo {max} caracteres")
	@Schema(name = "nome", description = "nome da origem do débito", example = "IPVA")
	private String nome;
	
	@NotBlank(message = "Favor informar a descrição da origem do débito")
	@Size(max = 300, message = "a descrição da origem deve conter no máximo {max} caracteres")
	@Schema(name = "descricao", description = "descrição da origem do débito", example = "descrição IPVA")
	private String descricao;
	
	@NotNull(message = "Favor informar a situação da origem do débito")
	@Schema(name = "situacao", description = "situação da origem do débito", example = "ATIVA")
	private Situacao situacao;

}
