package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
public class OrigemDebitoFilterRequestDto implements Serializable {

	private static final long serialVersionUID = 5274266605631991600L;

	private String nome;
	
	private String descricao;
	
	private Situacao situacao;
	
	public Map<String, Object> filters(){
	    Map<String, Object> filter = new HashMap<>();
	    filter.put("nome", nome == null || nome.isEmpty() ? null : "%" + nome.toUpperCase() + "%" );
	    filter.put("descricao", descricao == null || descricao.isEmpty() ? null : "%" + descricao.toUpperCase() + "%" );
	    filter.put("situacao", situacao == null ? null : situacao);
	    return filter;
	}

}
