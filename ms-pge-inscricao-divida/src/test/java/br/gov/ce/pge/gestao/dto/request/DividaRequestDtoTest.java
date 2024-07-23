package br.gov.ce.pge.gestao.dto.request;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class DividaRequestDtoTest {
  public static DividaRequestDto getDivida() {
    DividaRequestDto dto = new DividaRequestDto();
    dto.setId(UUID.fromString("6fa3e795-0474-400c-8ef1-fa80262b3ae4"));
    dto.setIdOrigemDebito(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));
    dto.setIdTipoReceita(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"));
    dto.setIdProdutoServico(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212"));
    dto.setDisposicoesLegais("teste");
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
    return dto;
  }

  @Test
  void teste_divida() {
    DividaRequestDto dto = getDivida();
    assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), dto.getIdOrigemDebito());
    assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), dto.getIdTipoReceita());
    assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212"), dto.getIdProdutoServico());
    assertEquals("teste", dto.getDisposicoesLegais());
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
    assertNull(dto.getGuiaItcd());
    assertNull(dto.getSequencialParcelamento());
    assertNull(dto.getProtocoloJudicial());
    assertNull(dto.getIdTipoDocumento());
    assertEquals(3, dto.getPessoas().size());
    assertEquals(2, dto.getDebitos().size());
  }

}
