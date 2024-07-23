package br.gov.ce.pge.mspgeoauth.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.UUID;

import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
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
    void test_exception_negocio_exception() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.fromData(null, HttpStatus.FORBIDDEN.value(), errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_senha_expirada() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA + UUID.randomUUID();
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.fromData(null, HttpStatus.FORBIDDEN.value(), errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_credenciais_invalidas() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_LOGIN_ERROR;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.fromData(null, HttpStatus.FORBIDDEN.value(), errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_tentativas_invalidas() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_ACESSO_BLOQUEADO;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.fromData(null, HttpStatus.FORBIDDEN.value(), errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_ACESSO_BLOQUEADO, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_inativo_bloqueado() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDto.fromData(null, HttpStatus.FORBIDDEN.value(), errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA, responseEntity.getBody().getMensagem());
    }

}
