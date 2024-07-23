package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.configs.*;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dao.DevedorDao;
import br.gov.ce.pge.gestao.dao.DividaDao;
import br.gov.ce.pge.gestao.dto.request.*;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.*;
import br.gov.ce.pge.gestao.mappers.tomodel.DividaMapperToModel;
import br.gov.ce.pge.gestao.repository.DividaRepository;
import br.gov.ce.pge.gestao.repository.FaseDividaRepository;
import br.gov.ce.pge.gestao.service.*;
import br.gov.ce.pge.gestao.shared.exception.AtualizaFaseExtintaException;
import br.gov.ce.pge.gestao.shared.exception.AtualizaMesmaFaseException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DividaServiceImplTest {

  @Mock
  private DividaRepository dividaRepository;

  @Mock
  private RegistroLivroService registroLivroService;

  @Mock
  private DaoConfig daoConfig;

  @Mock
  private DividaDao dividaDao;

  @Mock
  private DevedorDao devedorDao;

  @Mock
  private FeignClientConfig feignClientConfig;

  @Mock
  private OrigemDebitoService origemDebitoService;

  @Mock
  private TipoReceitaService tipoReceitaService;

  @Mock
  private ProdutoServicoService produtoServicoService;

  @Mock
  private ServicosConfig servicosConfig;

  @Mock
  private VaraOrigemService varaOrigemService;

  @Mock
  private TipoProcessoService tipoProcessoService;

  @Mock
  private StatusDebitoService statusDebitoService;

  @Mock
  private TipoPessoaService tipoPessoaService;

  @Mock
  private QualificacaoCorresponsavelService qualificacaoCorresponsavelService;

  @Mock
  private TipoDevedorService tipoDevedorService;

  @Mock
  private TipoPapelPessoaDividaService tipoPapelPessoaDividaService;

  @Mock
  private TipoContatoService tipoContatoService;

  @Mock
  private PessoaContatoConfig pessoaContatoConfig;

  @Mock
  private ConsultaPessoaService consultaPessoaService;

  @InjectMocks
  private DividaServiceImpl dividaService;

  private OrigemDebitoResponseDto origemDebitoResponseDto;

  private TipoReceitaResponseDto tipoReceitaResponseDto;

  private ProdutoServicoResponseDto produtoServicoResponseDto;

  @Mock
  private FaseDividaService faseDividaService;

  @Mock
  private AcaoJudicialService acaoJudicialService;

  @Mock
  private MotivoAtualizacaoFaseService motivoService;

  @Mock
  private FaseDividaRepository faseDividaRepository;

  @Mock
  private ServicosDividaConfig servicosDividaConfig;

  @Mock
  private ProvidenciaJudicialService providenciaJudicialService;

  @Mock
  private TipoAcaoJudicialService tipoAcaoJudicialService;

  @BeforeEach
  void setUp() {
    origemDebitoResponseDto = OrigemDebitoResponseDtoTest.getOrigem();
    tipoReceitaResponseDto = TipoReceitaResponseDtoTest.getTipoReceitaTributaria();
    produtoServicoResponseDto = ProdutoServicoResponseDtoTest.getProdutoServico();
  }

//  @Test
//  void teste_save_sucesso() {
//    when(feignClientConfig.getOrigemDebitoService()).thenReturn(origemDebitoService);
//    when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
//    when(feignClientConfig.getProdutoServicoService()).thenReturn(produtoServicoService);
//
//    when(servicosConfig.getTipoProcessoService()).thenReturn(tipoProcessoService);
//    when(servicosConfig.getStatusDebitoService()).thenReturn(statusDebitoService);
//
//    when(pessoaContatoConfig.getTipoPessoaService()).thenReturn(tipoPessoaService);
//    when(pessoaContatoConfig.getTipoPapelPessoaDividaService()).thenReturn(tipoPapelPessoaDividaService);
//    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
//    when(pessoaContatoConfig.getQualificacaoCorresponsavelService()).thenReturn(qualificacaoCorresponsavelService);
//    when(pessoaContatoConfig.getTipoDevedorService()).thenReturn(tipoDevedorService);
//    when(pessoaContatoConfig.getConsultaPessoaService()).thenReturn(consultaPessoaService);
//
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaFisica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaFisica());
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaJuridica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());
//
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel());
//
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoEmail().getId())).thenReturn(TipoContatoTest.getTipoContatoEmail());
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoCelular().getId())).thenReturn(TipoContatoTest.getTipoContatoCelular());
//
//    when(qualificacaoCorresponsavelService.findById(QualificacaoCorresponsavelTest.getQualificacao().getId())).thenReturn(QualificacaoCorresponsavelTest.getQualificacao());
//
//    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorPrincipal().getId())).thenReturn(TipoDevedorTest.getTipoDevedorPrincipal());
//
//    when(statusDebitoService.findById(any())).thenReturn(StatusDebitoTest.getStatusDebito());
//    when(tipoProcessoService.findByNome(any())).thenReturn(TipoProcessoTest.get_tipo_processo());
//
//    when(daoConfig.getDividaDao()).thenReturn(dividaDao);
//    when(daoConfig.getDevedorDao()).thenReturn(devedorDao);
//
//    when(origemDebitoService.findById(any())).thenReturn(origemDebitoResponseDto);
//    when(tipoReceitaService.findById(any())).thenReturn(tipoReceitaResponseDto);
//    when(produtoServicoService.findById(any())).thenReturn(produtoServicoResponseDto);
//
//    when(dividaDao.findUnique(any())).thenReturn(false);
//    when(devedorDao.findUnique(any())).thenReturn(false);
//    when(dividaRepository.findLast()).thenReturn(DividaTest.getDivida());
//
//    DividaRequestDto request = DividaRequestDtoTest.getDivida();
//
//    dividaService.save(request);
//
//    verify(dividaRepository, times(1)).save(any(Divida.class));
//    verify(registroLivroService, times(1)).registrar(any(Divida.class));
//  }

