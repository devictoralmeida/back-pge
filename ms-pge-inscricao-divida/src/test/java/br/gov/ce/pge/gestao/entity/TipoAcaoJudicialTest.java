package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoAcaoJudicialTest {

    public static TipoAcaoJudicial getTipoAcaoJudicial() {
        TipoAcaoJudicial tipoAcaoJudicial = new TipoAcaoJudicial();
        tipoAcaoJudicial.setTipo("Teste");
        tipoAcaoJudicial.setId(UUID.fromString("9beecf55-10be-480a-9f29-92b693de80e0"));

        return tipoAcaoJudicial;
    }

    @Test
    void asserts() {
        TipoAcaoJudicial tipoAcaoJudicial = getTipoAcaoJudicial();

        assertEquals("Teste", tipoAcaoJudicial.getTipo());
        assertEquals(UUID.fromString("9beecf55-10be-480a-9f29-92b693de80e0"), tipoAcaoJudicial.getId());
    }

}
