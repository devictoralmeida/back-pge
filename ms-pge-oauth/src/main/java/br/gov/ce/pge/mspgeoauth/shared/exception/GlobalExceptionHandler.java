package br.gov.ce.pge.mspgeoauth.shared.exception;

import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.enums.RespostaErro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        List<String> erros = new ArrayList<>();

        if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_SENHA_EXPIRADA)) {
            erros.add(RespostaErro.SENHA_EXPIRADA.toString());
        }

        if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_LOGIN_ERROR)) {
            erros.add(RespostaErro.CREDENCIAIS_INVALIDAS.toString());
        }

        if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_ACESSO_BLOQUEADO)) {
            erros.add(RespostaErro.TENTATIVAS_INVALIDAS.toString());
        }

        if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVA_BLOQUEADA)) {
            erros.add(RespostaErro.BLOQUEADO_INATIVO.toString());
        }

        ResponseDto<Object> obj = ResponseDto.fromData(field, HttpStatus.FORBIDDEN.value(), erro, erros);

        return handleExceptionInternal(ex, obj, null, HttpStatus.FORBIDDEN, request);
    }

}
