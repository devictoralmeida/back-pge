package br.gov.ce.pge.mspgeoauth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public interface LdapAuthenticationProviderService {
    Authentication authenticate(Authentication authentication, String login) throws AuthenticationException;
}
