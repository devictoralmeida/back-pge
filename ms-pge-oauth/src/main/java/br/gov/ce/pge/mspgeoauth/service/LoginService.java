package br.gov.ce.pge.mspgeoauth.service;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public interface LoginService {
	
	String login(@Validated LoginDto loginDto, HttpServletRequest request) throws JsonProcessingException;

	void removerSessao(String nomeUsuario, String ip) throws JsonProcessingException;
}
