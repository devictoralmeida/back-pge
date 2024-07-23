package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DividaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 751062739610320223L;

  private UUID id;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_ID_TIPO_RECEITA)
  private UUID idTipoReceita;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_ID_ORIGEM_DEBITO)
  private UUID idOrigemDebito;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_ID_PRODUTO_SERVICO)
  private UUID idProdutoServico;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DISPOSICOES_LEGAIS)
  @Size(max = 500, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_DISPOSICOES_LEGAIS_INVALIDO)
  private String disposicoesLegais;

  @Size(max = 500, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NATUREZA_FUNDAMENTACAO_INVALIDO)
  private String naturezaFundamentacao;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_INEXISTENCIA_CAUSAS_EXTINTIVAS_SUSPENSIVAS)
  @Size(max = 500, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_INEXISTENCIA_CAUSAS_EXTINTIVAS_SUSPENSIVAS_INVALIDO)
  private String inexistenciaCausaSuspensivas;

  @Size(max = 10, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_DOCUMENTO_INVALIDO)
  private String numeroDocumento;

  @PastOrPresent(message = "A data do documento deve ser anterior ou igual a data atual.")
  private LocalDate dataDocumento;

  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_TERMO_REVELIA_APENAS_NUMEROS)
  @Size(max = 9, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_TERMO_REVELIA_INVALIDO)
  private String termoRevelia;

  @PastOrPresent(message = "A data do termo revelia deve ser anterior ou igual à data atual.")
  private LocalDate dataTermoRevelia;

  @PastOrPresent(message = "A data do trânsito em julgado deve ser anterior ou igual à data atual.")
  private LocalDate dataTransitoJulgado;

  @PastOrPresent(message = "A data da constituição definitiva do crédito deve ser anterior ou igual à data atual.")
  private LocalDate dataConstituicaoDefinitivaCredito;

  @Pattern(regexp = SharedConstant.REGEX_FORMATO_PLACA_VEICULO, message = MessageCommonsConstants.MSG_ERRO_FORMATO_PLACA_VEICULO)
  @Size(min = 7, max = 7, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_PLACA_VEICULO_INVALIDO)
  private String placaVeiculo;

  @Size(max = 25, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CHASSI_INVALIDO)
  private String numeroChassi;

  @Size(min = 5, max = 10, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_GUIA_ITCD_INVALIDO)
  private String guiaItcd;

  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_SEQUENCIAL_PARCELAMENTO_APENAS_NUMEROS)
  @Size(min = 4, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_SEQUENCIAL_PARCELAMENTO_INVALIDO)
  private String sequencialParcelamento;

  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_PROTOCOLO_JUDICIAL_APENAS_NUMEROS)
  @Size(min = 20, max = 20, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_PROTOCOLO_JUDICIAL_INVALIDO)
  private String protocoloJudicial;

  @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_NUMERO_OFICIO_APENAS_NUMEROS)
  @Size(min = 9, max = 9, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_OFICIO_INVALIDO)
  private String numeroOficio;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_NUMERO_PROCESSO_ADMINISTRATIVO)
  @Size(min = 20, max = 20, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_PROCESSO_INVALIDO)
  private String numeroProcessoAdministrativo;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_PROCESSO)
  @PastOrPresent(message = "A data do processo deve ser anterior ou igual à data atual.")
  private LocalDate dataProcesso;

  @Size(max = 30, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_ACORDAO_INVALIDO)
  private String numeroAcordao;

  private String varaOrigem;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_PROCESSO)
  private String tipoProcesso;

  private UUID idTipoDocumento;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_PESSOAS)
  @NotEmpty(message = MessageCommonsConstants.MENSAGEM_INFORMAR_PESSOAS)
  private List<@Valid PessoaRequestDto> pessoas;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DEBITOS)
  private List<@Valid DebitoRequestDto> debitos;

  private Boolean declaracaoAusenciaCorresponsaveis = false;
}
