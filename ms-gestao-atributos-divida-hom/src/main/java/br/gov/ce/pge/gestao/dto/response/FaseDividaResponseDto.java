package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class FaseDividaResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String codigo;
    private String nome;
    private String descricao;
    private TipoMovimentacaoFase tipoMovimentacao;
    private Situacao situacao;
    private Boolean exigeCobranca;
    private Set<TipoCobranca> tipoCobranca = new HashSet<>();
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
