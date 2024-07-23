package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TipoProcessoTest {
    public static TipoProcesso get_tipo_processo() {
        TipoProcesso tipoProcesso = new TipoProcesso();
        tipoProcesso.setId(UUID.randomUUID());
        tipoProcesso.setNome("Processo Exemplo");
        return tipoProcesso;
    }

    @Test
    void teste_tipo_processo() {
        TipoProcesso tipoProcesso = get_tipo_processo();
        assertNotNull(tipoProcesso.getId());
        assertEquals("Processo Exemplo", tipoProcesso.getNome());
    }
}
