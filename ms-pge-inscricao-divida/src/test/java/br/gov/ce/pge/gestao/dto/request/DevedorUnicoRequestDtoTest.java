package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.DebitoTest;
import br.gov.ce.pge.gestao.entity.DividaTest;
import br.gov.ce.pge.gestao.mappers.todto.DevedorUnicoMapperToDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevedorUnicoRequestDtoTest {
  public static DevedorUnicoRequestDto getFilters() {
    return DevedorUnicoMapperToDto.converter(DividaTest.getDivida(), List.of(PessoaRequestDtoTest.getPessoaDevedora().getDocumento(), PessoaRequestDtoTest.getPessoaCorresponsavel().getDocumento()));
  }


  @Test
  void teste_converter() {
    DevedorUnicoRequestDto dto = getFilters();
    Map<String, Object> filters = dto.filters();

    List<String> documentos = List.of(PessoaRequestDtoTest.getPessoaDevedora().getDocumento(), PessoaRequestDtoTest.getPessoaCorresponsavel().getDocumento());
    List<String> statusDebitos = DebitoRequestDtoTest.getDebitosList().stream().map(request -> request.getIdStatusDebito().toString()).toList();
    List<String> referenciasIniciaisDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getReferenciaInicial).toList();
    List<String> referenciasFinaisDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getReferenciaFinal).toList();
    List<BigDecimal> valoresPrincipaisDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getValorPrincipal).toList();
    List<BigDecimal> valoresMultasDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getValorMulta).toList();
    List<LocalDate> datasVencimentosDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getDataVencimento).toList();
    List<LocalDate> datasConstituicoesDefinitivasDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getDataConstituicaoDefinitivaCredito).toList();
    List<LocalDate> datasAtualizacaoMonetariaDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getDataInicioAtualizacaoMonetaria).toList();

    assertEquals(documentos, filters.get("documentos"));
    assertEquals(statusDebitos, filters.get("statusDebitos"));
    assertEquals(referenciasIniciaisDebitos, filters.get("referenciasIniciaisDebitos"));
    assertEquals(referenciasFinaisDebitos, filters.get("referenciasFinaisDebitos"));
    assertEquals(valoresPrincipaisDebitos, filters.get("valoresPrincipaisDebitos"));
    assertEquals(valoresMultasDebitos, filters.get("valoresMultasDebitos"));
    assertEquals(datasVencimentosDebitos, filters.get("datasVencimentosDebitos"));
    assertEquals(datasConstituicoesDefinitivasDebitos, filters.get("datasConstituicoesDefinitivasDebitos"));
    assertEquals(datasAtualizacaoMonetariaDebitos, filters.get("datasAtualizacaoMonetariaDebitos"));
  }

}
