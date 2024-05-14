package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.dto.validation.UsuarioValid;
import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@UsuarioValid
public class UsuarioRequestDto  implements Serializable {
	
	private static final long serialVersionUID = 3975859704993609447L;
	
	@NotNull(message = "informar o tipo do usuário")
	private TipoUsuario tipoUsuario;

	@CPF(message = "CPF inválido")
	@NotBlank(message = "informar o cpf do usuário")
	@Size(min = 11, max = 11, message = "o cpf deve conter 11 caracteres")
	private String cpf;

	@NotBlank(message = "informar o nome do usuário")
	@Size(max = 250, message = "o nome do usuário deve conter no máximo {max} caracteres")
	private String nome;
	
	@NotBlank(message = "informar o orgão do usuário")
	private String orgao;
	
	@Size(max = 250, message = "a área do usuário deve conter no máximo {max} caracteres")
	private String area;
	
	@NotBlank(message = "informar o email do usuário")
	@Size(max = 250, message = "O email deve conter no máximo {max} caracteres")
	private String email;
	
	@Size(max = 250, message = "O usuário de rede deve conter no máximo {max} caracteres")
	private String usuarioRede;
	
	@NotNull(message = "informar o(s) sistema(s)")
	@NotEmpty(message = "informar o(s) sistema(s)")
	private List<UUID> sistemas;
	
	@NotNull(message = "informar o(s) perfil(is) de acesso(s)")
	@NotEmpty(message = "informar o(s) perfil(is) de acesso(s)")
	private List<UUID> perfisAcessos;
	
	@NotNull(message = "informar a situação do usuário")
	private SituacaoUsuario situacao;
	
}
