package br.gov.ce.pge.gestao.configs;

import br.gov.ce.pge.gestao.repository.ContatoRepository;
import br.gov.ce.pge.gestao.service.*;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PessoaContatoConfig {

  private final TipoPessoaService tipoPessoaService;
  private final QualificacaoCorresponsavelService qualificacaoCorresponsavelService;
  private final TipoDevedorService tipoDevedorService;
  private final TipoPapelPessoaDividaService tipoPapelPessoaDividaService;
  private final TipoContatoService tipoContatoService;
  private final ConsultaPessoaService consultaPessoaService;
  private final ContatoRepository contatoRepository;


  public PessoaContatoConfig(TipoPessoaService tipoPessoaService, QualificacaoCorresponsavelService qualificacaoCorresponsavelService, TipoDevedorService tipoDevedorService, TipoPapelPessoaDividaService tipoPapelPessoaDividaService, TipoContatoService tipoContatoService, ConsultaPessoaService consultaPessoaService, ContatoRepository contatoRepository) {
    this.tipoPessoaService = tipoPessoaService;
    this.qualificacaoCorresponsavelService = qualificacaoCorresponsavelService;
    this.tipoDevedorService = tipoDevedorService;
    this.tipoPapelPessoaDividaService = tipoPapelPessoaDividaService;
    this.tipoContatoService = tipoContatoService;
    this.consultaPessoaService = consultaPessoaService;
    this.contatoRepository = contatoRepository;
  }
}