//  @Test
//  void test_exception_save_divida_existente() {
//    DividaRequestDto request = DividaRequestDtoTest.getDivida();
//    when(feignClientConfig.getOrigemDebitoService()).thenReturn(origemDebitoService);
//    when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
//    when(feignClientConfig.getProdutoServicoService()).thenReturn(produtoServicoService);
//
//    when(servicosConfig.getTipoProcessoService()).thenReturn(tipoProcessoService);
//    when(servicosConfig.getStatusDebitoService()).thenReturn(statusDebitoService);
//
//    when(pessoaContatoConfig.getTipoPessoaService()).thenReturn(tipoPessoaService);
//    when(pessoaContatoConfig.getTipoPapelPessoaDividaService()).thenReturn(tipoPapelPessoaDividaService);
//    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
//    when(pessoaContatoConfig.getQualificacaoCorresponsavelService()).thenReturn(qualificacaoCorresponsavelService);
//    when(pessoaContatoConfig.getTipoDevedorService()).thenReturn(tipoDevedorService);
//    when(pessoaContatoConfig.getConsultaPessoaService()).thenReturn(consultaPessoaService);
//
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaFisica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaFisica());
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaJuridica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());
//
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel());
//
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoEmail().getId())).thenReturn(TipoContatoTest.getTipoContatoEmail());
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoCelular().getId())).thenReturn(TipoContatoTest.getTipoContatoCelular());
//
//    when(qualificacaoCorresponsavelService.findById(QualificacaoCorresponsavelTest.getQualificacao().getId())).thenReturn(QualificacaoCorresponsavelTest.getQualificacao());
//
//    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorPrincipal().getId())).thenReturn(TipoDevedorTest.getTipoDevedorPrincipal());
//
//    when(statusDebitoService.findById(any())).thenReturn(StatusDebitoTest.getStatusDebito());
//    when(tipoProcessoService.findByNome(any())).thenReturn(TipoProcessoTest.get_tipo_processo());
//
//    when(origemDebitoService.findById(any())).thenReturn(origemDebitoResponseDto);
//    when(tipoReceitaService.findById(any())).thenReturn(tipoReceitaResponseDto);
//    when(produtoServicoService.findById(any())).thenReturn(produtoServicoResponseDto);
//
//    when(consultaPessoaService.findByDocumentoEntity(any())).thenReturn(null);
//
//    when(daoConfig.getDividaDao()).thenReturn(dividaDao);
//    when(dividaDao.findUnique(any())).thenReturn(true);
//
//    assertThrows(NegocioException.class, () -> dividaService.save(request));
//  }

