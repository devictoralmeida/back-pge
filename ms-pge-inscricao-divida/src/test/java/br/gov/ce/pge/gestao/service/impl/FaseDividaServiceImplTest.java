package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.client.FaseDividaFeingClient;
import br.gov.ce.pge.gestao.dto.response.*;
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
public class FaseDividaServiceImplTest {

    @Mock
    private FaseDividaFeingClient feignClient;

    @InjectMocks
    private FaseDividaServiceImpl service;

    @Value("${ms.service.gestao}")
    private String baseUrl;

    @Test
    void test_find_by_id_success() {
        var expectedDto = FaseDividaResponseDtoTest.getResponse();
        UUID id = UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a");

        ResponseClientDto<FaseDividaResponseDto> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Fase da dívida encontrada");

        ResponseEntity<ResponseClientDto<FaseDividaResponseDto>> responseEntity = ResponseEntity.ok(responseDto);

        doReturn(responseEntity).when(feignClient).findById(expectedDto.getId());

        FaseDividaResponseDto actualDto = service.findById(id);

        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void test_find_all_success() {
        var expectedDto = FaseDividaResponseDtoTest.getList();

        ResponseClientDto<List<FaseDividaResponseDto>> responseDto = ResponseClientDto.fromData(expectedDto, HttpStatus.OK.value(), "Fase da dívida encontrada");

        ResponseEntity<ResponseClientDto<List<FaseDividaResponseDto>>> responseEntity = ResponseEntity.ok(responseDto);

        doReturn(responseEntity).when(feignClient).findAll();

        List<FaseDividaResponseDto> all = service.findAll();

        assertNotNull(all);
        assertEquals(expectedDto, all);
    }

    @Test
    void test_find_by_id_bad_request_exception() throws IOException {
        UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212");
        Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/fase-divida/" + id,
                new HashMap<>(), null, new RequestTemplate());

        final String errorMessage = "{\"mensagem\":\"Fase da dívida não encontrada.\"}";
        byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
        Response response = Response.builder()
                .status(400)
                .reason("Bad request")
                .request(request)
                .body(body)
                .build();

        FeignException feignException = new FeignException.BadRequest(
                "Fase da dívida não encontrada.", request, response.body().asInputStream().readAllBytes(), null);

        when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            service.findById(id);
        });

        assertEquals("Fase da dívida não encontrada.", exception.getMessage());
    }

    @Test
    void test_find_by_id_exception() throws IOException {
        UUID id = UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac212");
        Request request = Request.create(Request.HttpMethod.GET, baseUrl + "/fase-divida/" + id,
                new HashMap<>(), null, new RequestTemplate());

        final String errorMessage = "{\"mensagem\":\"Fase da dívida não encontrada.\"}";
        byte[] body = errorMessage.getBytes(StandardCharsets.UTF_8);
        Response response = Response.builder()
                .status(404)
                .reason("Not Found")
                .request(request)
                .body(body)
                .build();

        FeignException feignException = new FeignException.NotFound(
                "Fase da dívida não encontrada.", request, response.body().asInputStream().readAllBytes(), null);

        when(feignClient.findById(any(UUID.class))).thenThrow(feignException);

        NegocioException exception = assertThrows(NegocioException.class, () -> {
            service.findById(id);
        });

        assertEquals("Fase da dívida não encontrada.", exception.getMessage());
    }

    @Test
    void test_get_message_erro() {
        final String errorMessage = "{\"mensagem\":\"Erro esperado para teste.\"}";
        String extractedMessage = service.getMessageErro(errorMessage);

        assertEquals("Erro esperado para teste.", extractedMessage);
    }

}
