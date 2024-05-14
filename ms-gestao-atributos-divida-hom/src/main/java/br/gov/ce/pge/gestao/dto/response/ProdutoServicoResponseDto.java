package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
public class ProdutoServicoResponseDto implements Serializable {
	
	private static final long serialVersionUID = -236441868142528506L;

	private UUID id;

	private String codigo;

	private String descricao;

	private List<UUID> idsTipoReceitas;

	private Situacao situacao;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataAtualizacao;

}
