package br.gov.ce.pge.gestao.shared.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.gov.ce.pge.gestao.constants.GlobalExceptionConstant;
import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.enums.RespostaErro;
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
		 
        if (message != null && (message.contains(GlobalExceptionConstant.CPF))) {
            return GlobalExceptionConstant.MENSAGEM_CPF_DUPLICADO;
        } else if (message != null && message.contains(GlobalExceptionConstant.EMAIL)) {
            return GlobalExceptionConstant.MENSAGEM_EMAIL_DUPLICADO;
        } else if (message != null && message.contains(GlobalExceptionConstant.CODIGO)) {
            return GlobalExceptionConstant.MENSAGEM_CODIGO_DUPLICADO;
        } else if(message != null && message.contains("fk_tbmodulopermissao_tbpermissao")) {
        	return GlobalExceptionConstant.MENSAGEM_PERMISSAO_UTILIZADO;
        } else if(message != null && message.contains("fk_tbsistemamodulo_tbmodulo")) {
        	return GlobalExceptionConstant.MENSAGEM_MODULO_UTILIZADO;
        } else if(message != null && message.contains("fk_tbperfilacessosistema_tbsistema")) {
        	return GlobalExceptionConstant.MENSAGEM_SISTEMA_UTILIZADO;
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
				GlobalExceptionConstant.MENSAGEM_VRIFICAR_CAMPOS, errosParaUsuario);
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
                    GlobalExceptionConstant.MENSAGEM_VALOR_INVALIDO,
                    enumType, invalidValue, enumValues);

            ResponseDto<?> res = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST,errorMessage);
    		return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
        }

		return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * EXCEÇÕES PERSONALIZADAS
	 **/

	@ExceptionHandler({ NegocioException.class })
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		LOGGER.error(" =============== NegocioException ==========================");

		List<String> erros = new ArrayList<>();

		if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_CODIGO_EXPIRADO)) {
			erros.add(RespostaErro.CODIGO_EXPIRADO.toString());
		}

		if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_EMAIL_NAO_CADASTRADO)) {
			erros.add(RespostaErro.EMAIL_NAO_CADASTRADO.toString());
		}

		if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_USUARIO_BLOQUEADO)) {
			erros.add(RespostaErro.RECUPERAR_SENHA_USUARIO_BLOQUEADO.toString());
		}

		if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_USUARIO_INATIVO)) {
			erros.add(RespostaErro.RECUPERAR_SENHA_USUARIO_INATIVO.toString());
		}

		if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_MUITAS_SOLICITACOES)) {
			erros.add(RespostaErro.RECUPERACAO_JA_ENVIADO.toString());
		}

		if(ex.getMessage().contains(MessageCommonsContanst.MENSAGEM_USUARIO_INTERNO)) {
			erros.add(RespostaErro.RECUPERAR_SENHA_USUARIO_INTERNO.toString());
		}

		var field = ex.getMessage();
		var error = ex.getMessage();

		if(ex.getMessage().contains(GlobalExceptionConstant.EMAIL)) {
			field = GlobalExceptionConstant.MENSAGEM_EMAIL_EXISTENTE;
			error = ex.getMessage().replace(GlobalExceptionConstant.EMAIL, GlobalExceptionConstant.REGISTRO);
		}else if(ex.getMessage().contains("CPF")) {
			field = GlobalExceptionConstant.MENSAGEM_CPF_EXISTENTE;
			error = ex.getMessage().replace("CPF", GlobalExceptionConstant.REGISTRO);
		}else if(ex.getMessage().contains("usuário de rede")) {
			field = GlobalExceptionConstant.MENSAGEM_USUARIO_REDE_EXISTENTE;
			error = ex.getMessage().replace("usuário de rede", GlobalExceptionConstant.REGISTRO);
		}

		erros.add(field);

		Object obj = ResponseDto.fromData(null, HttpStatus.BAD_REQUEST, error,
				erros);
		return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
	}
	

	@ExceptionHandler({ HistoricoAtualizacaoNotFoundException.class })
	public ResponseEntity<?> handleHistoricoAtualizacaoNotFoundException(HistoricoAtualizacaoNotFoundException ex, WebRequest request) {
		LOGGER.error(" =============== HistoricoAtualizacaoNotFoundException ==========================");
		Object obj = ResponseDto.fromData(null, HttpStatus.NOT_FOUND, ex.getMessage(),
				Arrays.asList(ex.getMessage()));
		return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
	}
	
}