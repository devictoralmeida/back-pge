package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrigemDebitoConsultaResponseDto implements Serializable {
	
	private static final long serialVersionUID = 535061834602834582L;

	private String id;
	
	private String nome;
	
	private String descricao;
	
	private String situacao;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataAtualizacao;
	

}
