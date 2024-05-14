package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditoriaOrigemDebitoDto extends AuditoriaDto implements Serializable {
    private static final long serialVersionUID = 7956399774567083313L;

    private String idOrigemDebito;

    private String descricao;

    private String nome;

    private Situacao situacao;
}
