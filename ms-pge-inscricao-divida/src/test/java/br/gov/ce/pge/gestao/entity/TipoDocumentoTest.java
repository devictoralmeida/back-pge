package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoDocumentoTest {

    public static TipoDocumento getTipoDocumento() {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setId(UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9"));
        tipoDocumento.setNome("AI");

        return tipoDocumento;
    }

    @Test
    void asserts() {
        TipoDocumento tipoDocumento = getTipoDocumento();

        assertEquals(UUID.fromString("1a25c081-1035-4dbd-87b2-2aede997c7d9"), tipoDocumento.getId());
        assertEquals("AI", tipoDocumento.getNome());
    }

}
