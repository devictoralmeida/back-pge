package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitoResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;

	private String referenciaInicial;

	private String referenciaFinal;

	private LocalDate dataVencimento;

	private LocalDate dataConstituicaoDefinitivaCredito;

	private BigDecimal valorPrincipal;

	private BigDecimal valorMulta;
	
	private BigDecimal saldoDevedor;

}
