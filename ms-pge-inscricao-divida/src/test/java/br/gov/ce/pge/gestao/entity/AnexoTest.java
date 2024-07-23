package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnexoTest {

    public static Anexo get_anexo() {
        Anexo anexo = new Anexo();
        anexo.setId(UUID.fromString("fc45aa7e-d917-4e8f-9291-2f9759a3fae8"));
        anexo.setNome("Nome do Anexo");

        return anexo;
    }

    @Test
    void asserts() {
        Anexo anexo = get_anexo();

        assertEquals(UUID.fromString("fc45aa7e-d917-4e8f-9291-2f9759a3fae8"), anexo.getId());
        assertEquals("Nome do Anexo", anexo.getNome());
    }

//    @Test
//    void assert_divida() {
//
//        Anexo anexo = new Anexo();
//        anexo.setDivida(DividaTest.getDivida());
//        anexo.setTipoDocumento(TipoDocumentoAnexoTest.getTipoDocumentoAnexo());
//
//        assertNotNull(anexo.getDivida());
//        assertNotNull(anexo.getTipoDocumento());
//    }
}
