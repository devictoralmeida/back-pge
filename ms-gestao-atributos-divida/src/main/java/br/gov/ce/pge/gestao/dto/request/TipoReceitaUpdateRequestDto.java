package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.dto.validator.DescricaoValid;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
public class TipoReceitaUpdateRequestDto implements Serializable {

	private static final long serialVersionUID = -3615781501139080197L;

	private UUID id;

	@NotBlank(message = "favor informar a descrição")
	@Size(max = 250, message = "a descrição deve conter no máximo {max} caracteres")
	private String descricao;

	@NotNull(message = "favor selecionar uma ou mais origens")
	@NotEmpty(message = "favor selecionar uma ou mais origens")
	private List<UUID> origemDebitos;

	@NotNull(message = "favor informar a natureza")
	private Natureza natureza;

	@NotNull(message = "favor informar a situação")
	private Situacao situacao;

}
