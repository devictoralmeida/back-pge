package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditoriaFaseDividaDto extends AuditoriaDto implements Serializable {
    private static final long serialVersionUID = 7956399774567083313L;

    private String idFaseDivida;

    private String nome;

    private String descricao;

    private Situacao situacao;

    private String codigo;

    private TipoMovimentacaoFase tipoMovimentacao;

    private Boolean exigeCobranca;


    private String tipoCobrancasAdicionados;

    private String tipoCobrancasRemovidos;

}
