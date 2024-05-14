package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.gov.ce.pge.gestao.dto.validator.DescricaoValid;
import br.gov.ce.pge.gestao.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
public class ProdutoServicoFilterRequestDto implements Serializable {

	private static final long serialVersionUID = -2710643278658342057L;

	@Schema(name = "id", description = "Produto Serviço id", accessMode = Schema.AccessMode.READ_ONLY)
	private UUID id;

	@Size(min = 5, max = 5,message = "o código deve conter {max} caracteres")
	private String codigo;
	
	private String descricao;

	private Situacao situacao;

	private List<String> idsTipoReceitas;

	public Map<String, Object> filters() {
		Map<String, Object> filter = new HashMap<>();
		filter.put("codigo", codigo == null || codigo.isEmpty() ? null : codigo);
		filter.put("descricao", descricao == null || descricao.isEmpty() ? null : "%" + descricao.toUpperCase() + "%");
		filter.put("idsTipoReceitas", idsTipoReceitas == null || idsTipoReceitas.isEmpty() ? null : idsTipoReceitas);
		filter.put("situacao", situacao == null ? null : situacao);
		return filter;
	}

}
