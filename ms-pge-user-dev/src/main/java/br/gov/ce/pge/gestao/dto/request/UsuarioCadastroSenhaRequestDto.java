package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;

import br.gov.ce.pge.gestao.dto.validation.SenhaCadastroValid;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
@SenhaCadastroValid
public class UsuarioCadastroSenhaRequestDto implements Serializable {
	
	private static final long serialVersionUID = 2733233283296244865L;

	@Size(min = 8, max = 32, message = "A senha deve ter no mínimo 8 caracteres e no máximo 32.")
	private String senha;

	private String confirmarSenha;

}
