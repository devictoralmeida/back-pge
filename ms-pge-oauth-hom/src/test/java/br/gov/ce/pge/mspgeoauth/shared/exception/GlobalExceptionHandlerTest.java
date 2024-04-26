package br.gov.ce.pge.mspgeoauth.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;

@ExtendWith({ MockitoExtension.class })
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_exception_NegocioException() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.fromData(null, HttpStatus.FORBIDDEN.value(), errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());
    }

}
