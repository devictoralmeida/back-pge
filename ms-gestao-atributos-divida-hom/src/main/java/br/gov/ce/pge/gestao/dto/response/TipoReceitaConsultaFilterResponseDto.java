package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoReceitaConsultaFilterResponseDto implements Serializable {
	
	private static final long serialVersionUID = 535061834602834582L;

	private String id;
	
	private String codigo;
	
	private String descricao;
	
	private String situacao;
	
	private String natureza;
	
	private List<OrigemDebitoConsultaResponseDto> origemDebitos;
	
	private LocalDateTime dataCriacao;
	

}
