package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
public class CondicaoAcessoRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "informar bloqueio automático")
    @Pattern(regexp = SharedConstant.APENAS_NUMEROS, message = "valor do campo bloqueio automático deve ser igual ou superior a 1")
    private String bloqueioAutomatico;

    @NotBlank(message = "informar alteração de senha")
    @Pattern(regexp = SharedConstant.APENAS_NUMEROS, message = "valor do campo alteração de senha deve ser igual ou superior a 1")
    private String alteracaoSenha;

    @NotBlank(message = "informar encerramento da sessão")
    @Pattern(regexp = SharedConstant.FORMATO_DE_HORA, message = "formato de hora inválido, precisa ser HH:MM")
    private String encerramentoSessao;

    @NotBlank(message = "informar tentativas inválidas")
    @Pattern(regexp = SharedConstant.APENAS_NUMEROS, message = "valor do campo tentativas inválidas deve ser igual ou superior a 1")
    private String tentativasInvalidas;

    @NotBlank(message = "informar senhas cadastradas")
    @Pattern(regexp = SharedConstant.APENAS_NUMEROS, message = "valor do campo senhas cadastras deve ser igual ou superior a 1")
    private String senhasCadastradas;

    @Pattern(regexp = SharedConstant.APENAS_NUMEROS, message = "valor do campo intervalo bloqueio deve ser igual ou superior a 1")
    private String intervaloBloqueio;

}
