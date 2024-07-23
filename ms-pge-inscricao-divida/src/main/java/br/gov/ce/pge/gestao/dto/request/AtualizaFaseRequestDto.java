package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AtualizaFaseRequestDto implements Serializable {

    private static final long serialVersionUID = 6051889334021318950L;

    @NotNull(message = MessageCommonsConstants.MSG_INFORMAR_INSCRICOES)
    @NotEmpty(message = MessageCommonsConstants.MSG_INFORMAR_INSCRICOES)
    private List<String> dividas;

    @NotNull(message = MessageCommonsConstants.MSG_INFORMAR_FASE)
    @NotEmpty(message = MessageCommonsConstants.MSG_INFORMAR_FASE)
    private String fase;

    private List<String> acaoJudicial;

    private String motivo;

    @NotNull(message = MessageCommonsConstants.MSG_INFORMAR_OBSERVACAO)
    @NotEmpty(message = MessageCommonsConstants.MSG_INFORMAR_OBSERVACAO)
    private String observacao;

    private String anexo;

    private Boolean salvar = false;
}
