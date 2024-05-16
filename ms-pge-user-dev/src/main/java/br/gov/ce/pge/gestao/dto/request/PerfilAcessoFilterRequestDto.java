package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import lombok.Data;

@Data
public class PerfilAcessoFilterRequestDto implements Serializable {

	private static final long serialVersionUID = -845745522790188956L;

	@Size(max = 150, message = "O campo nome deve conter no máximo {max} caracteres")
	private String nome;

	private Situacao situacao;

	private List<String> sistemas;
	
	@JsonIgnore
	private long offset;
	
	@JsonIgnore
	private long limit;
	
	@JsonIgnore
	private String orderBy;

	@JsonIgnore
	public Map<String, Object> filters() {
		Map<String, Object> filter = new HashMap<>();
		filter.put("nome", nome == null || nome.isEmpty() ? null : "%" + nome.toUpperCase() + "%");
		filter.put("situacao", situacao == null ? null : situacao);
		filter.put("sistemas", sistemas == null || sistemas.isEmpty() ? null : sistemas);
		filter.put("offset", offset);
		filter.put("limit", limit);
		filter.put("orderBy", orderBy == null ? "pa.nm_perfil_acesso" : getOrdenacao(orderBy));
		return filter;
	}

	private String getOrdenacao(String orderBy) {
		
		switch (orderBy) {
		case "nome": 
			return "pa.nm_perfil_acesso";
		case "nome-desc": 
			return "pa.nm_perfil_acesso desc";
		case "situacao": 
			return "pa.ds_situacao_perfil_acesso";
		case "situacao-desc": 
			return "pa.ds_situacao_perfil_acesso desc";
		case "sistema": 
			return null;
		case "sistema-desc": 
			return null;
		case "cadastrado_em": 
			return "pa.dt_criacao";
		case "cadastrado_em-desc": 
			return "pa.dt_criacao desc";
		default:
			throw new NegocioException("Não é possível ordenar por: " + orderBy);
		}
	}

}
