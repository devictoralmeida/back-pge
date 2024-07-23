package br.gov.ce.pge.gestao.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.enums.Natureza;

@ExtendWith({ MockitoExtension.class })
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private WebRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_exception_method_argument_not_validException() {
       
        WebRequest request = mock(WebRequest.class);
        HttpHeaders headers = new HttpHeaders();

       
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = Arrays.asList(
                new FieldError("objectName", "field1", "Erro no campo 1"),
                new FieldError("objectName", "field2", "Erro no campo 2")
        );
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

       
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValid(ex, headers, HttpStatus.BAD_REQUEST, request);

        ResponseEntity<Object> expectedResponseEntity = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, "Favor verifique todos os campos com validação", Arrays.asList("Erro no campo 1", "Erro no campo 2")));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
    }
    
    
    @Test
    void test_exception_negocio_exception() {
        
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());
        
    }

    @Test
    void test_exception_negocio_exception_email_existente() {

        NegocioException ex = mock(NegocioException.class);
        String errorMessage = "email já existe";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem().replace("email", "registro"), responseEntity.getBody().getMensagem());

    }

    @Test
    void test_exception_negocio_exception_cpf_existente() {

        NegocioException ex = mock(NegocioException.class);
        String errorMessage = "CPF já existe";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem().replace("CPF", "registro"), responseEntity.getBody().getMensagem());

    }

    @Test
    void test_exception_negocio_exception_usuario_rede_existente() {

        NegocioException ex = mock(NegocioException.class);
        String errorMessage = "usuário de rede já existe";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem().replace("usuário de rede", "registro"), responseEntity.getBody().getMensagem());

    }
    
    @Test
    void test_exception_historico_atualizacao_not_found_exception() {
        
    	HistoricoAtualizacaoNotFoundException ex = mock(HistoricoAtualizacaoNotFoundException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleHistoricoAtualizacaoNotFoundException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.fromData(null, HttpStatus.NOT_FOUND, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());
        
    }
    
    
    @Test
    void test_exception_invalid_format_exception() {
        
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        MockHttpServletRequest request = new MockHttpServletRequest();
        ServletWebRequest webRequest = new ServletWebRequest(request);

        InvalidFormatException ife = mock(InvalidFormatException.class);
        when(ex.getRootCause()).thenReturn(ife);
        when(ife.getValue()).thenReturn("ValorInválido");

        when(ife.getTargetType()).thenReturn((Class) Natureza.class);

        var responseEntity = exceptionHandler.handleHttpMessageNotReadable(ex, headers, status, webRequest);

        var expectedErrorMessage = String.format(
                "Valor inválido para o tipo %s: %s. Valores aceitos são: %s",
                Natureza.class.getSimpleName(), "ValorInválido", Arrays.asList(Natureza.values()));
        var expectedResponseEntity = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, expectedErrorMessage);
        assertEquals(expectedResponseEntity.getStatus(), responseEntity.getStatusCodeValue());
    }
    
    
    @Test
    void test_exception_data_integrity_violation_exception_field_name_null() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);
        
        String msgErro = "Mensagem específica aqui - extractFieldName null"; 
        when(ex.getCause()).thenReturn(new RuntimeException(msgErro));

        String expectedErrorMsg = "Violação de integridade: " + msgErro;
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(ex.getMessage()));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }
   
    @Test
    void test_exception_data_integrity_violation_exception_field_cpf() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        when(ex.getCause()).thenReturn(new RuntimeException("campo cpf"));
        when(ex.getMessage()).thenReturn("campo cpf");

        String expectedErrorMsg = "Valor duplicado encontrado para o campo: CPF";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList("campo cpf"));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_data_integrity_violation_exception_field_email() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "campo email";
		when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Valor duplicado encontrado para o campo: EMAIL";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }
    
    @Test
    void test_exception_data_integrity_violation_exception_field_codigo() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "campo codigo";
		when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Valor duplicado encontrado para o campo: CODIGO";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }
    
    
    @Test
    void test_exception_data_integrity_violation_exception_exclusao_permissao() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "fk_tbmodulopermissao_tbpermissao";
		when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Não foi possível realizar a exclusão! A Permissão está sendo utilizada para um Módulo cadastrado.";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }
    
    
    @Test
    void test_exception_data_integrity_violation_exception_exclusao_modulo() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "fk_tbsistemamodulo_tbmodulo";
		when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Não foi possível realizar a exclusão! O Módulo está sendo utilizado para um Sistema cadastrado.";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }
    
    @Test
    void test_exception_data_integrity_violation_exception_exclusao_sistema() {
        
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "fk_tbperfilacessosistema_tbsistema";
		when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Não foi possível realizar a exclusão! O Sistema está sendo utilizado para um Perfil de Acesso cadastrado.";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_codigo_expirado() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_CODIGO_EXPIRADO;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_CODIGO_EXPIRADO, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_email_nao_cadastrado() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_EMAIL_NAO_CADASTRADO;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_EMAIL_NAO_CADASTRADO, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_email_recuperacao_ja_enviado() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_MUITAS_SOLICITACOES;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_MUITAS_SOLICITACOES, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_email_recuperacao_usuario_inativo() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_USUARIO_INATIVO;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVO, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_email_recuperacao_usuario_bloqueado() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_USUARIO_BLOQUEADO;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_USUARIO_BLOQUEADO, responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_negocio_exception_email_recuperacao_usuario_interno() {
        NegocioException ex = mock(NegocioException.class);
        String errorMessage = MessageCommonsContanst.MENSAGEM_USUARIO_INTERNO;
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(MessageCommonsContanst.MENSAGEM_USUARIO_INTERNO, responseEntity.getBody().getMensagem());
    }
}