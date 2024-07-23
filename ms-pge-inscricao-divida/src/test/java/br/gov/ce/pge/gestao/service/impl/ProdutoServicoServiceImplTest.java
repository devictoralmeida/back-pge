package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.ProdutoServicoFeingClient;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDtoTest;
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
class ProdutoServicoServiceImplTest {
  @Mock
  private ProdutoServicoFeingClient feignClient;

  @InjectMocks
  private ProdutoServicoServiceImpl service;

  @Value("${ms.service.gestao}")
  private String baseUrl;

  @Test
  void test_find_by_id_success() throws JsonProcessingException {
    ProdutoServicoResponseDto expectedDto = ProdutoServicoResponseDtoTest.getProdutoServico();
    ResponseClientDto<ProdutoServicoResponseDto> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Produto/Serviço encontrado");
    ResponseEntity<ResponseClientDto<ProdutoServicoResponseDto>> responseEntity = ResponseEntity.ok(responseDto);

    doReturn(responseEntity).when(feignClient).findById(expectedDto.getId());

    ProdutoServicoResponseDto actualDto = service.findById(UUID.fromString("a9e28442-86c7-4f05-b0e2-55835191a27a"));

    assertNotNull(actualDto);
    assertEquals(expectedDto, actualDto);
  }

  @Test
  void test_find_by_id_exception() throws IOException {
    UUID id = UUID.fromString("a9e28442-86c7-4f05-b0e2-55835191a27a");
    Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/produto-servico/" + id,
            new HashMap<>(), null, new RequestTemplate());

    final String errorMessage = "{\"mensagem\":\"Produto/Serviço não encontrado.\"}";
    byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
    Response response = Response.builder()
            .status(404)
            .reason("Not Found")
            .request(request)
            .body(body)
            .build();

    FeignException feignException = new FeignException.NotFound(
            "Produto/Serviço não encontrado.", request, response.body().asInputStream().readAllBytes(), null);

    when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      service.findById(id);
    });

    assertEquals("Produto/Serviço não encontrado.", exception.getMessage());
  }

  @Test
  void test_find_by_id_exception_bad_request() throws IOException {
    UUID id = UUID.fromString("a9e28442-86c7-4f05-b0e2-55835191a27a");
    Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/produto-servico/" + id,
            new HashMap<>(), null, new RequestTemplate());

    final String errorMessage = "{\"mensagem\":\"Produto/Serviço não encontrado.\"}";
    byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
    Response response = Response.builder()
            .status(400)
            .reason("Bad request")
            .request(request)
            .body(body)
            .build();

    FeignException feignException = new FeignException.BadRequest(
            "Produto/Serviço não encontrado.", request, response.body().asInputStream().readAllBytes(), null);

    when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

    NegocioException exception = assertThrows(NegocioException.class, () -> {
      service.findById(id);
    });

    assertEquals("Produto/Serviço não encontrado.", exception.getMessage());
  }

  @Test
  void test_get_message_erro() {
    final String errorMessage = "{\"mensagem\":\"Produto/Serviço não encontrado.\"}";
    String extractedMessage = service.getMessageErro(errorMessage);
    assertEquals("Produto/Serviço não encontrado.", extractedMessage);
  }
}
