package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.DebitoTest;
import br.gov.ce.pge.gestao.entity.DividaTest;
import br.gov.ce.pge.gestao.mappers.todto.DividaUnicaMapperToDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DividaUnicaRequestDtoTest {
  public static DividaUnicaRequestDto getFilters() {
    return DividaUnicaMapperToDto.converter(DividaTest.getDivida(), List.of(PessoaRequestDtoTest.getPessoaDevedora().getDocumento()));
  }

  @Test
  void teste_converter() {
    DividaUnicaRequestDto dto = getFilters();
    Map<String, Object> filters = dto.filters();

    String idTipoPapelPessoaDevedora = IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR.toString();
    List<String> documentosDevedores = List.of(PessoaRequestDtoTest.getPessoaDevedora().getDocumento());
    String origemDebito = DividaTest.getDivida().getIdOrigemDebito().toString();
    String tipoReceita = DividaTest.getDivida().getIdTipoReceita().toString();
    List<String> referenciasIniciaisDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getReferenciaInicial).toList();
    List<String> referenciasFinaisDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getReferenciaFinal).toList();
    List<BigDecimal> valoresPrincipaisDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getValorPrincipal).toList();
    List<BigDecimal> valoresMultasDebitos = DebitoTest.get_debitos_list().stream().map(Debito::getValorMulta).toList();

    assertEquals(idTipoPapelPessoaDevedora, filters.get("idTipoPapelPessoaDevedora"));
    assertEquals(documentosDevedores, filters.get("documentosDevedores"));
    assertEquals(origemDebito, filters.get("origemDebito"));
    assertEquals(tipoReceita, filters.get("tipoReceita"));
    assertEquals(referenciasIniciaisDebitos, filters.get("referenciasIniciaisDebitos"));
    assertEquals(referenciasFinaisDebitos, filters.get("referenciasFinaisDebitos"));
    assertEquals(valoresPrincipaisDebitos, filters.get("valoresPrincipaisDebitos"));
    assertEquals(valoresMultasDebitos, filters.get("valoresMultasDebitos"));

  }

}
