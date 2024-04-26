package br.gov.ce.pge.gestao.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
	public void teste_psqlException_com_email() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		DataIntegrityViolationException ex = new DataIntegrityViolationException(
				"duplicate key value violates unique constraint \"email\"");
		WebRequest request = mock(WebRequest.class);
		ResponseEntity<?> response = handler.handlePSQLException(ex, request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void teste_psqlException_com_codigo() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		DataIntegrityViolationException ex = new DataIntegrityViolationException(
				"duplicate key value violates unique constraint \"codigo\"");
		WebRequest request = mock(WebRequest.class);
		ResponseEntity<?> response = handler.handlePSQLException(ex, request);
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void test_exception_MethodArgumentNotValidException() {

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
	void test_exception_NegocioException() {

		NegocioException ex = mock(NegocioException.class);
		String errorMessage = "Mensagem de erro";
		when(ex.getMessage()).thenReturn(errorMessage);

		var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler.handleNegocioException(ex, request);

		var expectedResponseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage, Arrays.asList(errorMessage)));

		assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
		assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

	}

	@Test
	void test_exception_HistoricoAtualizacaoNotFoundException() {

		HistoricoAtualizacaoNotFoundException ex = mock(HistoricoAtualizacaoNotFoundException.class);
		String errorMessage = "Mensagem de erro";
		when(ex.getMessage()).thenReturn(errorMessage);

		var responseEntity = (ResponseEntity<ResponseDto>) exceptionHandler
				.handleHistoricoAtualizacaoNotFoundException(ex, request);

		var expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ResponseDto.fromData(null, HttpStatus.NOT_FOUND, errorMessage, Arrays.asList(errorMessage)));

		assertEquals(expectedResponseEntity.getStatusCode(), responseEntity.getStatusCode());
		assertEquals(expectedResponseEntity.getBody().getMensagem(), responseEntity.getBody().getMensagem());

	}

	@Test
	void test_exception_InvalidFormatException() {

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

		var expectedErrorMessage = String.format("Valor inválido para o tipo %s: %s. Valores aceitos são: %s",
				Natureza.class.getSimpleName(), "ValorInválido", Arrays.asList(Natureza.values()));
		var expectedResponseEntity = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, expectedErrorMessage);
		assertEquals(expectedResponseEntity.getStatus(), responseEntity.getStatusCodeValue());
	}

}