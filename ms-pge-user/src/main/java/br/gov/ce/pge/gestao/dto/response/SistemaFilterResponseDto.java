package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SistemaFilterResponseDto implements Serializable {
	
	private static final long serialVersionUID = -1326728103125421200L;

	private String id;
	
	private String nome;
	
	private String idPerfil;

	private String idUsuario;

}
