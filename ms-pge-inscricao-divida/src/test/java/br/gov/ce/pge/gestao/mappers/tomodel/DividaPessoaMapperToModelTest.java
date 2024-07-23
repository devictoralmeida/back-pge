package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.dto.request.PessoaRequestDto;
import br.gov.ce.pge.gestao.dto.request.PessoaRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.DividaPessoa;
import br.gov.ce.pge.gestao.entity.TipoPessoaTest;
import br.gov.ce.pge.gestao.service.*;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DividaPessoaMapperToModelTest {

  private PessoaContatoConfig pessoaContatoConfig;
  private TipoPessoaService tipoPessoaService;
  private QualificacaoCorresponsavelService qualificacaoCorresponsavelService;
  private TipoDevedorService tipoDevedorService;
  private TipoPapelPessoaDividaService tipoPapelPessoaDividaService;
  private TipoContatoService tipoContatoService;
  private ConsultaPessoaService consultaPessoaService;

  @BeforeEach
  void setUp() {
    pessoaContatoConfig = Mockito.mock(PessoaContatoConfig.class);
    tipoPessoaService = Mockito.mock(TipoPessoaService.class);
    qualificacaoCorresponsavelService = Mockito.mock(QualificacaoCorresponsavelService.class);
    tipoDevedorService = Mockito.mock(TipoDevedorService.class);
    tipoPapelPessoaDividaService = Mockito.mock(TipoPapelPessoaDividaService.class);
    tipoContatoService = Mockito.mock(TipoContatoService.class);
    consultaPessoaService = Mockito.mock(ConsultaPessoaService.class);

    when(pessoaContatoConfig.getConsultaPessoaService()).thenReturn(consultaPessoaService);
    when(pessoaContatoConfig.getQualificacaoCorresponsavelService()).thenReturn(qualificacaoCorresponsavelService);
    when(pessoaContatoConfig.getTipoDevedorService()).thenReturn(tipoDevedorService);
    when(pessoaContatoConfig.getTipoPapelPessoaDividaService()).thenReturn(tipoPapelPessoaDividaService);
    when(pessoaContatoConfig.getTipoContatoService()).thenReturn(tipoContatoService);
    when(pessoaContatoConfig.getTipoPessoaService()).thenReturn(tipoPessoaService);

  }

  @Test
  void test_converter_list() {
    List<PessoaRequestDto> requestList = PessoaRequestDtoTest.getPessoasList();
    Divida divida = new Divida();

    when(tipoPessoaService.findById(requestList.get(0).getIdTipoPessoa())).thenReturn(TipoPessoaTest.getTipoPessoaFisica());
    when(tipoPessoaService.findById(requestList.get(1).getIdTipoPessoa())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());
    when(tipoPessoaService.findById(requestList.get(2).getIdTipoPessoa())).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());

    List<DividaPessoa> result = DividaPessoaMapperToModel.converterList(requestList, divida, pessoaContatoConfig);

    assertFalse(result.isEmpty());
    assertEquals(3, result.size());
    assertEquals(requestList.get(0).getDocumento(), result.get(0).getPessoa().getDocumento());
    assertEquals(requestList.get(1).getNomeRazaoSocial(), result.get(1).getPessoa().getNomeRazaoSocial());
    assertEquals(requestList.get(2).getDocumento(), result.get(2).getPessoa().getDocumento());
    assertEquals(requestList.get(2).getNomeRazaoSocial(), result.get(2).getPessoa().getNomeRazaoSocial());
    assertEquals(divida, result.get(0).getDivida());
    assertEquals(divida, result.get(1).getDivida());
    assertEquals(divida, result.get(2).getDivida());
    assertNotNull(result.get(1).getDataDeclaracaoAusenciaContato());
    assertNotNull(result.get(2).getDataDeclaracaoAusenciaContato());
    assertEquals(TipoPessoaTest.getTipoPessoaFisica().getId(), result.get(0).getPessoa().getTipoPessoa().getId());
    assertEquals(TipoPessoaTest.getTipoPessoaJuridica().getId(), result.get(1).getPessoa().getTipoPessoa().getId());
    assertEquals(TipoPessoaTest.getTipoPessoaJuridica().getId(), result.get(2).getPessoa().getTipoPessoa().getId());
  }

  @Test
  void test_exception_tamanho_cnpj_invalido() {
    PessoaRequestDto request = PessoaRequestDtoTest.getPessoaCorresponsavel();
    request.setDocumento("3975405900010");
    Divida divida = new Divida();

    when(tipoPessoaService.findById(any(UUID.class))).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());

    assertThrows(NegocioException.class, () -> DividaPessoaMapperToModel.converterList(Collections.singletonList(request), divida, pessoaContatoConfig));
  }

  @Test
  void test_exception_tamanho_cpf_invalido() {
    PessoaRequestDto request = PessoaRequestDtoTest.getPessoaDevedora();
    request.setDocumento("3978316200");
    Divida divida = new Divida();

    when(tipoPessoaService.findById(any(UUID.class))).thenReturn(TipoPessoaTest.getTipoPessoaFisica());

    assertThrows(NegocioException.class, () -> DividaPessoaMapperToModel.converterList(Collections.singletonList(request), divida, pessoaContatoConfig));
  }

  @Test
  void test_exception_cgf_null_com_pessoa_juridica() {
    PessoaRequestDto request = PessoaRequestDtoTest.getPessoaCorresponsavel();
    request.setCgf(null);
    Divida divida = new Divida();

    when(tipoPessoaService.findById(any(UUID.class))).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());

    assertThrows(NegocioException.class, () -> DividaPessoaMapperToModel.converterList(Collections.singletonList(request), divida, pessoaContatoConfig));
  }

  @Test
  void test_exception_corresponsavel_sem_qualificacao() {
    PessoaRequestDto request = PessoaRequestDtoTest.getPessoaCorresponsavel();
    request.getDividaPessoa().setIdQualificacaoCorresponsavel(null);
    Divida divida = new Divida();

    when(tipoPessoaService.findById(any(UUID.class))).thenReturn(TipoPessoaTest.getTipoPessoaJuridica());

    assertThrows(NegocioException.class, () -> DividaPessoaMapperToModel.converterList(Collections.singletonList(request), divida, pessoaContatoConfig));
  }

  @Test
  void test_exception_devedor_sem_tipo() {
    PessoaRequestDto request = PessoaRequestDtoTest.getPessoaDevedora();
    request.getDividaPessoa().setIdTipoDevedor(null);
    Divida divida = new Divida();

    when(tipoPessoaService.findById(any(UUID.class))).thenReturn(TipoPessoaTest.getTipoPessoaFisica());

    assertThrows(NegocioException.class, () -> DividaPessoaMapperToModel.converterList(Collections.singletonList(request), divida, pessoaContatoConfig));
  }
}
