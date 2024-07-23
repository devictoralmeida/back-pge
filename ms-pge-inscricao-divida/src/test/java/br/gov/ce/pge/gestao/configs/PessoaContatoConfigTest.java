package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.service.*;
import org.mockito.Mock;

class PessoaContatoConfigTest {
  @Mock
  private TipoPessoaService mockTipoPessoaService;

  @Mock
  private QualificacaoCorresponsavelService mockQualificacaoCorresponsavelService;

  @Mock
  private TipoDevedorService mockTipoDevedorService;

  @Mock
  private TipoPapelPessoaDividaService mockTipoPapelPessoaDividaService;

  @Mock
  private TipoContatoService mockTipoContatoService;

  @Mock
  private ConsultaPessoaService mockConsultaPessoaService;

  private PessoaContatoConfig pessoaContatoConfig;

//  @BeforeEach
//  public void setUp() {
//    pessoaContatoConfig = new PessoaContatoConfig(mockTipoPessoaService, mockQualificacaoCorresponsavelService, mockTipoDevedorService, mockTipoPapelPessoaDividaService, mockTipoContatoService, mockConsultaPessoaService);
//  }
//
//  @Test
//  void test_constructor() {
//    assertEquals(mockTipoPessoaService, pessoaContatoConfig.getTipoPessoaService());
//    assertEquals(mockQualificacaoCorresponsavelService, pessoaContatoConfig.getQualificacaoCorresponsavelService());
//    assertEquals(mockTipoDevedorService, pessoaContatoConfig.getTipoDevedorService());
//    assertEquals(mockTipoPapelPessoaDividaService, pessoaContatoConfig.getTipoPapelPessoaDividaService());
//    assertEquals(mockTipoContatoService, pessoaContatoConfig.getTipoContatoService());
//    assertEquals(mockConsultaPessoaService, pessoaContatoConfig.getConsultaPessoaService());
//  }

}
