package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoDocumentoAnexoTest {

    public static TipoDocumentoAnexo getTipoDocumentoAnexo() {
        TipoDocumentoAnexo tipoDocumentoAnexo = new TipoDocumentoAnexo();
        tipoDocumentoAnexo.setId(UUID.fromString("457f2dc7-ff87-4664-b5d5-b12314539882"));
        tipoDocumentoAnexo.setTipoDocumento("Documento Exemplo");

        return tipoDocumentoAnexo;
    }

    @Test
    void asserts() {
        TipoDocumentoAnexo tipoDocumentoAnexo = getTipoDocumentoAnexo();

        assertEquals(UUID.fromString("457f2dc7-ff87-4664-b5d5-b12314539882"), tipoDocumentoAnexo.getId());
        assertEquals("Documento Exemplo", tipoDocumentoAnexo.getTipoDocumento());
    }

}
