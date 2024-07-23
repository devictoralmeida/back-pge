package br.gov.ce.pge.gestao.shared.exception;

import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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

import java.time.format.DateTimeParseException;
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

    String fieldName = extractFieldName(ex.getMessage());
    String errorMsg;

    if (fieldName != null) {
      errorMsg = fieldName;
    } else {
      errorMsg = "Violação de integridade: " + getMessage(ex.getMessage());
    }

    ResponseDto<?> obj = ResponseDto.fromData(errorMsg, HttpStatus.BAD_REQUEST, errorMsg, Arrays.asList(ex.getMessage()));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }

  public String getMessage(String message) {
    return getTable(message) + " : " + getField(message);
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

    List<String> erros = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST,
            "Favor verifique todos os campos com validação", erros);

    return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    Throwable rootCause = ex.getRootCause();
    if (rootCause instanceof InvalidFormatException) {
      LOGGER.error(" =============== HttpMessageNotReadableException validation ENUM ==========================");

      InvalidFormatException invalidFormatException = (InvalidFormatException) rootCause;
      String invalidValue = invalidFormatException.getValue().toString();
      String enumType = invalidFormatException.getTargetType().getSimpleName();
      List<String> enumValues = Arrays.stream(invalidFormatException.getTargetType().getEnumConstants())
              .map(Object::toString)
              .collect(Collectors.toList());

      String errorMessage = String.format(
              "Valor inválido para o tipo %s: %s. Valores aceitos são: %s.",
              enumType, invalidValue, enumValues);

      ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage);
      return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    } else if (rootCause instanceof DateTimeParseException) {
      LOGGER.error(" =============== HttpMessageNotReadableException validation LocalDate or LocalDateTime ==========================");

      DateTimeParseException dateTimeParseException = (DateTimeParseException) rootCause;
      String invalidValue = dateTimeParseException.getParsedString();
      String errorMessage = "Formato de data/hora inválido: " + invalidValue + ". ";

      if (ex.getLocalizedMessage().contains("LocalDate")) {
        errorMessage += "O formato esperado para a data é YYYY-MM-DD e para data com horário é YYYY-MM-DDTHH:MM:SS.";
      } else {
        errorMessage += "Use os formatos apropriados para data e hora.";
      }

      ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, errorMessage);
      return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }

    return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
  }

  /**
   * EXCEÇÕES PERSONALIZADAS
   **/

  @ExceptionHandler({NegocioException.class})
  public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
    LOGGER.error(" =============== NegocioException ==========================");

    String field = ex.getMessage();
    String error = ex.getMessage();

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error, Arrays.asList(field));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }
  
  @ExceptionHandler({DividasComSucessorCadastradoException.class})
  public ResponseEntity<?> handleDividasComSucessorCadastradoException(DividasComSucessorCadastradoException ex, WebRequest request) {
    LOGGER.error(" =============== DividasComSucessorCadastradoException ==========================");

    String msg = "A(s) inscrição(es) de número < " + ex.getMessage() + " > já possui um sucessor cadastrado. Deseja realmente prosseguir com o cadastro do sucessor?";
    String error = "DIVIDAS_COM_SUCESSOR_CADASTRADO";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, msg, Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }
  
  @ExceptionHandler({SucessorJaCadastradoException.class})
  public ResponseEntity<?> handleSucessorJaCadastradoException(SucessorJaCadastradoException ex, WebRequest request) {
    LOGGER.error(" =============== SucessorJaCadastradoException ==========================");

    String msg = "A(s) inscrição(es) de número < " + ex.getMessage() + " > já possui o sucessor cadastrado! Para prosseguir com o cadastro do sucessor, remova-as da seleção";
    String error = "SUCESSOR_JA_CADASTRADO";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, msg, Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({AtualizaFaseExtintaException.class})
  public ResponseEntity<?> handleFaseExtintaException(AtualizaFaseExtintaException ex, WebRequest request) {
    LOGGER.error(" =============== AtualizaFaseExtintaException ==========================");

    String error = "INSCRICAO_FASE_QUITADA_EXTINTA";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, ex.getMessage(), Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({AtualizaMesmaFaseException.class})
  public ResponseEntity<?> handleFaseExtintaException(AtualizaMesmaFaseException ex, WebRequest request) {
    LOGGER.error(" =============== AtualizaMesmaFaseException ==========================");

    String error = "INSCRICAO_MESMA_FASE";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, ex.getMessage(), Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }
  
  @ExceptionHandler({DividasComDevedoresDistintoException.class})
  public ResponseEntity<?> handleDividasComDevedoresDistintoException(DividasComDevedoresDistintoException ex, WebRequest request) {
    LOGGER.error(" =============== DividasComDevedoresDistintoException ==========================");

    String msg = "As inscrições selecionadas possuem devedores distintos. Deseja realmente prosseguir com o cadastro do sucessor?";
    String error = "DIVIDAS_DEVEDORES_DISTINTO";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, msg, Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }
  
  @ExceptionHandler({SucessorCadastradoComoDevedorException.class})
  public ResponseEntity<?> handleSucessorCadastradoComoDevedorException(SucessorCadastradoComoDevedorException ex, WebRequest request) {
    LOGGER.error(" =============== SucessorCadastradoComoDevedorException ==========================");

    String msg = "A(s) inscrição(es) de número < " + ex.getMessage() + " > já possui o sucessor cadastrado como devedor! Para prosseguir com o cadastro do sucessor, remova-as da seleção.";
    String error = "SUCESSOR_CADASTRADO_COMO_DEVEDOR";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, msg, Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }
  
  @ExceptionHandler({DividasFaseFinalisticaException.class})
  public ResponseEntity<?> handleDividasFaseFinalisticaException(DividasFaseFinalisticaException ex, WebRequest request) {
    LOGGER.error(" =============== DividasFaseFinalisticaException ==========================");

    String msg = "A(s) inscrição(es) de número < " + ex.getMessage() + " > já foi finalizada (Quitada ou Extinta). Deseja realmente prosseguir com o cadastro do sucessor?";
    String error = "DIVIDAS_FASE_FINALISTICA";

    Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, msg, Arrays.asList(error));
    return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
  }
}
