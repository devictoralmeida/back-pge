package br.gov.ce.pge.mspgeoauth.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
public class LoginDto implements Serializable {

	private static final long serialVersionUID = 8278042910974585987L;

	@Size(max = 250, message = "O campo login deve ter no máximo 250 caracteres.")
	private String login;

	@Size(min = 8, max = 32, message = "A senha deve ter no mínimo 8 caracteres e no máximo 32.")
	private String senha;

	private Boolean novoDispositivo;
}
