package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.contantes.SharedConstantes;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class FaseDividaFilterRequestDto {

    @Size(max = 150, message = "o nome deve conter no máximo {max} caracteres")
    private String nome;

    @Size(max = 5, message = "o código deve conter no máximo {max} caracteres")
    @Pattern(regexp = SharedConstantes.APENAS_NUMEROS, message = "o campo código deve ter apenas números")
    @Nullable
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Nullable
    private TipoMovimentacaoFase tipoMovimentacao;

    @Enumerated(EnumType.STRING)
    @Nullable
    private Situacao situacao;

    private Boolean exigeCobranca;


    private List<TipoCobranca> tipoCobranca = new ArrayList<>();


    public Map<String, Object> filters() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("nome", nome == null || nome.isEmpty() ? null : "%" + nome.toUpperCase() + "%");
        filter.put("codigo", codigo == null || codigo.isEmpty() ? null : "%" + codigo + "%");
        filter.put("situacao", situacao == null ? null : situacao);
        filter.put("tipoMovimentacao", tipoMovimentacao == null ? null : tipoMovimentacao);
        filter.put("exigeCobranca", exigeCobranca == null ? null : exigeCobranca);
        filter.put("tipoCobranca", tipoCobranca == null || tipoCobranca.isEmpty() ? null : tipoCobranca.stream().map(Enum::name).collect(Collectors.joining(",")));
        return filter;
    }
}