//  @Test
//  void test_exception_save_devedor_existente() {
//    DividaRequestDto request = DividaRequestDtoTest.getDivida();
//
//    when(feignClientConfig.getOrigemDebitoService()).thenReturn(origemDebitoService);
//    when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
//    when(feignClientConfig.getProdutoServicoService()).thenReturn(produtoServicoService);
//
//    when(servicosConfig.getTipoProcessoService()).thenReturn(tipoProcessoService);
//    when(servicosConfig.getStatusDebitoService()).thenReturn(statusDebitoService);
//
//    when(pessoaContatoConfig.getTipoPessoaService()).thenReturn(tipoPessoaService);
//    when(pessoaContatoConfig.getTipoPapelPessoaDividaService()).thenReturn(tipoPapelPessoaDividaService);
//    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
//    when(pessoaContatoConfig.getQualificacaoCorresponsavelService()).thenReturn(qualificacaoCorresponsavelService);
//    when(pessoaContatoConfig.getTipoDevedorService()).thenReturn(tipoDevedorService);
//    when(pessoaContatoConfig.getConsultaPessoaService()).thenReturn(consultaPessoaService);
//
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaFisica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaFisica());
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaJuridica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());
//
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel());
//
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoEmail().getId())).thenReturn(TipoContatoTest.getTipoContatoEmail());
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoCelular().getId())).thenReturn(TipoContatoTest.getTipoContatoCelular());
//
//    when(qualificacaoCorresponsavelService.findById(QualificacaoCorresponsavelTest.getQualificacao().getId())).thenReturn(QualificacaoCorresponsavelTest.getQualificacao());
//
//    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorPrincipal().getId())).thenReturn(TipoDevedorTest.getTipoDevedorPrincipal());
//
//    when(statusDebitoService.findById(any())).thenReturn(StatusDebitoTest.getStatusDebito());
//    when(tipoProcessoService.findByNome(any())).thenReturn(TipoProcessoTest.get_tipo_processo());
//
//    when(origemDebitoService.findById(any())).thenReturn(origemDebitoResponseDto);
//    when(tipoReceitaService.findById(any())).thenReturn(tipoReceitaResponseDto);
//    when(produtoServicoService.findById(any())).thenReturn(produtoServicoResponseDto);
//
//    when(consultaPessoaService.findByDocumentoEntity(any())).thenReturn(null);
//
//    when(daoConfig.getDividaDao()).thenReturn(dividaDao);
//    when(daoConfig.getDevedorDao()).thenReturn(devedorDao);
//    when(dividaDao.findUnique(any())).thenReturn(false);
//    when(devedorDao.findUnique(any())).thenReturn(true);
//
//    assertThrows(NegocioException.class, () -> dividaService.save(request));
//  }

  @Test
  void test_find_by_id_model() {
    UUID id = DividaTest.getDivida().getId();
    when(dividaRepository.findById(id)).thenReturn(Optional.of(DividaTest.getDivida()));

    Divida result = dividaService.findByIdModel(id);
    assertNotNull(result);
  }

  @Test
  void test_find_by_id_model_erro() {
    when(dividaRepository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NegocioException.class, () ->
            dividaService.findByIdModel(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"))
    );

    verify(dividaRepository, times(1)).findById(any());
  }

  @Test
  void test_atualiza_fase() {
    UUID id = DividaTest.getDivida().getId();
    when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
    when(faseDividaService.findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());
    when(dividaRepository.findById(id)).thenReturn(Optional.of(DividaTest.getDivida()));
    when(servicosDividaConfig.getMotivoService()).thenReturn(motivoService);
    when(motivoService.findByIdModel(any())).thenReturn(MotivoAtualizacaoTest.getMotivo());
    when(servicosDividaConfig.getAcaoJudicialService()).thenReturn(acaoJudicialService);
    when(acaoJudicialService.findByIdModel(any())).thenReturn(AcaoJudicialTest.getAcaoJudicial());
    when(servicosDividaConfig.getFaseDividaRepository()).thenReturn(faseDividaRepository);

    dividaService.atualizarFase(AtualizaFaseRequestDtoTest.getRequest());

    verify(dividaRepository, times(1)).save(any());
    verify(faseDividaRepository, times(1)).save(any());
  }

  @Test
  void test_atualiza_fase_erro_response() {
    when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
    when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(null);

    NegocioException erro = assertThrows(NegocioException.class, () ->
        dividaService.atualizarFase(AtualizaFaseRequestDtoTest.getRequest())
    );

    assertEquals(MessageCommonsConstants.MSG_FASE_DIVIDA, erro.getMessage());

    verify(feignClientConfig.getFaseDividaService(), times(1)).findById(any());
  }

  @Test
  void test_atualiza_fase_erro_inativa_manual() {
    when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
    when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponseInativa());

    NegocioException erro = assertThrows(NegocioException.class, () ->
            dividaService.atualizarFase(AtualizaFaseRequestDtoTest.getRequest())
    );

    assertEquals(MessageCommonsConstants.MSG_ERRO_ATUALIZA_FASE, erro.getMessage());

    verify(feignClientConfig.getFaseDividaService(), times(1)).findById(any());
  }

  @Test
  void test_atualiza_fase_erro_fases_iguais() {
    when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
    when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponse());
    when(dividaRepository.findByIds(any())).thenReturn(Arrays.asList(DividaTest.getDivida()));

    AtualizaMesmaFaseException erro = assertThrows(AtualizaMesmaFaseException.class, () ->
            dividaService.atualizarFase(AtualizaFaseRequestDtoTest.getRequestSalvarFalso())
    );

    assertEquals("As inscrições de números 2024000000017 já se encontram na fase teste! Deseja realmente prosseguir com o cadastro da fase?", erro.getMessage());

    verify(feignClientConfig.getFaseDividaService(), times(2)).findById(any());
  }

  @Test
  void test_atualiza_fase_erro_fase_final() {
    when(feignClientConfig.getFaseDividaService()).thenReturn(faseDividaService);
    when(feignClientConfig.getFaseDividaService().findById(any())).thenReturn(FaseDividaResponseDtoTest.getResponseQuitado());
    when(dividaRepository.findByIds(any())).thenReturn(Arrays.asList(DividaTest.getDivida()));

    AtualizaFaseExtintaException erro = assertThrows(AtualizaFaseExtintaException.class, () ->
            dividaService.atualizarFase(AtualizaFaseRequestDtoTest.getRequestQuitado())
    );

    assertEquals("As inscrições de número 2024000000017 já foi finalizada (Quitada ou Extinta). Deseja realmente prosseguir com o cadastro da fase? Essa ação é irreversível e poderá acarretar a reativação da inscrição em dívida ativa.", erro.getMessage());

    verify(feignClientConfig.getFaseDividaService(), times(2)).findById(any());
  }

