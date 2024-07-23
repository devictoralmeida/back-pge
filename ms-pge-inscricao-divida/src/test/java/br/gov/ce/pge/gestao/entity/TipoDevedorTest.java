package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoDevedorTest {

    public static TipoDevedor getTipoDevedor() {
        TipoDevedor tipoDevedor = new TipoDevedor();
        tipoDevedor.setId(UUID.fromString("3ef2d7b3-6822-4a6a-9652-ded8dd0ed90b"));
        tipoDevedor.setNome("Teste");

        return tipoDevedor;
    }

    public static TipoDevedor getTipoDevedorPrincipal() {
        TipoDevedor tipoDevedor = new TipoDevedor();
        tipoDevedor.setId(UUID.fromString("a948ce23-a065-4e2e-a249-463ea0f5dff2"));
        tipoDevedor.setNome("Principal");
        return tipoDevedor;
    }

    public static TipoDevedor getTipoDevedorSolidario() {
        TipoDevedor tipoDevedor = new TipoDevedor();
        tipoDevedor.setId(UUID.fromString("373e9aa4-4001-46d1-9f70-d6f64e003d49"));
        tipoDevedor.setNome("Solidário");
        return tipoDevedor;
    }

    public static TipoDevedor getTipoDevedorSubsidiario() {
        TipoDevedor tipoDevedor = new TipoDevedor();
        tipoDevedor.setId(UUID.fromString("6a75efa1-5725-4342-994d-d1a2fcd5b217"));
        tipoDevedor.setNome("Subsidiário");
        return tipoDevedor;
    }

    @Test
    void asserts() {
        TipoDevedor tipoDevedor = getTipoDevedor();
        assertEquals(UUID.fromString("3ef2d7b3-6822-4a6a-9652-ded8dd0ed90b"), tipoDevedor.getId());
        assertEquals("Teste", tipoDevedor.getNome());
    }

}
