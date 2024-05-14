package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class FaseDividaRequestDto {

    private UUID id;

    @NotBlank(message = "favor informar o nome")
    @Size(max = 150, message = "o nome deve conter no máximo {max} caracteres")
    private String nome;

    @NotBlank(message = "favor informar a descrição")
    @Size(max = 500, message = "a descrição deve conter no máximo {max} caracteres")
    private String descricao;

    @NotNull(message = "favor informar o tipo de movimentação")
    private TipoMovimentacaoFase tipoMovimentacao;

    @NotNull(message = "favor informar a situação")
    private Situacao situacao;

    @NotNull(message = "favor informar se a fase exige cobrança")
    private boolean exigeCobranca;

    private Set<TipoCobranca> tipoCobranca = new HashSet<>();
}
