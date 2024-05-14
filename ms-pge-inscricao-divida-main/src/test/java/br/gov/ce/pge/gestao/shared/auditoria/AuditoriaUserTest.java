package br.gov.ce.pge.gestao.shared.auditoria;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuditoriaUserTest {

    @Test
    void test_pre_update() {

        AuditoriaUser auditoriaUser = new AuditoriaUser() {
        };

        auditoriaUser.preUpdate();

        assertEquals("anônimo", auditoriaUser.getNomeUsuarioAtualizacao());
    }

    @Test
    void test_pre_persist() {

        AuditoriaUser auditoriaUser = new AuditoriaUser() {
        };

        auditoriaUser.prePersist();

        assertEquals("anônimo", auditoriaUser.getNomeUsuarioCadastro());
    }

    @Test
    void test_set_and_get_data_criacao() {
        LocalDateTime dataCriacao = LocalDateTime.now();
        AuditoriaUser auditoriaUser = new AuditoriaUser() {
        };
        auditoriaUser.setDataCriacao(dataCriacao);

        LocalDateTime retrievedDataCriacao = auditoriaUser.getDataCriacao();

        assertNotNull(retrievedDataCriacao);
        assertEquals(dataCriacao, retrievedDataCriacao);
    }

    @Test
    void test_set_and_get_data_atualizacao() {
        LocalDateTime dataAtualizacao = LocalDateTime.now();
        AuditoriaUser auditoriaUser = new AuditoriaUser() {
        };
        auditoriaUser.setDataAtualizacao(dataAtualizacao);

        LocalDateTime retrievedDataAtualizacao = auditoriaUser.getDataAtualizacao();

        assertNotNull(retrievedDataAtualizacao);
        assertEquals(dataAtualizacao, retrievedDataAtualizacao);
    }

}
