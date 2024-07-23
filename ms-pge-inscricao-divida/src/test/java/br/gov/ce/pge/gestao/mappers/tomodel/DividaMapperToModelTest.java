package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.FeignClientConfig;
import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.configs.ServicosConfig;
import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.*;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.*;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.service.*;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DividaMapperToModelTest {
  private FeignClientConfig feignClientConfig;

  private OrigemDebitoService origemDebitoService;

  private TipoReceitaService tipoReceitaService;

  private ProdutoServicoService produtoServicoService;

  private ServicosConfig servicosConfig;

  private TipoDocumentoService tipoDocumentoService;

  private VaraOrigemService varaOrigemService;

  private TipoProcessoService tipoProcessoService;

  private StatusDebitoService statusDebitoService;

  private TipoPessoaService tipoPessoaService;

  private QualificacaoCorresponsavelService qualificacaoCorresponsavelService;

  private TipoDevedorService tipoDevedorService;

  private TipoPapelPessoaDividaService tipoPapelPessoaDividaService;

  private TipoContatoService tipoContatoService;

  private ConsultaPessoaService consultaPessoaService;

  private PessoaContatoConfig pessoaContatoConfig;

  @BeforeEach
  void setUp() {
    feignClientConfig = Mockito.mock(FeignClientConfig.class);
    origemDebitoService = Mockito.mock(OrigemDebitoService.class);
    tipoReceitaService = Mockito.mock(TipoReceitaService.class);
    produtoServicoService = Mockito.mock(ProdutoServicoService.class);
    servicosConfig = Mockito.mock(ServicosConfig.class);
    tipoDocumentoService = Mockito.mock(TipoDocumentoService.class);
    varaOrigemService = Mockito.mock(VaraOrigemService.class);
    tipoProcessoService = Mockito.mock(TipoProcessoService.class);
    statusDebitoService = Mockito.mock(StatusDebitoService.class);
    tipoPessoaService = Mockito.mock(TipoPessoaService.class);
    pessoaContatoConfig = Mockito.mock(PessoaContatoConfig.class);
    qualificacaoCorresponsavelService = Mockito.mock(QualificacaoCorresponsavelService.class);
    tipoDevedorService = Mockito.mock(TipoDevedorService.class);
    tipoPapelPessoaDividaService = Mockito.mock(TipoPapelPessoaDividaService.class);
    tipoContatoService = Mockito.mock(TipoContatoService.class);
    consultaPessoaService = Mockito.mock(ConsultaPessoaService.class);

    when(feignClientConfig.getOrigemDebitoService()).thenReturn(origemDebitoService);
    when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
    when(feignClientConfig.getProdutoServicoService()).thenReturn(produtoServicoService);

    when(servicosConfig.getTipoDocumentoService()).thenReturn(tipoDocumentoService);
    when(servicosConfig.getVaraOrigemService()).thenReturn(varaOrigemService);
    when(servicosConfig.getTipoProcessoService()).thenReturn(tipoProcessoService);
    when(servicosConfig.getStatusDebitoService()).thenReturn(statusDebitoService);

    when(pessoaContatoConfig.getTipoPessoaService()).thenReturn(tipoPessoaService);
    when(pessoaContatoConfig.getTipoPapelPessoaDividaService()).thenReturn(tipoPapelPessoaDividaService);
    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
    when(pessoaContatoConfig.getQualificacaoCorresponsavelService()).thenReturn(qualificacaoCorresponsavelService);
    when(pessoaContatoConfig.getTipoDevedorService()).thenReturn(tipoDevedorService);
    when(pessoaContatoConfig.getConsultaPessoaService()).thenReturn(consultaPessoaService);

    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaFisica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaFisica());
    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaJuridica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());

    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor());
    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor());
    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel());

    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoEmail().getId())).thenReturn(TipoContatoTest.getTipoContatoEmail());
    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoCelular().getId())).thenReturn(TipoContatoTest.getTipoContatoCelular());

    when(qualificacaoCorresponsavelService.findById(QualificacaoCorresponsavelTest.getQualificacao().getId())).thenReturn(QualificacaoCorresponsavelTest.getQualificacao());

    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorPrincipal().getId())).thenReturn(TipoDevedorTest.getTipoDevedorPrincipal());
    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorSubsidiario().getId())).thenReturn(TipoDevedorTest.getTipoDevedorSubsidiario());
    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorSolidario().getId())).thenReturn(TipoDevedorTest.getTipoDevedorSolidario());

    when(statusDebitoService.findById(any())).thenReturn(StatusDebitoTest.getStatusDebito());
    when(tipoProcessoService.findByNome(any())).thenReturn(TipoProcessoTest.get_tipo_processo());
    when(varaOrigemService.findByNome(any())).thenReturn(VaraOrigemTest.get_vara_origem());
  }

  @Test
  void teste_converter() {
    when(origemDebitoService.findById(any())).thenReturn(OrigemDebitoResponseDtoTest.getOrigem());
    when(tipoReceitaService.findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributaria());
    when(produtoServicoService.findById(any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServico());

    Divida model = DividaMapperToModel.converter(DividaRequestDtoTest.getDivida(),
            feignClientConfig, servicosConfig, pessoaContatoConfig);

    assertNotNull(model.getIdOrigemDebito());
    assertNotNull(model.getIdTipoReceita());
    assertNotNull(model.getIdProdutoServico());
    assertEquals("teste", model.getDisposicoesLegais());
    assertEquals("teste", model.getNaturezaFundamentacao());
    assertEquals("teste", model.getInexistenciaCausaSuspensivas());
    assertEquals("1234567890", model.getNumeroDocumento());
    assertEquals(LocalDate.parse("2024-03-20"), model.getDataDocumento());
    assertEquals("123456789", model.getTermoRevelia());
    assertEquals(LocalDate.parse("2024-03-20"), model.getDataTermoRevelia());
    assertEquals("123456789", model.getNumeroOficio());
    assertEquals("98765432109876543210", model.getNumeroProcessoAdministrativo());
    assertEquals(LocalDate.parse("2024-03-20"), model.getDataProcesso());
    assertEquals("6789", model.getNumeroAcordao());
    assertEquals(LocalDate.parse("2024-03-20"), model.getDataTransitoJulgado());
    assertNull(model.getPlacaVeiculo());
    assertNull(model.getNumeroChassi());
    assertNull(model.getGuiaItcd());
    assertNull(model.getSequencialParcelamento());
    assertNull(model.getProtocoloJudicial());
  }

//  @Test
//  void teste_converter_update() {
//    when(origemDebitoService.findById(any())).thenReturn(OrigemDebitoResponseDtoTest.getOrigem());
//    when(tipoReceitaService.findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributaria());
//    when(produtoServicoService.findById(any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServico());
//
//    Divida model = DividaMapperToModel.converterUpdate(DividaUpdateRequestDtoTest.getDividaUpdateRequestDto(), DividaTest.getDivida(),
//            feignClientConfig, servicosConfig, pessoaContatoConfig);
//
//    assertNotNull(model.getIdOrigemDebito());
//    assertNotNull(model.getIdTipoReceita());
//    assertNotNull(model.getIdProdutoServico());
//    assertEquals("teste 2", model.getDisposicoesLegais());
//    assertEquals("teste", model.getNaturezaFundamentacao());
//    assertEquals("teste", model.getInexistenciaCausaSuspensivas());
//    assertEquals("1234567890", model.getNumeroDocumento());
//    assertEquals(LocalDate.parse("2024-03-20"), model.getDataDocumento());
//    assertEquals("123456789", model.getTermoRevelia());
//    assertEquals(LocalDate.parse("2024-03-20"), model.getDataTermoRevelia());
//    assertEquals("123456789", model.getNumeroOficio());
//    assertEquals("98765432109876543210", model.getNumeroProcessoAdministrativo());
//    assertEquals(LocalDate.parse("2024-03-20"), model.getDataProcesso());
//    assertEquals("6789", model.getNumeroAcordao());
//    assertEquals(LocalDate.parse("2024-03-20"), model.getDataTransitoJulgado());
//    assertNull(model.getPlacaVeiculo());
//    assertNull(model.getNumeroChassi());
//    assertNull(model.getGuiaItcd());
//    assertNull(model.getSequencialParcelamento());
//    assertNull(model.getProtocoloJudicial());
//    assertEquals(1, model.getMotivosAtualizacaoDivida().size());
//  }

  @Test
  void validacao_campos_deve_lancar_excecao_quando_origem_debito_esta_inativa() {
    DividaRequestDto request = new DividaRequestDto();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();
    origemDebito.setSituacao(Situacao.INATIVA);

    when(origemDebitoService.findById(any())).thenReturn(origemDebito);
    when(tipoReceitaService.findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributaria());
    when(produtoServicoService.findById(any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServico());

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      DividaMapperToModel.validacaoCampos(request, feignClientConfig);
    });

    assertEquals(MessageCommonsConstants.MSG_ERRO_ORIGEM_DEBITO_INATIVA, exception.getMessage());
  }

  @Test
  void validacao_campos_deve_lancar_excecao_quando_tipo_receita_esta_inativa() {
    DividaRequestDto request = new DividaRequestDto();
    TipoReceitaResponseDto tipoReceita = TipoReceitaResponseDtoTest.getTipoReceitaTributaria();
    tipoReceita.setSituacao(Situacao.INATIVA);

    when(origemDebitoService.findById(any())).thenReturn(OrigemDebitoResponseDtoTest.getOrigem());
    when(tipoReceitaService.findById(any())).thenReturn(tipoReceita);
    when(produtoServicoService.findById(any())).thenReturn(ProdutoServicoResponseDtoTest.getProdutoServico());


    NegocioException exception = assertThrows(NegocioException.class, () -> {
      DividaMapperToModel.validacaoCampos(request, feignClientConfig);
    });

    assertEquals(MessageCommonsConstants.MSG_ERRO_TIPO_RECEITA_INATIVO, exception.getMessage());
  }

  @Test
  void validacao_campos_deve_lancar_excecao_quando_produto_servico_esta_inativo() {
    DividaRequestDto request = new DividaRequestDto();
    ProdutoServicoResponseDto produtoServico = ProdutoServicoResponseDtoTest.getProdutoServico();
    produtoServico.setSituacao(Situacao.INATIVA);

    when(origemDebitoService.findById(any())).thenReturn(OrigemDebitoResponseDtoTest.getOrigem());
    when(tipoReceitaService.findById(any())).thenReturn(TipoReceitaResponseDtoTest.getTipoReceitaTributaria());
    when(produtoServicoService.findById(any())).thenReturn(produtoServico);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      DividaMapperToModel.validacaoCampos(request, feignClientConfig);
    });

    assertEquals(MessageCommonsConstants.MSG_ERRO_PRODUTO_SERVICO_INATIVO, exception.getMessage());
  }

  @Test
  void test_deve_lancar_excecao_quando_naturezaFundamentacao_esta_nula() {
    DividaRequestDto request = new DividaRequestDto();
    TipoReceitaResponseDto tipoReceita = new TipoReceitaResponseDto();
    tipoReceita.setNatureza(Natureza.NAO_TRIBUTARIA);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      DividaMapperToModel.isTipoReceitaNaoTributario(request, tipoReceita);
    });

    assertEquals(MessageCommonsConstants.MENSAGEM_INFORMAR_NATUREZA_FUNDAMENTACAO, exception.getMessage());
  }

  @Test
  void test_verifica_data_transito_julgado() {
    DividaRequestDto request = new DividaRequestDto();
    request.setDataTransitoJulgado(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaDataTransitoJulgado(request));
  }

  @Test
  void test_verifica_data_transito_julgado_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataTransitoJulgado(request));
  }

  @Test
  void test_verifica_tipo_documento() {
    DividaRequestDto request = new DividaRequestDto();
    request.setIdTipoDocumento(UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9"));
    request.setNumeroDocumento("1234567890");
    request.setTermoRevelia("123456789");
    request.setDataDocumento(LocalDate.now());
    request.setDataTermoRevelia(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaTipoDocumento(request));
  }

  @Test
  void test_verifica_tipo_documento_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaTipoDocumento(request));
  }

  @Test
  void test_verifica_data_constituicao_definitiva_credito() {
    DividaRequestDto request = new DividaRequestDto();
    request.setDataConstituicaoDefinitivaCredito(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaDataConstituicaoDefinitivaCreditoDivida(request));
  }

  @Test
  void test_verifica_data_constituicao_definitiva_credito_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataConstituicaoDefinitivaCreditoDivida(request));
  }

  @Test
  void test_verifica_termo_revelia() {
    DividaRequestDto request = new DividaRequestDto();
    request.setTermoRevelia("termo");
    request.setDataTermoRevelia(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaTermoRevelia(request));
  }

  @Test
  void test_verifica_termo_revelia_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaTermoRevelia(request));
  }

  @Test
  void test_verifica_data_documento() {
    DividaRequestDto request = new DividaRequestDto();
    request.setDataDocumento(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaDataDocumento(request));
  }

  @Test
  void test_verifica_data_documento_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataDocumento(request));
  }

  @Test
  void test_verifica_natureza_fundamentacao() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaNaturezaFundamentacao(request));
  }

  @Test
  void test_verifica_protocolo_judicial() {
    DividaRequestDto request = new DividaRequestDto();
    request.setProtocoloJudicial("protocolo");
    assertDoesNotThrow(() -> DividaMapperToModel.verificaProtocoloJudicial(request));
  }

  @Test
  void test_verifica_protocolo_judicial_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaProtocoloJudicial(request));
  }

  @Test
  void test_verifica_chassi() {
    DividaRequestDto request = new DividaRequestDto();
    request.setNumeroChassi("chassi");
    assertDoesNotThrow(() -> DividaMapperToModel.verificaChassi(request));
  }

  @Test
  void test_verifica_chassi_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaChassi(request));
  }

  @Test
  void test_verifica_placa_veiculo() {
    DividaRequestDto request = new DividaRequestDto();
    request.setPlacaVeiculo("placa");
    assertDoesNotThrow(() -> DividaMapperToModel.verificaPlacaVeiculo(request));
  }

  @Test
  void test_verifica_placa_veiculo_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaPlacaVeiculo(request));
  }

  @Test
  void test_verifica_guia_itcd() {
    DividaRequestDto request = new DividaRequestDto();
    request.setGuiaItcd("ITCD1234");

    assertDoesNotThrow(() -> DividaMapperToModel.verificaGuiaItcd(request));
  }

  @Test
  void test_verifica_guia_itcd_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaGuiaItcd(request));
  }

  @Test
  void test_verifica_sequencial_parcelamento() {
    DividaRequestDto request = new DividaRequestDto();
    request.setSequencialParcelamento("sequencial");

    assertDoesNotThrow(() -> DividaMapperToModel.verificaSequencialParcelamento(request));
  }

  @Test
  void test_verifica_sequencial_parcelamento_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaSequencialParcelamento(request));
  }

  @Test
  void test_verifica_data_termo_revelia() {
    DividaRequestDto request = new DividaRequestDto();

    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaDataTermoRevelia(request));

    request.setDataTermoRevelia(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaDataTermoRevelia(request));
  }

  @Test
  void test_verifica_numero_documento() {
    DividaRequestDto request = new DividaRequestDto();
    request.setNumeroDocumento("123456789");

    assertDoesNotThrow(() -> DividaMapperToModel.verificaNumeroDocumento(request));
  }

  @Test
  void test_verifica_numero_documento_exception() {
    DividaRequestDto request = new DividaRequestDto();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaNumeroDocumento(request));
  }

  @Test
  void test_verifica_se_tipo_documento_is_ai() {
    DividaRequestDto request = new DividaRequestDto();
    request.setIdTipoDocumento(UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9"));
    request.setTermoRevelia("termo");
    request.setDataDocumento(LocalDate.now());
    request.setDataTermoRevelia(LocalDate.now());

    assertDoesNotThrow(() -> DividaMapperToModel.verificaSeTipoDocumentoDiferenteAI(request));
  }

  @Test
  void test_valida_origens_permitidas_tipo_receita_data_constituicao_definitiva_credito() {
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();
    TipoReceitaResponseDto tipoReceita = TipoReceitaResponseDtoTest.getTipoReceitaTributaria();

    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita));

    tipoReceita.setNatureza(Natureza.NAO_TRIBUTARIA);

    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita));

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO);
    tipoReceita.setNatureza(Natureza.TRIBUTARIA);

    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita));

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_ITCD);

    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita));

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_AI);

    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita));

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_AIAM);

    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita));
  }

  @Test
  void test_verifica_associacao_produto_servico() {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    ProdutoServicoResponseDto produtoServico = ProdutoServicoResponseDtoTest.getProdutoServico();

    assertDoesNotThrow(() -> DividaMapperToModel.verificaAssociacaoProdutoServico(request, produtoServico));

    produtoServico.setIdsTipoReceitas(List.of(UUID.randomUUID()));

    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaAssociacaoProdutoServico(request, produtoServico));
  }

  @Test
  void test_verifica_associacao_tipo_receita() {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    TipoReceitaResponseDto tipoReceita = TipoReceitaResponseDtoTest.getTipoReceitaTributaria();

    assertDoesNotThrow(() -> DividaMapperToModel.verificaAssociacaoTipoReceita(request, tipoReceita));

    tipoReceita.setOrigemDebitos(List.of(UUID.randomUUID()));

    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaAssociacaoTipoReceita(request, tipoReceita));
  }

  @Test
  void test_is_origem_debito_permitida() {
    OrigemDebitoResponseDto request = OrigemDebitoResponseDtoTest.getOrigem();
    boolean result = DividaMapperToModel.isOrigemDebitoPermitida(request);

    assertFalse(result);

    request.setId(IdsConstants.ID_ORIGEM_DEBITO_AI);
    boolean result2 = DividaMapperToModel.isOrigemDebitoPermitida(request);

    assertTrue(result2);


    request.setId(IdsConstants.ID_ORIGEM_DEBITO_AIAM);
    boolean result3 = DividaMapperToModel.isOrigemDebitoPermitida(request);

    assertTrue(result3);

    request.setId(IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO);
    boolean result4 = DividaMapperToModel.isOrigemDebitoPermitida(request);

    assertTrue(result4);
  }

  @Test
  void test_verifica_numero_acordao() {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    request.setNumeroAcordao(null);
    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaNumeroAcordao(request));

    request.setNumeroAcordao("1234567890");
    assertDoesNotThrow(() -> DividaMapperToModel.verificaNumeroAcordao(request));
  }

  @Test
  void test_valida_origem_debito_permitida_guia_itcd() {
    OrigemDebitoResponseDto request = OrigemDebitoResponseDtoTest.getOrigem();
    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaOrigensPermitidasGuiaITCD(request));

    request.setId(IdsConstants.ID_ORIGEM_DEBITO_ITCD);
    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasGuiaITCD(request));

    request.setId(IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO);
    assertDoesNotThrow(() -> DividaMapperToModel.validaOrigensPermitidasGuiaITCD(request));
  }

  @Test
  void test_valida_tipo_documento() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    divida.setIdTipoDocumento(UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9"));
    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaTipoDocumento(divida, origemDebito));

    divida.setIdTipoDocumento(null);
    assertDoesNotThrow(() -> DividaMapperToModel.validaTipoDocumento(divida, origemDebito));
  }

  @Test
  void test_is_origem_ai_aiam() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    divida.setIdTipoDocumento(UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9"));
    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_AI);
    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemAiOuAiam(divida, origemDebito));

    divida.setIdTipoDocumento(null);
    divida.setDataTransitoJulgado(null);
    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemAiOuAiam(divida, origemDebito));
  }

  @Test
  void test_is_origem_resto_parcelamento() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    divida.setDataConstituicaoDefinitivaCredito(LocalDate.now());
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemRestoParcelamento(divida, origemDebito));

    divida.setSequencialParcelamento("sequencial");

    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemRestoParcelamento(divida, origemDebito));
  }

  @Test
  void test_is_origem_itcd() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    divida.setDataConstituicaoDefinitivaCredito(LocalDate.now());
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_ITCD);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemItcd(divida, origemDebito));

    divida.setGuiaItcd("ITCD1234");

    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemItcd(divida, origemDebito));
  }

  @Test
  void test_is_origem_ipva_detran() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_IPVA);
    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemIpvaOuDetran(divida, origemDebito));

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_DETRAN);
    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemIpvaOuDetran(divida, origemDebito));

    divida.setPlacaVeiculo("ORR7555");
    divida.setNumeroChassi("12345678912316516");

    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemIpvaOuDetran(divida, origemDebito));
  }

  @Test
  void test_is_origem_custas_judiciais() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_CUSTAS_JUDICIAIS);
    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemCustasJudiciais(divida, origemDebito));

    divida.setProtocoloJudicial("protocolo");
    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemCustasJudiciais(divida, origemDebito));
  }

  @Test
  void test_validacoes_vara_origem() {
    DividaRequestDto divida = DividaRequestDtoTest.getDivida();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_TJ);
    divida.setVaraOrigem(VaraOrigemTest.get_vara_origem().getNome());

    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemTj(divida, origemDebito));

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_CUSTAS_JUDICIAIS);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemTj(divida, origemDebito));
  }

  @Test
  void test_converter_exception_debitos_vazio() {
    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDebitos(Collections.emptyList()));
  }

  @Test
  void test_converter_exception_devedores_vazio() {
    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDevedores(Collections.emptyList()));
  }

  @Test
  void test_deve_setar_data_declaracao_ausencia_corresponsavel_corretamente() {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    request.setDeclaracaoAusenciaCorresponsaveis(false);
    Divida divida = new Divida();

    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDeclaracaoAusenciaCorresponsaveis(divida, request));

    request.setDeclaracaoAusenciaCorresponsaveis(true);
    DividaMapperToModel.validaDeclaracaoAusenciaCorresponsaveis(divida, request);
    assertNotNull(divida.getDataDeclaracaoAusenciaCorresponsaveis());
  }

  @Test
  void test_nao_deve_setar_data_declaracao_ausencia_corresponsavel_quando_declaracao_ausencia_false() {
    DividaRequestDto request = DividaRequestDtoTest.getDivida();
    request.setDeclaracaoAusenciaCorresponsaveis(false);
    Divida divida = new Divida();

    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDeclaracaoAusenciaCorresponsaveis(divida, request));
    assertNull(divida.getDataDeclaracaoAusenciaCorresponsaveis());
  }

  @Test
  void test_exception_data_constituicao_definitiva_credito_debito_null() {
    DebitoRequestDto debito = Mockito.mock(DebitoRequestDto.class);

    when(debito.getDataConstituicaoDefinitivaCredito()).thenReturn(null);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDataConstituicaoDefinitivaCreditoDebito(Collections.singletonList(debito)));
  }

  @Test
  void test_exception_data_constituicao_definitiva_credito_debito_diferente_data_vencimento_mais_um_dia() {
    DebitoRequestDto debito = Mockito.mock(DebitoRequestDto.class);

    when(debito.getDataConstituicaoDefinitivaCredito()).thenReturn(LocalDate.parse("2024-03-29"));
    when(debito.getDataVencimento()).thenReturn(LocalDate.parse("2024-01-29"));

    assertThrows(NegocioException.class, () -> DividaMapperToModel.comparaPayloadDataCorreta(debito));
  }

  @Test
  void test_sem_exception_data_constituicao_definitiva_credito_debito_diferente_data_vencimento_mais_um_dia() {
    DebitoRequestDto debito = Mockito.mock(DebitoRequestDto.class);

    when(debito.getDataConstituicaoDefinitivaCredito()).thenReturn(LocalDate.now());
    when(debito.getDataVencimento()).thenReturn(LocalDate.now().minusDays(1));

    assertDoesNotThrow(() -> DividaMapperToModel.comparaPayloadDataCorreta(debito));
  }

  @Test
  void test_exception_sem_devedor_principal() {
    PessoaRequestDto devedor = Mockito.mock(PessoaRequestDto.class);
    DividaPessoaRequestDto dividaPessoa = Mockito.mock(DividaPessoaRequestDto.class);

    when(devedor.getDividaPessoa()).thenReturn(dividaPessoa);
    when(devedor.getDividaPessoa().getIdPapelPessoa()).thenReturn(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR);
    when(devedor.getDividaPessoa().getIdTipoDevedor()).thenReturn(IdsConstants.ID_TIPO_DEVEDOR_SOLIDARIO);

    List<PessoaRequestDto> devedores = Collections.singletonList(devedor);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.verificaTiposDevedores(devedores));
  }

  @Test
  void test_exception_devedor_principal_e_solidario() {
    PessoaRequestDto devedor = Mockito.mock(PessoaRequestDto.class);
    DividaPessoaRequestDto dividaPessoa = Mockito.mock(DividaPessoaRequestDto.class);

    when(devedor.getDividaPessoa()).thenReturn(dividaPessoa);
    when(devedor.getDividaPessoa().getIdPapelPessoa()).thenReturn(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR);
    when(devedor.getDividaPessoa().getIdTipoDevedor()).thenReturn(IdsConstants.ID_TIPO_DEVEDOR_PRINCIPAL);

    List<PessoaRequestDto> devedores = Collections.singletonList(devedor);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDevedorTipoPrincipal(true, devedores));
  }

  @Test
  void test_exception_sem_devedores_solidarios_suficiente() {
    PessoaRequestDto devedor = Mockito.mock(PessoaRequestDto.class);
    DividaPessoaRequestDto dividaPessoa = Mockito.mock(DividaPessoaRequestDto.class);

    when(devedor.getDividaPessoa()).thenReturn(dividaPessoa);
    when(devedor.getDividaPessoa().getIdPapelPessoa()).thenReturn(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR);
    when(devedor.getDividaPessoa().getIdTipoDevedor()).thenReturn(IdsConstants.ID_TIPO_DEVEDOR_SOLIDARIO);

    List<PessoaRequestDto> devedores = Collections.singletonList(devedor);

    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDevedorTipoSolidario(devedores));
  }

  @Test
  void test_exception_devedor_subsidiario_sem_devedor_principal() {
    assertThrows(NegocioException.class, () -> DividaMapperToModel.validaDevedorTipoSubsidiario(false, false));
  }

  @Test
  void test_is_origem_ipva_ou_icms() {
    List<DebitoRequestDto> debitos = DebitoRequestDtoTest.getDebitosList();
    OrigemDebitoResponseDto origemDebito = OrigemDebitoResponseDtoTest.getOrigem();

    origemDebito.setId(IdsConstants.ID_ORIGEM_DEBITO_IPVA);

    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemIpvaOuIcms(debitos, origemDebito));

    OrigemDebitoResponseDto origemDebito2 = OrigemDebitoResponseDtoTest.getOrigem();
    origemDebito2.setId(IdsConstants.ID_ORIGEM_DEBITO_ICMS);

    assertDoesNotThrow(() -> DividaMapperToModel.isOrigemIpvaOuIcms(debitos, origemDebito2));

    debitos.get(0).setDataConstituicaoDefinitivaCredito(null);
    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemIpvaOuIcms(debitos, origemDebito));

    debitos.get(1).setDataConstituicaoDefinitivaCredito(LocalDate.now());
    assertThrows(NegocioException.class, () -> DividaMapperToModel.isOrigemIpvaOuIcms(debitos, origemDebito2));
  }


}
