package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoDividaTest;
import br.gov.ce.pge.gestao.entity.TipoDocumentoTest;
import br.gov.ce.pge.gestao.entity.TipoProcessoTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DividaUpdateRequestDtoTest {
  public static DividaUpdateRequestDto getDividaUpdateRequestDto() {
    DividaUpdateRequestDto dto = new DividaUpdateRequestDto();
    dto.setId(UUID.fromString("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));
    dto.setIdOrigemDebito(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));
    dto.setIdTipoReceita(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"));
    dto.setIdProdutoServico(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212"));
    dto.setDisposicoesLegais("teste 2");
    dto.setNaturezaFundamentacao("teste");
    dto.setInexistenciaCausaSuspensivas("teste");
    dto.setNumeroDocumento("1234567890");
    dto.setDataDocumento(LocalDate.parse("2024-03-20"));
    dto.setTermoRevelia("123456789");
    dto.setDataTermoRevelia(LocalDate.parse("2024-03-20"));
    dto.setNumeroOficio("123456789");
    dto.setNumeroProcessoAdministrativo("98765432109876543210");
    dto.setDataProcesso(LocalDate.parse("2024-03-20"));
    dto.setNumeroAcordao("6789");
    dto.setDataTransitoJulgado(LocalDate.parse("2024-03-20"));
    dto.setPlacaVeiculo(null);
    dto.setNumeroChassi(null);
    dto.setSequencialParcelamento(null);
    dto.setProtocoloJudicial(null);
    dto.setTipoProcesso("TipoProcesso teste");
    dto.setPessoas(PessoaRequestDtoTest.getPessoasList());
    dto.setDebitos(DebitoRequestDtoTest.getDebitosList());
    dto.setMotivo(MotivoAtualizacaoDividaTest.getMotivoAtualizacao().getMotivo());
    return dto;
  }

  public static DividaUpdateRequestDto getDividaIgual() {
    DividaUpdateRequestDto dto = new DividaUpdateRequestDto();
    dto.setIdTipoReceita(UUID.fromString("186ca579-d170-4f63-a2f8-bc8a2f3cda7a"));
    dto.setIdOrigemDebito(UUID.fromString("6a711477-05c5-4f8f-ab19-9d6bb210d7eb"));
    dto.setIdProdutoServico(UUID.fromString("227940ef-cd0f-4cde-9a94-ffb01bc9a724"));
    dto.setDisposicoesLegais("Disposições legais");
    dto.setNaturezaFundamentacao("Natureza fundamentação");
    dto.setInexistenciaCausaSuspensivas("Inexistência de causa suspensiva");
    dto.setNumeroDocumento("1234567890");
    dto.setDataDocumento(LocalDate.parse("2024-03-30"));
    dto.setTermoRevelia("Termo de revelia");
    dto.setDataTermoRevelia(LocalDate.parse("2024-03-31"));
    dto.setNumeroOficio("12345");
    dto.setNumeroProcessoAdministrativo("98765432109876543210");
    dto.setDataProcesso(LocalDate.parse("2024-04-01"));
    dto.setNumeroAcordao("54321");
    dto.setDataTransitoJulgado(LocalDate.parse("2024-04-02"));
    dto.setDataConstituicaoDefinitivaCredito(LocalDate.now());
    dto.setPlacaVeiculo("ABC1234");
    dto.setNumeroChassi("1HGBH41JXMN109186");
    dto.setGuiaItcd("ITCD1234");
    dto.setSequencialParcelamento("001");
    dto.setProtocoloJudicial("PJ1234");
    dto.setTipoProcesso(TipoProcessoTest.get_tipo_processo().getNome());
    dto.setIdTipoDocumento(TipoDocumentoTest.getTipoDocumento().getId());
    dto.setDeclaracaoAusenciaCorresponsaveis(true);


    dto.setDebitos(DebitoRequestDtoTest.getDebitosList());
    return dto;
  }

  @Test
  void teste_divida() {
    DividaUpdateRequestDto dto = getDividaUpdateRequestDto();
    assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), dto.getIdOrigemDebito());
    assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), dto.getIdTipoReceita());
    assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212"), dto.getIdProdutoServico());
    assertEquals("teste 2", dto.getDisposicoesLegais());
    assertEquals("teste", dto.getInexistenciaCausaSuspensivas());
    assertEquals("1234567890", dto.getNumeroDocumento());
    assertEquals(LocalDate.parse("2024-03-20"), dto.getDataDocumento());
    assertEquals("123456789", dto.getTermoRevelia());
    assertEquals(LocalDate.parse("2024-03-20"), dto.getDataTermoRevelia());
    assertEquals("123456789", dto.getNumeroOficio());
    assertEquals("98765432109876543210", dto.getNumeroProcessoAdministrativo());
    assertEquals(LocalDate.parse("2024-03-20"), dto.getDataProcesso());
    assertEquals("6789", dto.getNumeroAcordao());
    assertEquals(LocalDate.parse("2024-03-20"), dto.getDataTransitoJulgado());
    assertEquals("TipoProcesso teste", dto.getTipoProcesso());
    assertNull(dto.getPlacaVeiculo());
    assertNull(dto.getNumeroChassi());
    assertNull(dto.getSequencialParcelamento());
    assertNull(dto.getProtocoloJudicial());
    assertNull(dto.getGuiaItcd());
    assertNull(dto.getNomeAnexo());
    assertNull(dto.getIdAcaoJudicial());
    assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", dto.getMotivo());
  }
}
