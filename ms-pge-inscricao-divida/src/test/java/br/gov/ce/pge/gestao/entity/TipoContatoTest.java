package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TipoContatoTest {

    public static TipoContato getTipoContatoCelular() {

        TipoContato tipoContato = new TipoContato();
        tipoContato.setId(UUID.fromString("842e4277-29d7-435e-853f-dfffd2682624"));
        tipoContato.setNome("Celular");

        return tipoContato;
    }

    public static TipoContato getTipoContatoEmail() {

        TipoContato tipoContato = new TipoContato();
        tipoContato.setId(UUID.fromString("4ce0c397-8110-4198-a1e0-3f3faad9933f"));
        tipoContato.setNome("Email");

        return tipoContato;
    }

    @Test
    void asserts() {
        TipoContato tipoContato = getTipoContatoCelular();
        assertEquals(UUID.fromString("842e4277-29d7-435e-853f-dfffd2682624"), tipoContato.getId());
        assertEquals("Celular", tipoContato.getNome());
    }

}
