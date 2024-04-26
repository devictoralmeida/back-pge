package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitoRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Favor informar a Referência Inicial.")
	@Size(min = 7, max = 7, message = "a Referência Inicial deve conter {max} caracteres")
	private String referenciaInicial;

	@NotBlank(message = "Favor informar a Referência Final.")
	@Size(min = 7, max = 7, message = "a Referência Final deve conter {max} caracteres")
	private String referenciaFinal;

	@NotNull(message = "Favor informar a data de vencimento.")
	private LocalDate dataVencimento;

	private LocalDate dataConstituicaoDefinitivaCredito;

	@NotNull(message = "Favor informar o valor principal.")
	private BigDecimal valorPrincipal;

	@NotNull(message = "Favor informar o valor da multa.")
	private BigDecimal valorMulta;

}
