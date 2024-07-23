package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.service.*;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServicosConfig {
    private final VaraOrigemService varaOrigemService;
    private final TipoProcessoService tipoProcessoService;
    private final TipoDevedorService tipoDevedorService;
    private final TipoDocumentoService tipoDocumentoService;
    private final StatusDebitoService statusDebitoService;
    private final AcaoJudicialService acaoJudicialService;

    public ServicosConfig(VaraOrigemService varaOrigemService, TipoProcessoService tipoProcessoService, TipoDevedorService tipoDevedorService, TipoDocumentoService tipoDocumentoService, StatusDebitoService statusDebitoService, AcaoJudicialService acaoJudicialService) {
        this.varaOrigemService = varaOrigemService;
        this.tipoProcessoService = tipoProcessoService;
        this.tipoDevedorService = tipoDevedorService;
        this.tipoDocumentoService = tipoDocumentoService;
        this.statusDebitoService = statusDebitoService;
        this.acaoJudicialService = acaoJudicialService;
    }
}
