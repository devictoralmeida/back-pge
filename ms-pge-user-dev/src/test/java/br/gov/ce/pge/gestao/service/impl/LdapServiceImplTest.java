package br.gov.ce.pge.gestao.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({ MockitoExtension.class })
public class LdapServiceImplTest {

    @Mock
    private LdapTemplate ldapTemplate;

    @InjectMocks
    private LdapServiceImpl ldapService;

    @Test
    public void test_user_exists_userFound() {
        when(ldapTemplate.search(eq(""), anyString(), any(SearchControls.class), any(AttributesMapper.class)))
                .thenReturn(Collections.singletonList(mock(Attributes.class)));

        assertTrue(ldapService.userExists("testUser"));
    }

}
