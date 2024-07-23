package br.gov.ce.pge.gestao.shared.auditoria;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuditoriaTest {
    @Test
    void test_setters_and_getters() {
        Long id = 1L;
        long timestamp = System.currentTimeMillis();
        LocalDateTime dataMovimento = LocalDateTime.now();
        String nomeUsuario = "TestUser";
        String dadosAntigos = "OldData";

        Auditoria auditoria = new Auditoria();

        auditoria.setId(id);
        auditoria.setTimestamp(timestamp);
        auditoria.setDataMovimento(dataMovimento);
        auditoria.setNomeUsuario(nomeUsuario);
        auditoria.setDadosAntigos(dadosAntigos);

        assertEquals(id, auditoria.getId());
        assertEquals(timestamp, auditoria.getTimestamp());
        assertEquals(dataMovimento, auditoria.getDataMovimento());
        assertEquals(nomeUsuario, auditoria.getNomeUsuario());
        assertEquals(dadosAntigos, auditoria.getDadosAntigos());
    }

    @Test
    void test_to_string() {
        Long id = 1L;
        long timestamp = System.currentTimeMillis();
        LocalDateTime dataMovimento = LocalDateTime.now();
        String nomeUsuario = "TestUser";
        String dadosAntigos = "OldData";

        Auditoria auditoria = new Auditoria();
        auditoria.setId(id);
        auditoria.setTimestamp(timestamp);
        auditoria.setDataMovimento(dataMovimento);
        auditoria.setNomeUsuario(nomeUsuario);
        auditoria.setDadosAntigos(dadosAntigos);

        String expectedToString = "Auditoria(id=" + id +
                ", timestamp=" + timestamp +
                ", dataMovimento=" + dataMovimento +
                ", nomeUsuario=" + nomeUsuario +
                ", dadosAntigos=" + dadosAntigos +
                ")";

        String actualToString = auditoria.toString();
        String actualWithoutSuper = actualToString.replaceAll("super=.*?, ", "");
        assertEquals(expectedToString, actualWithoutSuper);
    }

}
