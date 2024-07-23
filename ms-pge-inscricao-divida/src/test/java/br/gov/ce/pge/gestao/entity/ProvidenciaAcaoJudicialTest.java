package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProvidenciaAcaoJudicialTest {

    public static ProvidenciaAcaoJudicial getProvidencia() {
        ProvidenciaAcaoJudicial providenciaAcaoJudicial = new ProvidenciaAcaoJudicial();
        providenciaAcaoJudicial.setId(UUID.fromString("ad8fdad0-ba4d-414c-9e1b-b1881bae733b"));
        providenciaAcaoJudicial.setNome("Teste");

        return providenciaAcaoJudicial;
    }

    @Test
    void asserts() {
        ProvidenciaAcaoJudicial providencia = getProvidencia();

        assertEquals(UUID.fromString("ad8fdad0-ba4d-414c-9e1b-b1881bae733b"), providencia.getId());
        assertEquals("Teste", providencia.getNome());
    }

}
