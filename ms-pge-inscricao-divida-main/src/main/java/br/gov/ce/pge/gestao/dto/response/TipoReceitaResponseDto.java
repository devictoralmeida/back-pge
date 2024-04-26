package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoReceitaResponseDto implements Serializable {

	private static final long serialVersionUID = 7433415198088528728L;

	private UUID id;

	private String codigo;

	private String descricao;

	private List<UUID> origemDebitos;

	private Natureza natureza;

	private Situacao situacao;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataAtualizacao;

}
