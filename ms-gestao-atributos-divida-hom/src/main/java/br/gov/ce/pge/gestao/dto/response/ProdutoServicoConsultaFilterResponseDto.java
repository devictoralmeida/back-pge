package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
public class ProdutoServicoConsultaFilterResponseDto implements Serializable {

	private static final long serialVersionUID = -336003481521694731L;
	
	private String id;

	private String codigo;

	private String descricao;

	private Situacao situacao;

	private List<TipoReceitaConsultaFilterResponseDto> tipoReceitas;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataAtualizacao;

}
