package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.ce.pge.gestao.enums.TipoUsuario;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import lombok.Data;

@Data
public class UsuarioFilterRequestDto implements Serializable {

	private static final long serialVersionUID = 1458187039743284099L;

	private TipoUsuario tipoUsuario;
	
	private List<String> sistemas;
	
	private List<String> perfisAcessos;
	
	@Size(max = 11, min =11, message = "O campo nome deve conter {max} caracteres")
	private String cpf;
	
	@Size(max = 250, message = "O campo nome deve conter no máximo {max} caracteres")
	private String nome;
	
	private String orgao;
	
	private String area;
	
	@Size(max = 250, message = "O campo e-mail deve conter no máximo {max} caracteres")
	private String email;
	
	private SituacaoUsuario situacao;

	@JsonIgnore
	private long offset;

	@JsonIgnore
	private long limit;

	@JsonIgnore
	private String orderBy;

	public Map<String, Object> filters() {
		Map<String, Object> filter = new HashMap<>();
		filter.put("tipoUsuario", tipoUsuario == null ? null : tipoUsuario);
		filter.put("sistemas", sistemas == null || sistemas.isEmpty() ? null : sistemas);
		filter.put("perfisAcessos", perfisAcessos == null || perfisAcessos.isEmpty() ? null : perfisAcessos);
		filter.put("cpf", cpf == null || cpf.isEmpty() ? null : cpf);
		filter.put("nome", nome == null || nome.isEmpty() ? null : "%" + nome.toUpperCase() + "%");
		filter.put("email", email == null || email.isEmpty() ? null : "%" + email.toUpperCase() + "%");
		filter.put("situacao", situacao == null ? null : situacao);
		filter.put("orgao", orgao == null ? null : orgao);
		filter.put("area", area == null ? null : area);
		filter.put("offset", offset);
		filter.put("limit", limit);
		filter.put("orderBy", orderBy == null ? "u.nm_usuario" : getOrdenacao(orderBy));
		return filter;
	}

	private String getOrdenacao(String orderBy) {

		switch (orderBy) {
			case "nome":
				return "u.nm_usuario";
			case "nome-desc":
				return "u.nm_usuario desc";
			case "orgao":
				return "u.ds_orgao_usuario";
			case "orgao-desc":
				return "u.ds_orgao_usuario desc";
			case "situacao":
				return "u.ds_situacao_usuario";
			case "situacao-desc":
				return "u.ds_situacao_usuario desc";
			case "sistema":
				return null;
			case "sistema-desc":
				return null;
			case "ultimo_acesso":
				return "u.dt_ultimo_acesso_usuario";
			case "ultimo_acesso-desc":
				return "u.dt_ultimo_acesso_usuario desc";
			case "tipoUsuario":
				return "u.tp_usuario";
			case "tipoUsuario-desc":
				return "u.tp_usuario desc";
			default:
				throw new NegocioException("Não é possível ordenar por: " + orderBy);
		}
	}

}
