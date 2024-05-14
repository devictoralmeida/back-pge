package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DividaResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID id;

	private UUID idTipoReceita;

	private UUID idOrigemDebito;

	private String disposicoesLegais;

	private String naturezaFundamentacao;

	private String inexistenciaCausaSuspensivas;

	private TipoDocumento tipoDocumento;

	private String numeroDocumento;

	private LocalDate dataDocumento;

	private String termoRevelia;

	private LocalDate dataTermoRevelia;

	private LocalDate dataTransitoJulgado;

	private LocalDate dataConstituicaoDefinitivaCredito;

	private String placaVeiculo;

	private String numeroChassi;

	private String guiaItcd;

	private String sequencialParcelamento;

	private String protocoloJudicial;

	private String numeroOficio;

	private TipoProcesso tipoProcesso;

	private String numeroProcessoAdministrativo;

	private LocalDate dataProcesso;

	private String numeroAcordao;

	private String nomeAnexoProcessoDigitalizado;
	
	private String arquivo;

}
