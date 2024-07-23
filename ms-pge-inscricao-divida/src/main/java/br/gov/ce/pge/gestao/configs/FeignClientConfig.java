package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.service.FaseDividaService;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FeignClientConfig {

    private final OrigemDebitoService origemDebitoService;
    private final TipoReceitaService tipoReceitaService;
    private final ProdutoServicoService produtoServicoService;
    private final FaseDividaService faseDividaService;

    public FeignClientConfig(OrigemDebitoService origemDebitoService, TipoReceitaService tipoReceitaService, ProdutoServicoService produtoServicoService, FaseDividaService faseDividaService) {
        this.origemDebitoService = origemDebitoService;
        this.tipoReceitaService = tipoReceitaService;
        this.produtoServicoService = produtoServicoService;
        this.faseDividaService = faseDividaService;
    }
}
