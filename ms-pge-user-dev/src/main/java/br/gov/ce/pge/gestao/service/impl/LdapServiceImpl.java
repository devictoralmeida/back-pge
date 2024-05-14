package br.gov.ce.pge.gestao.service.impl;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.service.LdapService;

@Service
public class LdapServiceImpl implements LdapService {

    private final LdapTemplate ldapTemplate;
    
    public LdapServiceImpl(LdapTemplate ldapTemplate) {
    	this.ldapTemplate = ldapTemplate;
    }

    @Override
    public boolean userExists(String username) {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        String filter = "(sAMAccountName=" + username + ")";

        ldapTemplate.setIgnorePartialResultException(true);
        List<?> results = ldapTemplate.search("", filter, searchControls, (AttributesMapper<Object>) attributes ->
            attributes
        );

        return !results.isEmpty();
    }
}
