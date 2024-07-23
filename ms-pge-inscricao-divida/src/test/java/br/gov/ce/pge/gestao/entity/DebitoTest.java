package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DebitoTest {

    public static Debito get_debito() {
        Debito model = new Debito();
        model.setId(UUID.fromString("dc816fd4-b7e1-46c9-8c0c-092a1eea12f3"));
        model.setReferenciaInicial("01/2024");
        model.setReferenciaFinal("03/2024");
        model.setDataVencimento(LocalDate.parse("2024-03-29"));
        model.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
        model.setDataInicioAtualizacaoMonetaria(LocalDate.parse("2024-03-25"));
        model.setValorPrincipal(BigDecimal.valueOf(150));
        model.setValorMulta(BigDecimal.valueOf(50));
        model.setStatus(StatusDebitoTest.getStatusDebito());

        return model;
    }

    public static List<Debito> get_debitos_list() {
        Debito debito1 = get_debito();
        Debito debito2 = get_debito();
        debito2.setId(UUID.fromString("dc816fd4-b7e1-46c9-8c0c-092a1eea12f4"));
        debito2.setReferenciaInicial("01/2024");
        debito2.setReferenciaFinal("02/2024");
        debito2.setDataVencimento(LocalDate.parse("2024-03-29"));
        debito2.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-30"));
        debito2.setValorPrincipal(BigDecimal.valueOf(200));
        debito2.setValorMulta(BigDecimal.valueOf(100));
        debito2.setDataInicioAtualizacaoMonetaria(LocalDate.parse("2024-03-25"));

        List<Debito> list = new ArrayList<>();
        list.add(debito1);
        list.add(debito2);

        return list;
    }

    @Test
    void teste_debito() {
        Debito model = get_debito();
        assertEquals(UUID.fromString("dc816fd4-b7e1-46c9-8c0c-092a1eea12f3"), model.getId());
        assertEquals("01/2024", model.getReferenciaInicial());
        assertEquals("03/2024", model.getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-29"), model.getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), model.getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), model.getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), model.getValorMulta());
    }

    @Test
    void teste_debitos_list() {
        List<Debito> debitos = get_debitos_list();
        assertEquals(2, debitos.size());

        Debito debito1 = debitos.get(0);
        assertEquals("01/2024", debito1.getReferenciaInicial());
        assertEquals("03/2024", debito1.getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-29"), debito1.getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), debito1.getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(150), debito1.getValorPrincipal());
        assertEquals(BigDecimal.valueOf(50), debito1.getValorMulta());

        Debito debito2 = debitos.get(1);
        assertEquals("01/2024", debito2.getReferenciaInicial());
        assertEquals("02/2024", debito2.getReferenciaFinal());
        assertEquals(LocalDate.parse("2024-03-29"), debito2.getDataVencimento());
        assertEquals(LocalDate.parse("2024-03-30"), debito2.getDataConstituicaoDefinitivaCredito());
        assertEquals(BigDecimal.valueOf(200), debito2.getValorPrincipal());
        assertEquals(BigDecimal.valueOf(100), debito2.getValorMulta());
    }

    @Test
    void test_asserts_debito() {
        Debito debito = new Debito();

        debito.setId(UUID.fromString("dc816fd4-b7e1-46c9-8c0c-092a1eea12f3"));
        debito.setValorJuros(new BigDecimal(10));
        debito.setValorEncargos(new BigDecimal(10));
        debito.setValorHonorarios(new BigDecimal(10));
        debito.setSaldoDevedor(new BigDecimal(10));
        debito.setStatus(StatusDebitoTest.getStatusDebito());
        debito.setDivida(DividaTest.getDivida());

        asserts_valores(debito);
    }

    void asserts_valores(Debito debito) {

        assertEquals(new BigDecimal(10), debito.getValorJuros());
        assertEquals(new BigDecimal(10), debito.getValorEncargos());
        assertEquals(new BigDecimal(10), debito.getValorHonorarios());
        assertEquals(new BigDecimal(10), debito.getSaldoDevedor());
        assertEquals(UUID.fromString("dc816fd4-b7e1-46c9-8c0c-092a1eea12f3"), debito.getId());
        assertNotNull(debito.getDivida());
        assertNotNull(debito.getStatus());
    }
}
