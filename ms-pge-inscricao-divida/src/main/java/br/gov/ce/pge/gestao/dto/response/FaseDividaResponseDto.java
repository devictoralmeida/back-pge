package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoFase;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FaseDividaResponseDto implements Serializable {

    private static final long serialVersionUID = -1480301097303482308L;

    private UUID id;

    private String codigo;

    private String nome;

    private String descricao;

    private TipoMovimentacaoFase tipoMovimentacao;

    private Situacao situacao;

    private Boolean exigeCobranca;

    private Set<TipoCobranca> tipoCobranca;

    private TipoFase tipoFase;
}
