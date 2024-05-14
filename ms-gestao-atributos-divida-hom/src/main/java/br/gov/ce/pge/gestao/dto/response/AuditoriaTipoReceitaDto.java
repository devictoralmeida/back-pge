package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

import br.gov.ce.pge.gestao.enums.Natureza;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditoriaTipoReceitaDto extends AuditoriaDto implements Serializable {

    private static final long serialVersionUID = 447373868256591788L;

    private String idTipoReceita;

    private String codigo;

    private String origensDebitosAdicionados;

    private String origensDebitosRemovidos;

    private Natureza natureza;
}
