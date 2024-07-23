package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DividaTest {

  public static Divida getDivida() {
    Divida divida = new Divida();
    divida.setId(UUID.fromString("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));
    divida.setIdTipoReceita(UUID.fromString("186ca579-d170-4f63-a2f8-bc8a2f3cda7a"));
    divida.setIdOrigemDebito(UUID.fromString("6a711477-05c5-4f8f-ab19-9d6bb210d7eb"));
    divida.setIdProdutoServico(UUID.fromString("227940ef-cd0f-4cde-9a94-ffb01bc9a724"));
    divida.setDisposicoesLegais("Disposições legais");
    divida.setNaturezaFundamentacao("Natureza fundamentação");
    divida.setInexistenciaCausaSuspensivas("Inexistência de causa suspensiva");
    divida.setNumeroDocumento("1234567890");
    divida.setDataDocumento(LocalDate.parse("2024-03-30"));
    divida.setTermoRevelia("Termo de revelia");
    divida.setDataTermoRevelia(LocalDate.parse("2024-03-31"));
    divida.setNumeroOficio("12345");
    divida.setNumeroProcessoAdministrativo("98765432109876543210");
    divida.setDataProcesso(LocalDate.parse("2024-04-01"));
    divida.setNumeroAcordao("54321");
    divida.setDataTransitoJulgado(LocalDate.parse("2024-04-02"));
    divida.setDataConstituicaoDefinitivaCredito(LocalDate.now());
    divida.setPlacaVeiculo("ABC1234");
    divida.setNumeroChassi("1HGBH41JXMN109186");
    divida.setGuiaItcd("ITCD1234");
    divida.setSequencialParcelamento("001");
    divida.setProtocoloJudicial("PJ1234");
    divida.setVaraOrigem(VaraOrigemTest.get_vara_origem());
    divida.setTipoProcesso(TipoProcessoTest.get_tipo_processo());
    divida.setTipoDocumento(TipoDocumentoTest.getTipoDocumento());

    List<DividaPessoa> listDividaPessoas = new ArrayList<>();
    listDividaPessoas.add(DividaPessoaTest.getDividaPessoaDevedora());

    divida.setDividaPessoas(listDividaPessoas);
    divida.setDebitos(DebitoTest.get_debitos_list());
    divida.setNumeroInscricao("2024000000017");
    divida.setTipoDocumento(TipoDocumentoTest.getTipoDocumento());
    divida.setDataDeclaracaoAusenciaCorresponsaveis(LocalDateTime.now());
    divida.setFasesDivida(Arrays.asList(FaseDividaTest.getFaseDivida(), FaseDividaTest.getFaseDividaAnterior()));
    divida.setAcoesJudiciais(new ArrayList<>(Arrays.asList(AcaoJudicialTest.getAcaoJudicial())));

    return divida;
  }

  public static Divida getDividaSemVaraOrigem() {
    Divida divida = getDivida();
    divida.setVaraOrigem(null);
    return divida;
  }

  public static Divida getDividaPessoasUpdated() {
    Divida divida = getDivida();
    List<DividaPessoa> listDividaPessoas = new ArrayList<>();
    listDividaPessoas.add(DividaPessoaTest.getDividaPessoaDevedora());
    listDividaPessoas.add(DividaPessoaTest.getDividaPessoaCorresponsavel());
    listDividaPessoas.add(DividaPessoaTest.getDividaPessoaSucessora());

    divida.setDividaPessoas(listDividaPessoas);
    return divida;
  }

  public static Divida getDividaComAcoesJudiciais() {
    Divida divida = getDivida();
    divida.setAcoesJudiciais(new ArrayList<>(Arrays.asList(AcaoJudicialTest.getAcaoJudicial())));
    return divida;
  }

  public static Divida getDividaReponse() {
    Divida divida = new Divida();
    divida.setId(UUID.fromString("e93fd062-c46e-4dfa-ad87-67ab0300dd37"));
    divida.setIdTipoReceita(UUID.fromString("186ca579-d170-4f63-a2f8-bc8a2f3cda7a"));
    divida.setIdOrigemDebito(UUID.fromString("6a711477-05c5-4f8f-ab19-9d6bb210d7eb"));
    divida.setIdProdutoServico(UUID.randomUUID());
    divida.setDisposicoesLegais("Disposições legais");
    divida.setNaturezaFundamentacao("Natureza fundamentação");
    divida.setInexistenciaCausaSuspensivas("Inexistência de causa suspensiva");
    divida.setNumeroDocumento("1234567890");
    divida.setDataDocumento(LocalDate.parse("2024-03-30"));
    divida.setTermoRevelia("Termo de revelia");
    divida.setDataTermoRevelia(LocalDate.parse("2024-03-31"));
    divida.setNumeroOficio("12345");
    divida.setNumeroProcessoAdministrativo("98765432109876543210");
    divida.setDataProcesso(LocalDate.parse("2024-04-01"));
    divida.setNumeroAcordao("54321");
    divida.setDataTransitoJulgado(LocalDate.parse("2024-04-02"));
    divida.setDataConstituicaoDefinitivaCredito(LocalDate.now());
    divida.setPlacaVeiculo("ABC1234");
    divida.setNumeroChassi("1HGBH41JXMN109186");
    divida.setGuiaItcd("ITCD1234");
    divida.setSequencialParcelamento("001");
    divida.setProtocoloJudicial("PJ1234");
    divida.setVaraOrigem(VaraOrigemTest.get_vara_origem());
    divida.setTipoProcesso(TipoProcessoTest.get_tipo_processo());
    divida.setTipoDocumento(TipoDocumentoTest.getTipoDocumento());
    divida.setDividaPessoas(Arrays.asList(DividaPessoaTest.getDividaPessoaDevedora()));
    divida.setDebitos(DebitoTest.get_debitos_list());
    divida.setNumeroInscricao("2024000000017");
    divida.setTipoDocumento(TipoDocumentoTest.getTipoDocumento());
    divida.setDataDeclaracaoAusenciaCorresponsaveis(LocalDateTime.now());

    return divida;
  }

  public static List<Divida> get_dividas_list() {
    List<Divida> dividas = new ArrayList<>();
    dividas.add(getDivida());
    return dividas;
  }

  @Test
  void teste_divida() {
    Divida divida = getDivida();
    assertNotNull(divida.getId());
    assertNotNull(divida.getIdTipoReceita());
    assertNotNull(divida.getIdOrigemDebito());
    assertNotNull(divida.getIdProdutoServico());
    assertEquals("Disposições legais", divida.getDisposicoesLegais());
    assertEquals("Natureza fundamentação", divida.getNaturezaFundamentacao());
    assertEquals("Inexistência de causa suspensiva", divida.getInexistenciaCausaSuspensivas());
    assertEquals("1234567890", divida.getNumeroDocumento());
    assertEquals(LocalDate.parse("2024-03-30"), divida.getDataDocumento());
    assertEquals("Termo de revelia", divida.getTermoRevelia());
    assertEquals(LocalDate.parse("2024-03-31"), divida.getDataTermoRevelia());
    assertEquals("12345", divida.getNumeroOficio());
    assertEquals("98765432109876543210", divida.getNumeroProcessoAdministrativo());
    assertEquals(LocalDate.parse("2024-04-01"), divida.getDataProcesso());
    assertEquals("54321", divida.getNumeroAcordao());
    assertEquals(LocalDate.parse("2024-04-02"), divida.getDataTransitoJulgado());
    assertNotNull(divida.getDataConstituicaoDefinitivaCredito());
    assertEquals("ABC1234", divida.getPlacaVeiculo());
    assertEquals("1HGBH41JXMN109186", divida.getNumeroChassi());
    assertEquals("ITCD1234", divida.getGuiaItcd());
    assertEquals("001", divida.getSequencialParcelamento());
    assertEquals("PJ1234", divida.getProtocoloJudicial());
    assertNotNull(divida.getVaraOrigem());
    assertNotNull(divida.getTipoProcesso());
    assertNotNull(divida.getDataDeclaracaoAusenciaCorresponsaveis());
    assertNotNull(divida.getDividaPessoas());
    assertNotNull(divida.getDebitos());
    assertEquals("2024000000017", divida.getNumeroInscricao());
    assertNotNull(divida.getTipoDocumento());
  }

  @Test
  void dividaDevePossuirSucessorQuandoDocumentoSucessorNaoNulo() {
    Divida divida = DividaTest.getDividaPessoasUpdated();
    divida.getDividaPessoas().forEach(dividaPessoa -> dividaPessoa.setPessoa(PessoaTest.getPessoaSucessora()));
    assertTrue(divida.possiuSucessor());
  }

  @Test
  void dividaNaoDevePossuirSucessorQuandoDocumentoSucessorNulo() {
    Divida divida = DividaTest.getDivida();
    divida.getDividaPessoas().forEach(dividaPessoa -> dividaPessoa.setPessoa(PessoaTest.getPessoaDevedora()));
    assertFalse(divida.possiuSucessor());
  }

  @Test
  void dividaDevePossuirDevedorQuandoDocumentoDevedorNaoNulo() {
    Divida divida = DividaTest.getDivida();
    divida.getDividaPessoas().forEach(dividaPessoa -> dividaPessoa.setPessoa(PessoaTest.getPessoaDevedora()));
    assertTrue(divida.possiuDevedor());
  }

  @Test
  void deveRetornarFaseAtualQuandoExistirFaseAtual() {
    Divida divida = DividaTest.getDivida();
    assertTrue(divida.faseDividaAtual().isPresent());
  }

  @Test
  void naoDeveRetornarFaseAtualQuandoNaoExistirFaseAtual() {
    Divida divida = DividaTest.getDivida();
    divida.getFasesDivida().forEach(fase -> fase.setFaseAtual(false));
    assertFalse(divida.faseDividaAtual().isPresent());
  }
}
