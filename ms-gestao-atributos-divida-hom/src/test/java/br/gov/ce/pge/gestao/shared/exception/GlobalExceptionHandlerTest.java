package br.gov.ce.pge.gestao.shared.exception;

import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.enums.Natureza;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void test_exception_method_argument_not_valid_exception() {

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
    void test_exception_no_content_exception() {

        NoContentException ex = mock(NoContentException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNoContentException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

    }

    @Test
    void test_exception_bad_value_exception() {

        BadValueException ex = mock(BadValueException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleBadValueException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

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
    void test_exception_produto_servico_not_found_exception() {

        ProdutoServicoNotFoundException ex = mock(ProdutoServicoNotFoundException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleProdutoServicoNotFoundException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.fromData(null, HttpStatus.NOT_FOUND, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

    }

    @Test
    void test_exception_origem_debito_not_found_exception() {

        OrigemDebitoNotFoundException ex = mock(OrigemDebitoNotFoundException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleOrigemDebitoNotFoundException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.fromData(null, HttpStatus.NOT_FOUND, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

    }

    @Test
    void test_exception_tipo_receita_not_found_exception() {

        TipoReceitaNotFoundException ex = mock(TipoReceitaNotFoundException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleTipoReceitaNotFoundException(ex, request);

        var expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.fromData(null, HttpStatus.NOT_FOUND, errorMessage, Arrays.asList(errorMessage)));

        assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
        assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

    }
    
    @Test
    void test_exception_fase_divida_not_found_exception() {

        FaseDividaNotFoundException ex = mock(FaseDividaNotFoundException.class);
        String errorMessage = "Mensagem de erro";
        when(ex.getMessage()).thenReturn(errorMessage);

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleFaseDividaNotFoundException(ex, request);

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
    void test_exception_data_integrity_violation_exception_exclusao_origem_debito() {

        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "fk_tbtiporeceitaorigem_tborigemdebito";
		when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }

    @Test
    void test_exception_data_integrity_violation_exception_exclusao_tipo_receita() {

        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        WebRequest request = mock(WebRequest.class);

        String mgsErro = "fk_tbprodutoservicoreceita_tbtiporeceita";
        when(ex.getCause()).thenReturn(new RuntimeException(mgsErro));
        when(ex.getMessage()).thenReturn(mgsErro);

        String expectedErrorMsg = "Violação de integridade: fk_tbprodutoservicoreceita_tbtiporeceita";
        ResponseDto<?> expectedResponse = ResponseDto.fromData(expectedErrorMsg, HttpStatus.BAD_REQUEST, expectedErrorMsg, Arrays.asList(mgsErro));

        var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handlePSQLException(ex, request);

        assertEquals(expectedResponse.getStatus(), responseEntity.getStatusCodeValue());
        assertEquals(expectedResponse.getMensagem(), responseEntity.getBody().getMensagem());
    }
  
}