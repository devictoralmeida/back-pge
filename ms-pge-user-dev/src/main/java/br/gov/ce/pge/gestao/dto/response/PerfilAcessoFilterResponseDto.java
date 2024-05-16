package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PerfilAcessoFilterResponseDto implements Serializable {
	
	private static final long serialVersionUID = -7299114364185980507L;

	private String id;

	private String nome;
	
	private Situacao situacao;
	
	private LocalDateTime dataCadastro;
	
	private LocalDateTime dataAtualizacao;
	
	private List<SistemaFilterResponseDto> sistemas;

	private String idUsuario;
}
