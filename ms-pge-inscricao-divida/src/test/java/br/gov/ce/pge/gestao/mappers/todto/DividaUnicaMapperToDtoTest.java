package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.request.DividaUnicaRequestDto;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Divida;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DividaUnicaMapperToDtoTest {

  @Test
  void teste_converter() {
    Divida divida = new Divida();
    divida.setIdOrigemDebito(UUID.randomUUID());
    divida.setIdTipoReceita(UUID.randomUUID());

    Debito debito = new Debito();
    debito.setReferenciaInicial("202001");
    debito.setReferenciaFinal("202012");
    debito.setValorPrincipal(new BigDecimal("100.00"));
    debito.setValorMulta(new BigDecimal("10.00"));
    divida.setDebitos(Arrays.asList(debito));

    List<String> documentosDevedores = Arrays.asList("12345678901");

    DividaUnicaRequestDto result = DividaUnicaMapperToDto.converter(divida, documentosDevedores);

    assertNotNull(result);
    assertEquals(divida.getIdOrigemDebito().toString(), result.getOrigemDebito());
    assertEquals(divida.getIdTipoReceita().toString(), result.getTipoReceita());
    assertEquals(documentosDevedores, result.getDocumentosDevedores());
    assertEquals(Arrays.asList("202001"), result.getReferenciasIniciaisDebitos());
    assertEquals(Arrays.asList("202012"), result.getReferenciasFinaisDebitos());
    assertEquals(Arrays.asList(new BigDecimal("100.00")), result.getValoresPrincipaisDebitos());
    assertEquals(Arrays.asList(new BigDecimal("10.00")), result.getValoresMultasDebitos());
  }
}
