package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.TipoReceitaFeingClient;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDtoTest;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoReceitaServiceImplTest {
  @Mock
  private TipoReceitaFeingClient feignClient;

  @InjectMocks
  private TipoReceitaServiceImpl service;

  @Value("${ms.service.gestao}")
  private String baseUrl;

  @Test
  void test_find_by_id_success() {
    var expectedDto = TipoReceitaResponseDtoTest.getTipoReceitaTributaria();
    UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211");

    ResponseClientDto<TipoReceitaResponseDto> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Tipo de receita encontrada");

    ResponseEntity<ResponseClientDto<TipoReceitaResponseDto>> responseEntity = ResponseEntity.ok(responseDto);

    doReturn(responseEntity).when(feignClient).findById(expectedDto.getId());

    TipoReceitaResponseDto actualDto = service.findById(id);

    assertNotNull(actualDto);
    assertEquals(expectedDto, actualDto);
  }

  @Test
  void test_find_all_success() {
    var expectedDto = TipoReceitaResponseDtoTest.getList();

    ResponseClientDto<List<TipoReceitaResponseDto>> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Tipo de receita encontrada");

    ResponseEntity<ResponseClientDto<List<TipoReceitaResponseDto>>> responseEntity = ResponseEntity.ok(responseDto);

    doReturn(responseEntity).when(feignClient).findAll();

    List<TipoReceitaResponseDto> all = service.findAll();

    assertNotNull(all);
    assertEquals(expectedDto, all);
  }

  @Test
  void test_find_by_id_exception() throws IOException {
    UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212");
    Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/tipo-receita/" + id,
            new HashMap<>(), null, new RequestTemplate());

    final String errorMessage = "{\"mensagem\":\"Tipo de Receita não encontrada.\"}";
    byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
    Response response = Response.builder()
            .status(404)
            .reason("Not Found")
            .request(request)
            .body(body)
            .build();

    FeignException feignException = new FeignException.NotFound(
            "Tipo de Receita não encontrada.", request, response.body().asInputStream().readAllBytes(), null);

    when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      service.findById(id);
    });

    assertEquals("Tipo de Receita não encontrada.", exception.getMessage());
  }

  @Test
  void test_find_by_id_bad_request_exception() throws IOException {
    UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212");
    Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/tipo-receita/" + id,
            new HashMap<>(), null, new RequestTemplate());

    final String errorMessage = "{\"mensagem\":\"Tipo de Receita não encontrada.\"}";
    byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
    Response response = Response.builder()
            .status(400)
            .reason("Bad request")
            .request(request)
            .body(body)
            .build();

    FeignException feignException = new FeignException.BadRequest(
            "Tipo de Receita não encontrada.", request, response.body().asInputStream().readAllBytes(), null);

    when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      service.findById(id);
    });

    assertEquals("Tipo de Receita não encontrada.", exception.getMessage());
  }

  @Test
  void test_get_message_erro() {
    final String errorMessage = "{\"mensagem\":\"Erro esperado para teste.\"}";
    String extractedMessage = service.getMessageErro(errorMessage);

    assertEquals("Erro esperado para teste.", extractedMessage);
  }

}
