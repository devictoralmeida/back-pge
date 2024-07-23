package br.gov.ce.pge.mspgeoauth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RequisicaoLogarTest {

    public static RequisicaoLogar getRequisicaoLogar() {
        var requisicaoLogar = new RequisicaoLogar();
        requisicaoLogar.setId(UUID.fromString("28c56a10-4c32-407a-adcf-ce8b263f646d"));
        requisicaoLogar.setHoraRequisicao(LocalDateTime.now());
        requisicaoLogar.setSucesso(true);
        requisicaoLogar.setUsuario(UsuarioTest.getUsuarioExterno());

        return requisicaoLogar;
    }

    @Test
    void test_classe() {
        var requisicaoLogar = new RequisicaoLogar();

        assertNotNull(requisicaoLogar);
    }

}
