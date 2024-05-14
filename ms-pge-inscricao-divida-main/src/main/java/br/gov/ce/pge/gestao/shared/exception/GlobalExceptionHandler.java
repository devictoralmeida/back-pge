package br.gov.ce.pge.gestao.shared.exception;

import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String REGISTRO = "registro";
  private static final String CODIGO = "codigo";
  private static final String EMAIL = "email";
  private static final String CPF = "cpf";
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<?> handlePSQLException(DataIntegrityViolationException ex, WebRequest request) {
    LOGGER.error(" =============== DataIntegrityViolationException ==========================");

    String fieldName = this.extractFieldName(ex.getMessage());
    String errorMsg;

    if (fieldName != null) {
      errorMsg = fieldName;
    } else {
      errorMsg = "Violação de integridade: " + this.getMessage(ex.getMessage());
    }

    ResponseDto<?> obj = ResponseDto.fromData(errorMsg, HttpStatus.BAD_REQUEST, errorMsg, Arrays.asList(ex.getMessage()));
    return this.handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }

  public String getMessage(String message) {
    return this.getTable(message) + " : " + this.getField(message);
  }

  public String getField(String errorMessage) {
    int indexOfConstraint = errorMessage.indexOf("constraint [");
    int indexOfEndOfField = errorMessage.indexOf("\" of relation", indexOfConstraint);
    return errorMessage.substring(indexOfConstraint + "constraint [".length(), indexOfEndOfField);
  }

  public String getTable(String errorMessage) {
    int indexOfRelation = errorMessage.indexOf("\" of relation");
    int indexOfEndOfTable = errorMessage.indexOf("];", indexOfRelation);
    return errorMessage.substring(indexOfRelation + "\" of relation \"".length(), indexOfEndOfTable);
  }

  private String extractFieldName(String message) {

    if (message != null && (message.contains(CPF))) {
      return "Valor duplicado encontrado para o campo: CPF";
    } else if (message != null && message.contains(EMAIL)) {
      return "Valor duplicado encontrado para o campo: EMAIL";
    } else if (message != null && message.contains(CODIGO)) {
      return "Valor duplicado encontrado para o campo: CODIGO";
    }
    return null;
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    LOGGER.error(" =============== Campos do DTO que não passaram na validação ==========================");

    List<String> errosParaUsuario = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage())
            .collect(Collectors.toList());

    ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST,
            "Favor verifique todos os campos com validação", errosParaUsuario);
    return this.handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    LOGGER.error(" =============== HttpMessageNotReadableException validation ENUM ==========================");

    Throwable rootCause = ex.getRootCause();
    if (rootCause instanceof InvalidFormatException) {
      InvalidFormatException ife = (InvalidFormatException) rootCause;
      String invalidValue = ife.getValue().toString();
      String enumType = ife.getTargetType().getSimpleName();
      List<String> enumValues = Arrays.stream(ife.getTargetType().getEnumConstants())
              .map(Object::toString)
              .collect(Collectors.toList());

      String errorMessage = String.format(
              "Valor inválido para o tipo %s: %s. Valores aceitos são: %s",
              enumType, invalidValue, enumValues);

      ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage);
      return this.handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }

    return this.handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
  }

  /**
   * EXCEÇÕES PERSONALIZADAS
   **/

  @ExceptionHandler({NegocioException.class})
  public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
    LOGGER.error(" =============== NegocioException ==========================");

    var field = ex.getMessage();
    var error = ex.getMessage();

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error,
            Arrays.asList(field));
    return this.handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }

}
