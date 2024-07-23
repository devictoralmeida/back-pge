package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
public class DebitoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_INICIO_ATUALIZACAO_MONETARIA)
  @PastOrPresent(message = "A Data de Início da Atualização Monetária deve ser anterior ou igual à data atual.")
  private LocalDate dataInicioAtualizacaoMonetaria;

  @NotBlank(message = MessageCommonsConstants.MENSAGEM_INFORMAR_REFERENCIA_INICIAL)
  @Pattern(regexp = SharedConstant.REGEX_FORMATO_DATA_MM_YYYY, message = MessageCommonsConstants.MSG_ERRO_FORMATO_REFERENCIA_INVALIDO)
  @Size(min = 7, max = 7, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_REFERENCIA_INVALIDO)
  private String referenciaInicial;

  @Pattern(regexp = SharedConstant.REGEX_FORMATO_DATA_MM_YYYY, message = MessageCommonsConstants.MSG_ERRO_FORMATO_REFERENCIA_INVALIDO)
  @Size(min = 7, max = 7, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_REFERENCIA_INVALIDO)
  private String referenciaFinal;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_VENCIMENTO)
  @PastOrPresent(message = "A data do vencimento deve ser anterior ou igual a data atual.")
  private LocalDate dataVencimento;

  private LocalDate dataConstituicaoDefinitivaCredito;

  @Positive(message = MessageCommonsConstants.MSG_ERRO_VALOR_PRINCIPAL_MULTA_DEBITO_POSITIVO)
  private BigDecimal valorPrincipal;

  @Positive(message = MessageCommonsConstants.MSG_ERRO_VALOR_PRINCIPAL_MULTA_DEBITO_POSITIVO)
  private BigDecimal valorMulta;

  @NotNull(message = MessageCommonsConstants.MENSAGEM_INFORMAR_STATUS_DEBITO)
  private UUID idStatusDebito;

  public void validarReferencias() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate dataAtual = LocalDate.now();
    LocalDate dataInicial = LocalDate.parse(SharedConstant.REFERENCIA_PRIMEIRO_DIA_MES + "/" + referenciaInicial, formatter);

    if (dataInicial.isAfter(dataAtual)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_REFERENCIA_INICIAL_SUPERIOR_DATA_ATUAL);
    }

    if (referenciaFinal != null) {
      validarReferenciaFinal(formatter, dataAtual, dataInicial);
    }
  }

  public DebitoRequestDto() {
  }

  private void validarReferenciaFinal(DateTimeFormatter formatter, LocalDate dataAtual, LocalDate dataInicial) {
    LocalDate dataFinal = LocalDate.parse(SharedConstant.REFERENCIA_PRIMEIRO_DIA_MES + "/" + referenciaFinal, formatter);

    if (dataFinal.isAfter(dataAtual)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_REFERENCIA_FINAL_SUPERIOR_DATA_ATUAL);
    }

    if (dataFinal.isBefore(dataInicial)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_REFERENCIA_FINAL_ANTERIOR_INICIAL);
    }
  }


  public void validarValores() {
    if (valorPrincipal == null && valorMulta == null) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_DEBITO_SEM_VALORES);
    }
  }
}
