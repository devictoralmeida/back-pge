package br.gov.ce.pge.gestao.shared.auditoria;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuditoriaUserTest {

    @Test
    public void test_pre_update() {

        AuditoriaUser auditoriaUser = new AuditoriaUser() {
        };

        auditoriaUser.preUpdate();

        assertEquals("anônimo", auditoriaUser.getNomeUsuarioAtualizacao());
    }

    @Test
    public void test_pre_persist() {

        AuditoriaUser auditoriaUser = new AuditoriaUser() {
        };

        auditoriaUser.prePersist();

        assertEquals("anônimo", auditoriaUser.getNomeUsuarioCadastro());
    }

}
