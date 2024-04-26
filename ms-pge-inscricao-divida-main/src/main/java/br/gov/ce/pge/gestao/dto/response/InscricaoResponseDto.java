package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.StatusInscricao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InscricaoResponseDto implements Serializable {

	private static final long serialVersionUID = -1227286084183445319L;
	
	private UUID id;

	private DevedorResponseDto devedor;
	
	private DividaResponseDto divida;
	
	private List<CorresponsavelResponseDto> corresponsaveis;
	
	private List<DebitoResponseDto> debitos;
	
	private StatusInscricao status;

}
