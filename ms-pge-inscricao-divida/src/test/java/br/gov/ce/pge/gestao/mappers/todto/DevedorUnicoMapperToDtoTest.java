package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.request.DevedorUnicoRequestDto;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.StatusDebito;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DevedorUnicoMapperToDtoTest {

  @Test
  void teste_converter() {
    Divida divida = new Divida();
    Debito debito = new Debito();
    StatusDebito status = new StatusDebito();
    status.setId(UUID.randomUUID());
    debito.setStatus(status);
    debito.setReferenciaInicial("202001");
    debito.setReferenciaFinal("202012");
    debito.setValorPrincipal(new BigDecimal("100.00"));
    debito.setValorMulta(new BigDecimal("10.00"));
    debito.setDataVencimento(LocalDate.of(2020, 12, 31));
    debito.setDataConstituicaoDefinitivaCredito(LocalDate.of(2020, 1, 1));
    debito.setDataInicioAtualizacaoMonetaria(LocalDate.of(2020, 1, 2));
    divida.setDebitos(Arrays.asList(debito));

    List<String> documentos = Arrays.asList("12345678901", "09876543210");

    DevedorUnicoRequestDto result = DevedorUnicoMapperToDto.converter(divida, documentos);

    assertNotNull(result);
    assertEquals(documentos, result.getDocumentos());
    assertEquals(status.getId().toString(), result.getStatusDebitos().get(0));
    assertEquals("202001", result.getReferenciasIniciaisDebitos().get(0));
    assertEquals("202012", result.getReferenciasFinaisDebitos().get(0));
    assertEquals(new BigDecimal("100.00"), result.getValoresPrincipaisDebitos().get(0));
    assertEquals(new BigDecimal("10.00"), result.getValoresMultasDebitos().get(0));
    assertEquals(LocalDate.of(2020, 12, 31), result.getDatasVencimentosDebitos().get(0));
    assertEquals(LocalDate.of(2020, 1, 1), result.getDatasConstituicoesDefinitivasDebitos().get(0));
    assertEquals(LocalDate.of(2020, 1, 2), result.getDatasAtualizacaoMonetariaDebitos().get(0));
  }
}
