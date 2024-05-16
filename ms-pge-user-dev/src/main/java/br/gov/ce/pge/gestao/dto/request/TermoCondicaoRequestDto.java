package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class TermoCondicaoRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(max = 15000, message = "O conteúdo do termo deve conter no máximo {max} caracteres!")
	@NotBlank(message = "Favor informar o conteúdo!")
	private String conteudo;

}
