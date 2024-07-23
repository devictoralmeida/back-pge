package br.gov.ce.pge.mspgeoauth.service;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public interface CustomAuthenticationProviderService extends AuthenticationProvider {

    Authentication authenticate(Authentication authentication);

    void validarDispositivo(LoginDto loginDto, HttpServletRequest request) throws JsonProcessingException;

}
