package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import br.gov.ce.pge.gestao.enums.StatusInscricao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InscricaoRequestDto implements Serializable {

	private static final long serialVersionUID = 3669334057923643310L;
	
	private UUID id;

	private @Valid DevedorRequestDto devedor;
	
	private @Valid DividaRequestDto divida;
	
	private @Valid List<CorresponsavelRequestDto> corresponsaveis;
	
	private @Valid List<DebitoRequestDto> debitos;
	
	private StatusInscricao status;

}
