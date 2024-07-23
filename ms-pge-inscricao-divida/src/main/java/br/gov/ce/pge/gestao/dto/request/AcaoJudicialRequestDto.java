package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class AcaoJudicialRequestDto implements Serializable {

    private static final long serialVersionUID = -411422607042950274L;

    @NotNull(message = MessageCommonsConstants.MSG_NUMERO_ORDEM_JUDICIAL)
    @NotEmpty(message = MessageCommonsConstants.MSG_NUMERO_ORDEM_JUDICIAL)
    @Size(max = 50, message = MessageCommonsConstants.MSG_TAMANHO_NUMERO_ORDEM_JUDICIAL)
    private String numeroOrdemJudicial;

    @NotNull(message = MessageCommonsConstants.MSG_JUIZO)
    @NotEmpty(message = MessageCommonsConstants.MSG_JUIZO)
    @Size(max = 250, message = MessageCommonsConstants.MSG_TAMANHO_JUIZO)
    private String juizo;

    @NotNull(message = MessageCommonsConstants.MSG_DATA_ACAO)
    @NotEmpty(message = MessageCommonsConstants.MSG_DATA_ACAO)
    private String dataAcaoJudicial;

    @NotNull(message = MessageCommonsConstants.MSG_REU)
    @NotEmpty(message = MessageCommonsConstants.MSG_REU)
    @Size(max = 250, message = MessageCommonsConstants.MSG_TAMANHO_REU)
    private String reu;

    @NotNull(message = MessageCommonsConstants.MSG_AUTOR)
    @NotEmpty(message = MessageCommonsConstants.MSG_AUTOR)
    @Size(max = 250, message = MessageCommonsConstants.MSG_TAMANHO_AUTOR)
    private String autor;

    @NotNull(message = MessageCommonsConstants.MSG_TIPO_ACAO)
    @NotEmpty(message = MessageCommonsConstants.MSG_TIPO_ACAO)
    private UUID tipoAcao;

    @NotNull(message = MessageCommonsConstants.MSG_PROVIDENCIA)
    @NotEmpty(message = MessageCommonsConstants.MSG_PROVIDENCIA)
    private String providencia;

    private String observacao;

    @NotNull(message = MessageCommonsConstants.MSG_ANEXO)
    @NotEmpty(message = MessageCommonsConstants.MSG_ANEXO)
    private String anexo;
}
