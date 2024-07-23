package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VaraOrigemTest {

    public static VaraOrigem get_vara_origem() {
        VaraOrigem varaOrigem = new VaraOrigem();
        varaOrigem.setId(UUID.randomUUID());
        varaOrigem.setNome("Vara Origem Exemplo");
        return varaOrigem;
    }

    @Test
    void teste_vara_origem() {
        VaraOrigem varaOrigem = get_vara_origem();
        assertNotNull(varaOrigem.getId());
        assertEquals("Vara Origem Exemplo", varaOrigem.getNome());
    }
}
