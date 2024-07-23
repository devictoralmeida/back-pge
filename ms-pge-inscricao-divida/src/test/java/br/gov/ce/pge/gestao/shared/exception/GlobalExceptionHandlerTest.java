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

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
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
  public void teste_psqlException_com_cpf() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    DataIntegrityViolationException ex = new DataIntegrityViolationException(
            "duplicate key value violates unique constraint \"cpf\"");
    WebRequest request = mock(WebRequest.class);
    ResponseEntity<?> response = handler.handlePSQLException(ex, request);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void teste_psql_exception_com_email() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    DataIntegrityViolationException ex = new DataIntegrityViolationException(
            "duplicate key value violates unique constraint \"email\"");
    WebRequest request = mock(WebRequest.class);
    ResponseEntity<?> response = handler.handlePSQLException(ex, request);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void teste_psql_exception_com_codigo() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    DataIntegrityViolationException ex = new DataIntegrityViolationException(
            "duplicate key value violates unique constraint \"codigo\"");
    WebRequest request = mock(WebRequest.class);
    ResponseEntity<?> response = handler.handlePSQLException(ex, request);
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void test_exception_method_argument_not_valid_exception() {

    WebRequest request = mock(WebRequest.class);
    HttpHeaders headers = new HttpHeaders();

    BindingResult bindingResult = mock(BindingResult.class);
    List<FieldError> fieldErrors = Arrays.asList(new FieldError("objectName", "field1", "Erro no campo 1"),
            new FieldError("objectName", "field2", "Erro no campo 2"));
    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

    ResponseEntity<Object> responseEntity = exceptionHandler.handleMethodArgumentNotValid(ex, headers,
            HttpStatus.BAD_REQUEST, request);

    ResponseEntity<Object> expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST,
                    "Favor verifique todos os campos com validação",
                    Arrays.asList("Erro no campo 1", "Erro no campo 2")));

    assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
  }

  @Test
  void test_exception_negocio_exception() {

    NegocioException ex = mock(NegocioException.class);
    final String errorMessage = "Mensagem de erro";
    when(ex.getMessage()).thenReturn(errorMessage);

    var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

    var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

    assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
    assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

  }

  @Test
  void test_exception_invalid_format_exception() {

    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    HttpHeaders headers = new HttpHeaders();
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    MockHttpServletRequest request = new MockHttpServletRequest();
    ServletWebRequest webRequest = new ServletWebRequest(request);

    InvalidFormatException ife = mock(InvalidFormatException.class);
    when(ex.getRootCause()).thenReturn(ife);
    when(ife.getValue()).thenReturn("ValorInválido");

    when(ife.getTargetType()).thenReturn((Class) Natureza.class);

    var responseEntity = exceptionHandler.handleHttpMessageNotReadable(ex, headers, status, webRequest);

    var expectedErrorMessage = String.format("Valor inválido para o tipo %s: %s. Valores aceitos são: %s",
            Natureza.class.getSimpleName(), "ValorInválido", Arrays.asList(Natureza.values()));
    var expectedResponseEntity = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, expectedErrorMessage);
    assertEquals(expectedResponseEntity.getStatus(), responseEntity.getStatusCodeValue());
  }

  @Test
  void test_exception_invalid_format_exception_with_DateTimeParseException() {
    HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
    HttpHeaders headers = new HttpHeaders();
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    MockHttpServletRequest request = new MockHttpServletRequest();
    ServletWebRequest webRequest = new ServletWebRequest(request);

    DateTimeParseException dateTimeParseException = mock(DateTimeParseException.class);
    when(ex.getRootCause()).thenReturn(dateTimeParseException);
    when(dateTimeParseException.getParsedString()).thenReturn("InvalidDateTime");

    final String errorMessage = "Formato de data/hora inválido: InvalidDateTime. Use os formatos apropriados para data e hora.";
    when(ex.getLocalizedMessage()).thenReturn(errorMessage);

    var responseEntity = exceptionHandler.handleHttpMessageNotReadable(ex, headers, status, webRequest);

    var expectedResponseEntity = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage);
    assertEquals(expectedResponseEntity.getStatus(), responseEntity.getStatusCodeValue());
  }

  @Test
  void teste_exception_dividas_com_sucessor_cadastrado() {
    DividasComSucessorCadastradoException ex = new DividasComSucessorCadastradoException("12345");
    ResponseEntity<?> response = exceptionHandler.handleDividasComSucessorCadastradoException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("A(s) inscrição(es) de número < 12345 > já possui um sucessor cadastrado. Deseja realmente prosseguir com o cadastro do sucessor?", responseDto.getMensagem());
    assertTrue(responseDto.getErrors().contains("DIVIDAS_COM_SUCESSOR_CADASTRADO"));
  }

  @Test
  void teste_exception_sucessor_cadastrado() {
    SucessorJaCadastradoException ex = new SucessorJaCadastradoException("12345");
    ResponseEntity<?> response = exceptionHandler.handleSucessorJaCadastradoException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("A(s) inscrição(es) de número < 12345 > já possui o sucessor cadastrado! Para prosseguir com o cadastro do sucessor, remova-as da seleção", responseDto.getMensagem());
  }

  @Test
  void teste_exception_atualiza_fase_extinta() {
    AtualizaFaseExtintaException ex = new AtualizaFaseExtintaException("A fase da inscrição 12345 está extinta.");
    ResponseEntity<?> response = exceptionHandler.handleFaseExtintaException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("A fase da inscrição 12345 está extinta.", responseDto.getMensagem());
  }

  @Test
  void teste_exception_atualiza_mesma_fase() {
    AtualizaMesmaFaseException ex = new AtualizaMesmaFaseException("A inscrição 12345 já está nesta fase.");
    ResponseEntity<?> response = exceptionHandler.handleFaseExtintaException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("A inscrição 12345 já está nesta fase.", responseDto.getMensagem());
  }

  @Test
  void teste_exception_dividas_devedores_distintos() {
    DividasComDevedoresDistintoException ex = new DividasComDevedoresDistintoException();
    ResponseEntity<?> response = exceptionHandler.handleDividasComDevedoresDistintoException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("As inscrições selecionadas possuem devedores distintos. Deseja realmente prosseguir com o cadastro do sucessor?", responseDto.getMensagem());
  }

  @Test
  void teste_exception_sucessor_cadastrado_como_devedor() {
    SucessorCadastradoComoDevedorException ex = new SucessorCadastradoComoDevedorException("12345");
    ResponseEntity<?> response = exceptionHandler.handleSucessorCadastradoComoDevedorException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("A(s) inscrição(es) de número < 12345 > já possui o sucessor cadastrado como devedor! Para prosseguir com o cadastro do sucessor, remova-as da seleção.", responseDto.getMensagem());
    assertTrue(responseDto.getErrors().contains("SUCESSOR_CADASTRADO_COMO_DEVEDOR"));
  }

  @Test
  void teste_exception_dividas_fase_finalistica() {
    DividasFaseFinalisticaException ex = new DividasFaseFinalisticaException("12345");
    ResponseEntity<?> response = exceptionHandler.handleDividasFaseFinalisticaException(ex, request);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertInstanceOf(ResponseDto.class, response.getBody());
    ResponseDto responseDto = (ResponseDto) response.getBody();
    assertEquals("A(s) inscrição(es) de número < 12345 > já foi finalizada (Quitada ou Extinta). Deseja realmente prosseguir com o cadastro do sucessor?", responseDto.getMensagem());
    assertTrue(responseDto.getErrors().contains("DIVIDAS_FASE_FINALISTICA"));
  }

}
