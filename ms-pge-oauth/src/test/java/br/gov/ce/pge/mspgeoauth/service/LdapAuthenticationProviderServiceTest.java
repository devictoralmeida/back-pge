package br.gov.ce.pge.mspgeoauth.service;

import br.gov.ce.pge.mspgeoauth.service.impl.LdapAuthenticationProviderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({ MockitoExtension.class })
public class LdapAuthenticationProviderServiceTest {

    @Test
    void test_construtor() {
        String domain = "example.com";
        String url = "ldap://example.com:389";

        LdapAuthenticationProviderServiceImpl service = new LdapAuthenticationProviderServiceImpl(domain, url);

        assertNotNull(service);
    }

}
