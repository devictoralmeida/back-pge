package br.gov.ce.pge.gestao.service;

import org.springframework.stereotype.Service;

@Service
public interface LdapService {
    boolean userExists(String username);
}
