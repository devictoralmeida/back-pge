package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.OrigemDebitoFeingClient;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDtoTest;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrigemDebitoServiceImplTest {
  @Mock
  private OrigemDebitoFeingClient feignClient;

  @InjectMocks
  private OrigemDebitoServiceImpl service;

  @Value("${ms.service.gestao}")
  private String baseUrl;

  @Test
  void test_find_by_id_success() throws JsonProcessingException {
    var expectedDto = OrigemDebitoResponseDtoTest.getOrigem();
    ResponseClientDto<OrigemDebitoResponseDto> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Origem de débito encontrada");
    ResponseEntity<ResponseClientDto<OrigemDebitoResponseDto>> responseEntity = ResponseEntity.ok(responseDto);

    doReturn(responseEntity).when(feignClient).findById(expectedDto.getId());

    OrigemDebitoResponseDto actualDto = service.findById(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));

    assertNotNull(actualDto);
    assertEquals(expectedDto, actualDto);
  }

  @Test
  void test_find_by_id_exception() throws IOException {
    UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac299");
    Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/origem-debito/" + id,
            new HashMap<>(), null, new RequestTemplate());

    final String errorMessage = "{\"mensagem\":\"Origem débito não encontrada.\"}";
    byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
    Response response = Response.builder()
            .status(404)
            .reason("Not Found")
            .request(request)
            .body(body)
            .build();

    FeignException feignException = new FeignException.NotFound(
            "Origem débito não encontrada.", request, response.body().asInputStream().readAllBytes(), null);

    when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      service.findById(id);
    });

    assertEquals("Origem débito não encontrada.", exception.getMessage());
  }

  @Test
  void test_find_by_id_exception_bad_request() throws IOException {
    UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac299");
    Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/origem-debito/" + id,
            new HashMap<>(), null, new RequestTemplate());

    final String errorMessage = "{\"mensagem\":\"Origem débito não encontrada.\"}";
    byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
    Response response = Response.builder()
            .status(400)
            .reason("Bad request")
            .request(request)
            .body(body)
            .build();

    FeignException feignException = new FeignException.BadRequest(
            "Origem débito não encontrada.", request, response.body().asInputStream().readAllBytes(), null);

    when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      service.findById(id);
    });

    assertEquals("Origem débito não encontrada.", exception.getMessage());
  }

  @Test
  void test_get_message_erro() {
    final String errorMessage = "{\"mensagem\":\"Origem débito não encontrada.\"}";
    String extractedMessage = service.getMessageErro(errorMessage);
    assertEquals("Origem débito não encontrada.", extractedMessage);
  }

}
