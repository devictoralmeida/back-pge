package br.gov.ce.pge.gestao.dto.request;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DividaRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;

	@NotNull(message = "Favor informar o Tipo Receita.")
	private UUID idTipoReceita;

	@NotNull(message = "Favor informar a Origem Débito.")
	private UUID idOrigemDebito;

	@NotBlank(message = "Favor informar as Disposições Legais")
	@Size(max = 500, message = "Disposições Legais deve conter no máximo {max} caracteres.")
	private String disposicoesLegais;

	@Size(max = 500, message = "A Natureza e Fundamentação Legal do Débito deve conter no máximo {max} caracteres.")
	private String naturezaFundamentacao;

	@Size(max = 500, message = "A Inexistência de Causas Extintivas ou Suspensivas da Exigibilidade deve conter no máximo {max} caracteres.")
	private String inexistenciaCausaSuspensivas;

	private TipoDocumento tipoDocumento;

	@Size(max = 10, message = "O número do documento deve conter no máximo {max} caracteres.")
	private String numeroDocumento;

	@Column(name = "dt_documento")
	private LocalDate dataDocumento;

	@Size(max = 9, message = "O termo de revelia deve conter no máximo {max} caracteres.")
	private String termoRevelia;

	private LocalDate dataTermoRevelia;

	private LocalDate dataTransitoJulgado;

	private LocalDate dataConstituicaoDefinitivaCredito;

	@Size(min = 7, max = 7, message = "A placa deve conter {max} caracteres")
	private String placaVeiculo;

	@Size(min = 17, max = 17, message = "O número do chassi deve conter {max} caracteres")
	private String numeroChassi;

	private String guiaItcd;

	@Size(min = 4, max = 12, message = "O Sequencial de parcelamento deve conter entre {min} e {max} caracteres")
	private String sequencialParcelamento;

	private String protocoloJudicial;

	@Size(max = 10, message = "O número do ofício deve conter no máximo {max} caracteres.")
	private String numeroOficio;

	@NotNull(message = "Favor informar o tipo do processo.")
	private TipoProcesso tipoProcesso;

	@NotBlank(message = "Favor informar o Número do Processo Administrativo.")
	private String numeroProcessoAdministrativo;

	@NotNull(message = "Favor informar o tipo do processo.")
	private LocalDate dataProcesso;

	private String numeroAcordao;

	private String nomeAnexoProcessoDigitalizado;
	
	private byte[] arquivo;

}
