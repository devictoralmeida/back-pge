package br.gov.ce.pge.gestao.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
public class CondicaoAcessoRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String APENAS_NUMEROS = "^[1-9]\\d*$";
    private static final String FORMATO_DE_HORA = "^(\\d+):([0-5]\\d)$";

    @NotBlank(message = "informar bloqueio automático")
    @Pattern(regexp = APENAS_NUMEROS, message = "valor do campo bloqueio automático deve ser igual ou superior a 1")
    private String bloqueioAutomatico;

    @NotBlank(message = "informar alteração de senha")
    @Pattern(regexp = APENAS_NUMEROS, message = "valor do campo alteração de senha deve ser igual ou superior a 1")
    private String alteracaoSenha;

    @NotBlank(message = "informar encerramento da sessão")
    @Pattern(regexp = FORMATO_DE_HORA, message = "formato de hora inválido, precisa ser HH:MM")
    private String encerramentoSessao;

    @NotBlank(message = "informar tentativas inválidas")
    @Pattern(regexp = APENAS_NUMEROS, message = "valor do campo tentativas inválidas deve ser igual ou superior a 1")
    private String tentativasInvalidas;

    @NotBlank(message = "informar senhas cadastradas")
    @Pattern(regexp = APENAS_NUMEROS, message = "valor do campo senhas cadastras deve ser igual ou superior a 1")
    private String senhasCadastradas;

    @Pattern(regexp = APENAS_NUMEROS, message = "valor do campo intervalo bloqueio deve ser igual ou superior a 1")
    private String intervaloBloqueio;

}
