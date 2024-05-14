package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DebitoTest {


    public static Debito get_debito() {
        var model = new Debito();
        model.setReferenciaInicial("01/2024");
        model.setReferenciaFinal("10/2024");
        model.setDataVencimento(LocalDate.parse("2024-03-30"));
        model.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
        model.setValorPrincipal(BigDecimal.valueOf(150));
        model.setValorMulta(BigDecimal.valueOf(50));
        model.setInscricao(InscricaoTest.getIncricaoComDevedorDividaStatusCorresponsaveis());

        return model;
    }

    public static List<Debito> get_debitos_list() {
        Debito debito1 = get_debito();
        Debito debito2 = get_debito();
        debito2.setReferenciaInicial("11/2024");
        debito2.setReferenciaFinal("12/2024");
        debito2.setDataVencimento(LocalDate.parse("2024-03-30"));
        debito2.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
        debito2.setValorPrincipal(BigDecimal.valueOf(200));
        debito2.setValorMulta(BigDecimal.valueOf(100));
        debito2.setInscricao(InscricaoTest.getIncricaoComDevedorDividaStatusCorresponsaveis());

        return new ArrayList<>(List.of(debito1, debito2));
    }

    @Test
    void teste_debito() {
        var model = get_debito();
        assertEquals("01/2024", model.getReferenciaInicial());
        assertEquals("10/2024", model.getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-30"), model.getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), model.getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), model.getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), model.getValorMulta());
        assertNotNull(model.getInscricao());
    }

}
