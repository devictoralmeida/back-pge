package br.gov.ce.pge.gestao.configs;
import br.gov.ce.pge.gestao.repository.FaseDividaRepository;
import br.gov.ce.pge.gestao.service.*;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServicosDividaConfig {

    private final ProvidenciaJudicialService providenciaJudicialService;
    private final FaseDividaRepository faseDividaRepository;
    private final TipoAcaoJudicialService tipoAcaoJudicialService;
    private final MotivoAtualizacaoFaseService motivoService;
    private final AcaoJudicialService acaoJudicialService;

    public ServicosDividaConfig(ProvidenciaJudicialService providenciaJudicialService, FaseDividaRepository faseDividaRepository,
                                TipoAcaoJudicialService tipoAcaoJudicialService, MotivoAtualizacaoFaseService motivoService,
                                AcaoJudicialService acaoJudicialService) {
        this.providenciaJudicialService = providenciaJudicialService;
        this.faseDividaRepository = faseDividaRepository;
        this.tipoAcaoJudicialService = tipoAcaoJudicialService;
        this.motivoService = motivoService;
        this.acaoJudicialService = acaoJudicialService;
    }

}
