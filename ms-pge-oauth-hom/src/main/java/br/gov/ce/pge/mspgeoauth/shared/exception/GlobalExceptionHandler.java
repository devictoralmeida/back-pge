package br.gov.ce.pge.mspgeoauth.shared.exception;

import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * EXCEÇÕES PERSONALIZADAS
     **/

    @ExceptionHandler({ NegocioException.class })
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
        LOGGER.error(" =============== NegocioException ==========================");

        String erro = ex.getMessage();
        String field = null;

        Pattern pattern = Pattern.compile("\\b[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}\\b");
        Matcher matcher = pattern.matcher(erro);

        if(matcher.find()) {
            field = matcher.group();
            erro = matcher.replaceAll("");
        }

        ResponseDto<Object> obj = ResponseDto.fromData(field, HttpStatus.FORBIDDEN.value(), erro);

        return handleExceptionInternal(ex, obj, null, HttpStatus.FORBIDDEN, request);
    }

}
