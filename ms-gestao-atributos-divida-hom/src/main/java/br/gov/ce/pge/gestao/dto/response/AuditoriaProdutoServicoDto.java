package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditoriaProdutoServicoDto extends AuditoriaDto implements Serializable {

    private static final long serialVersionUID = -1811656846310083541L;

    private String idProdutoServico;

    private String codigo;

    private String tipoReceitasAdicionados;

    private String tipoReceitasRemovidos;

}
