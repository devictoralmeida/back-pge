package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.dto.validator.DescricaoValid;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TipoReceitaFilterRequestDto implements Serializable {

	private static final long serialVersionUID = 5274266605631991600L;

	private Natureza natureza;

	@Schema(name = "id", description = "Tipo Receita id", accessMode = Schema.AccessMode.READ_ONLY)
	private UUID id;

	@Size(min = 4, max = 4,message = "o código deve conter {max} caracteres")
	private String codigo;
	
	@Size(max = 250, message = "a descrição só pode ter no maximo {max} caracteres")
	private String descricao;
	
	private Situacao situacao;
	
	private List<String> origemDebitos;
	
	public Map<String, Object> filters(){
	    Map<String, Object> filter = new HashMap<>();
	    filter.put("natureza", natureza == null ? null : natureza);
	    filter.put("codigo", codigo == null || codigo.equals("") ? null : codigo);
	    filter.put("descricao", descricao == null || descricao.equals("") ? null : "%" + descricao.toUpperCase() + "%" );
	    filter.put("idsOrigem", origemDebitos == null || origemDebitos.isEmpty() ? null : origemDebitos);
	    filter.put("situacao", situacao == null? null : situacao );
	    return filter;
	}

}
