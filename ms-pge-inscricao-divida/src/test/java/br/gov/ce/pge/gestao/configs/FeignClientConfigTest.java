package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.service.FaseDividaService;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeignClientConfigTest {
  @Mock
  private OrigemDebitoService origemDebitoService;

  @Mock
  private TipoReceitaService tipoReceitaService;

  @Mock
  private ProdutoServicoService produtoServicoService;

  @Mock
  private FaseDividaService faseDividaService;

  private FeignClientConfig feignClientConfig;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    feignClientConfig = new FeignClientConfig(origemDebitoService, tipoReceitaService, produtoServicoService, faseDividaService);
  }

  @Test
  void test_constructor() {
    assertEquals(origemDebitoService, feignClientConfig.getOrigemDebitoService());
    assertEquals(tipoReceitaService, feignClientConfig.getTipoReceitaService());
    assertEquals(produtoServicoService, feignClientConfig.getProdutoServicoService());
    assertEquals(faseDividaService, feignClientConfig.getFaseDividaService());
  }
}
