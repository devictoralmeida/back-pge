package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.repository.FaseDividaRepository;
import br.gov.ce.pge.gestao.service.AcaoJudicialService;
import br.gov.ce.pge.gestao.service.MotivoAtualizacaoFaseService;
import br.gov.ce.pge.gestao.service.ProvidenciaJudicialService;
import br.gov.ce.pge.gestao.service.TipoAcaoJudicialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicosDividaConfigTest {

    private ServicosDividaConfig servicosDividaConfig;

    @Mock
    private ProvidenciaJudicialService providenciaJudicialService;

    @Mock
    private FaseDividaRepository faseDividaRepository;

    @Mock
    private TipoAcaoJudicialService tipoAcaoJudicialService;

    @Mock
    private MotivoAtualizacaoFaseService motivoService;

    @Mock
    private AcaoJudicialService acaoJudicialService;

    @BeforeEach
    public void setUp() {
        servicosDividaConfig = new ServicosDividaConfig(providenciaJudicialService, faseDividaRepository, tipoAcaoJudicialService, motivoService, acaoJudicialService);
    }

    @Test
    void test_constructor() {
        assertEquals(providenciaJudicialService, servicosDividaConfig.getProvidenciaJudicialService());
        assertEquals(faseDividaRepository, servicosDividaConfig.getFaseDividaRepository());
        assertEquals(tipoAcaoJudicialService, servicosDividaConfig.getTipoAcaoJudicialService());
        assertEquals(motivoService, servicosDividaConfig.getMotivoService());
        assertEquals(acaoJudicialService, servicosDividaConfig.getAcaoJudicialService());
    }

}
