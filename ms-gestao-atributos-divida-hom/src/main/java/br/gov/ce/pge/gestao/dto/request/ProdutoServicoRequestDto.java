package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.dto.validator.DescricaoValid;
import br.gov.ce.pge.gestao.dto.validator.ProdutoServicoValid;
import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
@ProdutoServicoValid
public class ProdutoServicoRequestDto implements Serializable {

	private static final long serialVersionUID = -3850310755909203982L;

	private UUID id;

	@NotBlank(message = "favor informar o código")
	@Size(min = 5, max = 5,message = "o código deve conter {max} caracteres")
	private String codigo;

	@NotBlank(message = "favor informar a descrição")
	@Size(max = 250, message = "a descrição deve conter no máximo {max} caracteres")
	private String descricao;

	@NotNull(message = "favor informar o tipo receita")
	private List<UUID> idsTipoReceitas;

	@NotNull(message = "favor informar a situação")
	private Situacao situacao;
}
