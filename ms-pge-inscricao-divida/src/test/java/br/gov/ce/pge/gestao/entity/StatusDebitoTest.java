package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusDebitoTest {

    public static StatusDebito getStatusDebito() {

        StatusDebito statusDebito = new StatusDebito();
        statusDebito.setId(UUID.fromString("ca3752b7-d33a-4041-ba19-4731cbab41e2"));
        statusDebito.setNome("Teste");

        return statusDebito;
    }

    @Test
    void asserts() {
        StatusDebito statusDebito = getStatusDebito();

        assertEquals(UUID.fromString("ca3752b7-d33a-4041-ba19-4731cbab41e2"), statusDebito.getId());
        assertEquals("Teste", statusDebito.getNome());
    }
}
