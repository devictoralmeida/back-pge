package br.gov.ce.pge.gestao.shared.exception;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.gov.ce.pge.gestao.dto.response.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<?> handlePSQLException(DataIntegrityViolationException ex, WebRequest request) {
        LOGGER.error(" =============== DataIntegrityViolationException ==========================");

        String message = ex.getCause().getMessage();
        String fieldName = extractFieldName(ex.getMessage());
        String errorMsg;

        if (fieldName != null) {
            errorMsg = fieldName;
        } else {
            errorMsg = "Violação de integridade: " + message;
        }

        ResponseDto<?> obj = ResponseDto.fromData(errorMsg, HttpStatus.BAD_REQUEST, errorMsg, Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
    }

    private String extractFieldName(String message) {
        LOGGER.error("============== " + message);
        if (message != null && (message.contains("cpf") || message.contains("CPF"))) {
            return "Valor duplicado encontrado para o campo: CPF";
        } else if (message != null && message.contains("email")) {
            return "Valor duplicado encontrado para o campo: EMAIL";
        } else if (message != null && message.contains("codigo")) {
            return "Valor duplicado encontrado para o campo: CODIGO";
        } else if(message != null && message.contains("fk_tbtiporeceitaorigem_tborigemdebito")) {
            return "Não foi possível realizar a exclusão! A Origem do Débito está sendo utilizada para um Tipo de Receita cadastrada.";
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
        return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {

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

            ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST,errorMessage);
            return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
        }

        return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * EXCEÇÕES PERSONALIZADAS
     **/
    @ExceptionHandler({ BadValueException.class })
    public ResponseEntity<?> handleBadValueException(BadValueException ex, WebRequest request) {
        LOGGER.error(" =============== Bad request front ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ NoContentException.class })
    public ResponseEntity<?> handleNoContentException(NoContentException ex, WebRequest request) {
        LOGGER.error(" =============== No content request front ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.NO_CONTENT, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ NegocioException.class })
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        LOGGER.error(" =============== NegocioException ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ OrigemDebitoNotFoundException.class })
    public ResponseEntity<?> handleOrigemDebitoNotFoundException(OrigemDebitoNotFoundException ex, WebRequest request) {
        LOGGER.error(" =============== OrigemDebitoNotFoundException ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ HistoricoAtualizacaoNotFoundException.class })
    public ResponseEntity<?> handleHistoricoAtualizacaoNotFoundException(HistoricoAtualizacaoNotFoundException ex, WebRequest request) {
        LOGGER.error(" =============== HistoricoAtualizacaoNotFoundException ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ ProdutoServicoNotFoundException.class })
    public ResponseEntity<?> handleProdutoServicoNotFoundException(ProdutoServicoNotFoundException ex, WebRequest request) {
        LOGGER.error(" =============== ProdutoServicoNotFoundException ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ TipoReceitaNotFoundException.class })
    public ResponseEntity<?> handleTipoReceitaNotFoundException(TipoReceitaNotFoundException ex, WebRequest request) {
        LOGGER.error(" =============== TipoReceitaNotFoundException ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ FaseDividaNotFoundException.class })
    public ResponseEntity<?> handleFaseDividaNotFoundException(FaseDividaNotFoundException ex, WebRequest request) {
        LOGGER.error(" =============== FaseDividaNotFoundException ==========================");
        Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, ex.getMessage(),
                Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }
}