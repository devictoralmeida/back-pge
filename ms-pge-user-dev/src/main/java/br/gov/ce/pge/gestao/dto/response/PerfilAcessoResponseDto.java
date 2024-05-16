package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PerfilAcessoResponseDto implements Serializable {
	
	private static final long serialVersionUID = -8246161999543959383L;

	private UUID id;

	private String nome;
	
	private Situacao situacao;
	
	private List<Sistema> sistemas;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataAtualizacao;
	
}
