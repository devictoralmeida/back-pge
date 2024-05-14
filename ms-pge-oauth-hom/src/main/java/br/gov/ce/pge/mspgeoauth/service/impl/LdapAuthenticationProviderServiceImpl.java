package br.gov.ce.pge.mspgeoauth.service.impl;

import br.gov.ce.pge.mspgeoauth.service.LdapAuthenticationProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthenticationProviderServiceImpl implements LdapAuthenticationProviderService {

    private final ActiveDirectoryLdapAuthenticationProvider provider;

    public LdapAuthenticationProviderServiceImpl(@Value("${ldap.domain}") String domain, @Value("${ldap.url}") String url) {
        this.provider = new ActiveDirectoryLdapAuthenticationProvider(domain, url);
        this.provider.setConvertSubErrorCodesToExceptions(true);
        this.provider.setUseAuthenticationRequestCredentials(true);
    }

    @Override
    public Authentication authenticate(Authentication authentication, String login) throws AuthenticationException {
        this.provider.setSearchFilter("(&(objectClass=user)(sAMAccountName=" + login + "))");
        return provider.authenticate(authentication);
    }
}
