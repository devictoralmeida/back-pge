package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicosConfigTest {
  @Mock
  private VaraOrigemService mockVaraOrigemService;

  @Mock
  private TipoProcessoService mockTipoProcessoService;

  @Mock
  private TipoDevedorService mockTipoDevedorService;

  @Mock
  private TipoDocumentoService mockTipoDocumentoService;

  @Mock
  private StatusDebitoService mockStatusDebitoService;

  @Mock
  private AcaoJudicialService mockAcaoJudicialService;

  private ServicosConfig servicosConfig;

  @BeforeEach
  public void setUp() {
    servicosConfig = new ServicosConfig(mockVaraOrigemService, mockTipoProcessoService, mockTipoDevedorService, mockTipoDocumentoService, mockStatusDebitoService, mockAcaoJudicialService);
  }

  @Test
  void test_constructor() {
    assertEquals(mockVaraOrigemService, servicosConfig.getVaraOrigemService());
    assertEquals(mockTipoProcessoService, servicosConfig.getTipoProcessoService());
    assertEquals(mockTipoDevedorService, servicosConfig.getTipoDevedorService());
    assertEquals(mockTipoDocumentoService, servicosConfig.getTipoDocumentoService());
    assertEquals(mockStatusDebitoService, servicosConfig.getStatusDebitoService());
    assertEquals(mockAcaoJudicialService, servicosConfig.getAcaoJudicialService());
  }

}
