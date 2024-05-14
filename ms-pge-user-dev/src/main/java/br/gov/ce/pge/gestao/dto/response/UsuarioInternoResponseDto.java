package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UsuarioInternoResponseDto implements Serializable {

	private static final long serialVersionUID = -4076719923186563301L;

	private String nome;

	private String area;

}
