package br.gov.ce.pge.mspgeoauth.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class PermissaoTest {

    public static Permissao getPermissao() {
        var permissao = new Permissao();
        permissao.setId(UUID.fromString("e038f9db-5bfe-4a32-aff1-ad51f67b0640"));
        permissao.setNome("cadastrar");
        return permissao;
    }
    
    @Test
    public void test_equals_different_id() {
        Permissao permissao1 = new Permissao();
        permissao1.setId(UUID.randomUUID());
        Permissao permissao2 = new Permissao();
        permissao2.setId(UUID.randomUUID());
        assertFalse(permissao1.equals(permissao2));
    }

    @Test
    public void test_equals_different_nome() {
        Permissao permissao1 = new Permissao();
        permissao1.setId(UUID.randomUUID());
        permissao1.setNome("Nome1");
        Permissao permissao2 = new Permissao();
        permissao2.setId(permissao1.getId());
        permissao2.setNome("Nome2");
        assertFalse(permissao1.equals(permissao2));
    }

}