//  @Test
//  void test_atualiza_divida_quando_ha_mudancas() {
//    when(feignClientConfig.getOrigemDebitoService()).thenReturn(origemDebitoService);
//    when(feignClientConfig.getTipoReceitaService()).thenReturn(tipoReceitaService);
//    when(feignClientConfig.getProdutoServicoService()).thenReturn(produtoServicoService);
//
//    when(servicosConfig.getTipoProcessoService()).thenReturn(tipoProcessoService);
//    when(servicosConfig.getStatusDebitoService()).thenReturn(statusDebitoService);
//
//    when(pessoaContatoConfig.getTipoPessoaService()).thenReturn(tipoPessoaService);
//    when(pessoaContatoConfig.getTipoPapelPessoaDividaService()).thenReturn(tipoPapelPessoaDividaService);
//    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
//    when(pessoaContatoConfig.getQualificacaoCorresponsavelService()).thenReturn(qualificacaoCorresponsavelService);
//    when(pessoaContatoConfig.getTipoDevedorService()).thenReturn(tipoDevedorService);
//    when(pessoaContatoConfig.getConsultaPessoaService()).thenReturn(consultaPessoaService);
//
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaFisica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaFisica());
//    when(tipoPessoaService.findById(TipoPessoaTest.getTipoPessoaJuridica().getId())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());
//
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaDevedor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaSucessor());
//    when(tipoPapelPessoaDividaService.findById(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel().getId())).thenReturn(TipoPapelPessoaDividaTest.getTipoPapelPessoaCorresponsavel());
//
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoEmail().getId())).thenReturn(TipoContatoTest.getTipoContatoEmail());
//    when(tipoContatoService.findById(TipoContatoTest.getTipoContatoCelular().getId())).thenReturn(TipoContatoTest.getTipoContatoCelular());
//
//    when(qualificacaoCorresponsavelService.findById(QualificacaoCorresponsavelTest.getQualificacao().getId())).thenReturn(QualificacaoCorresponsavelTest.getQualificacao());
//
//    when(tipoDevedorService.findById(TipoDevedorTest.getTipoDevedorPrincipal().getId())).thenReturn(TipoDevedorTest.getTipoDevedorPrincipal());
//
//    when(statusDebitoService.findById(any())).thenReturn(StatusDebitoTest.getStatusDebito());
//    when(tipoProcessoService.findByNome(any())).thenReturn(TipoProcessoTest.get_tipo_processo());
//
//    when(origemDebitoService.findById(any())).thenReturn(origemDebitoResponseDto);
//    when(tipoReceitaService.findById(any())).thenReturn(tipoReceitaResponseDto);
//    when(produtoServicoService.findById(any())).thenReturn(produtoServicoResponseDto);
//
//    Divida existingDivida = DividaTest.getDivida();
//    DividaUpdateRequestDto updateRequestDto = DividaUpdateRequestDtoTest.getDividaUpdateRequestDto();
//    Divida updatedDivida = DividaMapperToModel.converterUpdate(updateRequestDto, existingDivida, feignClientConfig, servicosConfig, pessoaContatoConfig);
//
//    when(dividaRepository.findById(any())).thenReturn(Optional.of(existingDivida));
//
//    dividaService.update(existingDivida.getId(), updateRequestDto, "anônimo");
//    verify(dividaRepository, times(1)).save(updatedDivida);
//  }

  @Test
  void teste_detecta_mudanca_contatos_diferentes() {
    List<Contato> existingContacts = ContatoTest.getContatosList();
    List<ContatoRequestDto> updatedContacts = ContatoRequestDtoTest.getContatosListDiferente();

    boolean result = dividaService.mudouContatos(existingContacts, updatedContacts);

    assertTrue(result);
  }

  @Test
  void teste_nao_detecta_mudanca_contatos_iguais() {
    List<Contato> existingContacts = ContatoTest.getContatosList();
    List<ContatoRequestDto> updatedContacts = ContatoRequestDtoTest.getContatosList();

    boolean result = dividaService.mudouContatos(existingContacts, updatedContacts);

    assertFalse(result);
  }

  @Test
  void teste_compara_divida_deve_retornar_verdadeiro_quando_declaracao_ausencia_corresponsaveis_diferente() {
    Divida divida = new Divida();
    DividaUpdateRequestDto request = new DividaUpdateRequestDto();
    request.setDeclaracaoAusenciaCorresponsaveis(true); // Diferente do valor padrão `false`

    boolean resultado = dividaService.mudouDivida(divida, request);

    assertTrue(resultado);
  }

  @Test
  void teste_compara_debitos_com_todos_campos_iguais() {
    List<Debito> debitos = DebitoTest.get_debitos_list();
    List<DebitoRequestDto> requestList = DebitoRequestDtoTest.getDebitosList();

    assertFalse(dividaService.mudouDebitos(debitos, requestList));
  }

  @Test
  void teste_debitos_com_um_campo_diferente() {
    List<Debito> debitos = DebitoTest.get_debitos_list();
    List<DebitoRequestDto> requestList = DebitoRequestDtoTest.getDebitosListDiferente();

    assertTrue(dividaService.mudouDebitos(debitos, requestList));
  }

  @Test
  void teste_compara_divida_deve_retornar_verdadeiro_quando_um_campo_diferente() {
    Divida divida = DividaTest.getDivida();
    DividaUpdateRequestDto request = DividaUpdateRequestDtoTest.getDividaUpdateRequestDto();

    boolean resultado = dividaService.mudouDivida(divida, request);

    assertTrue(resultado);
  }

  @Test
  void teste_detecta_mudanca_endereco_todos_campos_diferentes() {
    Endereco endereco = EnderecoTest.getEndereco();
    EnderecoRequestDto request = EnderecoRequestDtoTest.getEnderecoDiferente();
    boolean result = dividaService.mudouEndereco(endereco, request);
    assertTrue(result);
  }

  @Test
  void teste_nao_detecta_mudanca_endereco_todos_campos_iguais() {
    Endereco endereco = EnderecoTest.getEndereco();
    EnderecoRequestDto request = EnderecoRequestDtoTest.getEndereco();
    boolean result = dividaService.mudouEndereco(endereco, request);
    assertFalse(result);
  }

  @Test
  void teste_deve_retornar_verdadeiro_quando_ambos_valores_sao_nulos() {
    assertTrue(dividaService.comparaValores(null, null));
  }

  @Test
  void teste_deve_retornar_verdadeiro_quando_ambos_valores_sao_zero() {
    assertTrue(dividaService.comparaValores(BigDecimal.ZERO, BigDecimal.ZERO));
  }

  @Test
  void teste_deve_retornar_verdadeiro_quando_ambos_valores_sao_iguais_e_nao_zero() {
    assertTrue(dividaService.comparaValores(new BigDecimal("123.45"), new BigDecimal("123.45")));
  }

  @Test
  void teste_deve_retornar_falso_quando_valores_sao_diferentes() {
    assertFalse(dividaService.comparaValores(new BigDecimal("123.45"), new BigDecimal("543.21")));
  }

  @Test
  void teste_deve_retornar_verdadeiro_quando_um_valor_e_nulo_e_o_outro_e_zero() {
    assertTrue(dividaService.comparaValores(null, BigDecimal.ZERO));
    assertTrue(dividaService.comparaValores(BigDecimal.ZERO, null));
  }

  @Test
  void teste_deve_retornar_falso_quando_um_valor_e_nulo_e_o_outro_e_nao_zero() {
    assertFalse(dividaService.comparaValores(null, new BigDecimal("123.45")));
    assertFalse(dividaService.comparaValores(new BigDecimal("123.45"), null));
  }

  @Test
  void test_registrar_acao_judicial() {

    when(dividaRepository.findById(any())).thenReturn(Optional.of(DividaTest.getDivida()));
    when(servicosDividaConfig.getProvidenciaJudicialService()).thenReturn(providenciaJudicialService);
    when(servicosDividaConfig.getTipoAcaoJudicialService()).thenReturn(tipoAcaoJudicialService);
    when(servicosDividaConfig.getAcaoJudicialService()).thenReturn(acaoJudicialService);
    doNothing().when(acaoJudicialService).save(any());

    dividaService.registrarAcao(AcaoJudicialRequestDtoTest.getRequest(), UUID.fromString("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));

    verify(dividaRepository, times(1)).save(any());
  }
}
