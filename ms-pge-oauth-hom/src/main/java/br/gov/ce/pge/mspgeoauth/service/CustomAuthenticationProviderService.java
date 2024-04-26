package br.gov.ce.pge.mspgeoauth.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



@Service
public interface CustomAuthenticationProviderService extends AuthenticationProvider {
    Authentication authenticate(Authentication authentication);
}
