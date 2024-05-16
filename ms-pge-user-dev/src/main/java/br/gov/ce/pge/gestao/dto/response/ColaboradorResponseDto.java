package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ColaboradorResponseDto implements Serializable {

	private static final long serialVersionUID = -8496157631570354604L;

	private String nome;

	private String cpf;

	private String area;

	private Boolean desvinculado;

	private LocalDateTime dataDesligamento;

}
