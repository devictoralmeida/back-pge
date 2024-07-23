package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.ServicosConfig;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.DebitoRequestDto;
import br.gov.ce.pge.gestao.dto.request.DebitoRequestDtoTest;
import br.gov.ce.pge.gestao.entity.Debito;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.StatusDebitoTest;
import br.gov.ce.pge.gestao.service.StatusDebitoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DebitoMapperToModelTest {

  private ServicosConfig servicosConfig;
  private StatusDebitoService statusDebitoService;

  @BeforeEach
  void setUp() {
    servicosConfig = Mockito.mock(ServicosConfig.class);
    statusDebitoService = Mockito.mock(StatusDebitoService.class);
    when(servicosConfig.getStatusDebitoService()).thenReturn(statusDebitoService);
  }

  @Test
  void teste_converter_list_sucesso() {
    List<DebitoRequestDto> requestList = DebitoRequestDtoTest.getDebitosList();
    Divida divida = new Divida();

    when(statusDebitoService.findById(requestList.get(0).getIdStatusDebito())).thenReturn(StatusDebitoTest.getStatusDebito());
    when(statusDebitoService.findById(requestList.get(1).getIdStatusDebito())).thenReturn(StatusDebitoTest.getStatusDebito());

    List<Debito> debitos = DebitoMapperToModel.converterList(requestList, divida, servicosConfig);

    assertEquals(2, debitos.size());
    assertEquals("01/2024", debitos.get(0).getReferenciaInicial());
    assertEquals("03/2024", debitos.get(0).getReferenciaFinal());
    assertEquals(LocalDate.parse("2024-03-29"), debitos.get(0).getDataVencimento());
    assertEquals(LocalDate.parse("2024-03-30"), debitos.get(0).getDataConstituicaoDefinitivaCredito());
    assertEquals(BigDecimal.valueOf(150), debitos.get(0).getValorPrincipal());
    assertEquals(BigDecimal.valueOf(50), debitos.get(0).getValorMulta());
    assertEquals(LocalDate.parse("2024-03-25"), debitos.get(0).getDataInicioAtualizacaoMonetaria());
    assertNotNull(debitos.get(0).getStatus());
    assertNotNull(debitos.get(1).getStatus());
  }

  @Test
  void teste_converter_sucesso() {
    DebitoRequestDto requestDto = DebitoRequestDtoTest.getDebito();
    Divida divida = new Divida();

    when(statusDebitoService.findById(any(UUID.class))).thenReturn(StatusDebitoTest.getStatusDebito());

    Debito debito = DebitoMapperToModel.converter(requestDto, divida, servicosConfig);

    assertNotNull(debito);
    assertEquals("01/2024", debito.getReferenciaInicial());
    assertEquals("03/2024", debito.getReferenciaFinal());
    assertEquals(LocalDate.parse("2024-03-29"), debito.getDataVencimento());
    assertEquals(LocalDate.parse("2024-03-30"), debito.getDataConstituicaoDefinitivaCredito());
    assertEquals(BigDecimal.valueOf(150), debito.getValorPrincipal());
    assertEquals(BigDecimal.valueOf(50), debito.getValorMulta());
    assertEquals(LocalDate.parse("2024-03-25"), debito.getDataInicioAtualizacaoMonetaria());
    assertNotNull(debito.getStatus());
  }

  @Test
  void teste_exception_referencia_inicial_apos_data_atual() {
    DebitoRequestDto requestDto = DebitoRequestDtoTest.getDebito();
    requestDto.setReferenciaInicial("01/2099");
    requestDto.setReferenciaFinal("12/2099");
    Divida divida = new Divida();

    Exception exception = assertThrows(NegocioException.class, () -> DebitoMapperToModel.converter(requestDto, divida, servicosConfig));

    assertEquals(MessageCommonsConstants.MSG_ERRO_REFERENCIA_INICIAL_SUPERIOR_DATA_ATUAL, exception.getMessage());
  }

  @Test
  void teste_exception_referencia_final_anterior_referencia_inicial() {
    DebitoRequestDto requestDto = DebitoRequestDtoTest.getDebito();
    requestDto.setReferenciaInicial("12/2020");
    requestDto.setReferenciaFinal("01/2020");
    Divida divida = new Divida();

    Exception exception = assertThrows(NegocioException.class, () -> DebitoMapperToModel.converter(requestDto, divida, servicosConfig));

    assertEquals(MessageCommonsConstants.MSG_ERRO_REFERENCIA_FINAL_ANTERIOR_INICIAL, exception.getMessage());
  }

  @Test
  void teste_exception_valores_principal_multa_null() {
    DebitoRequestDto requestDto = DebitoRequestDtoTest.getDebito();
    requestDto.setValorPrincipal(null);
    requestDto.setValorMulta(null);
    Divida divida = new Divida();

    assertThrows(NegocioException.class, () -> DebitoMapperToModel.converter(requestDto, divida, servicosConfig));
  }
}
