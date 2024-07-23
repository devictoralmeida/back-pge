package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FaseDividaTest {

    public static FaseDivida getFaseDivida() {
        FaseDivida faseDivida = new FaseDivida();
        faseDivida.setId(UUID.fromString("5d726d53-6892-4c3b-a15c-525af40e15f2"));
        faseDivida.setIdFase(UUID.fromString("a75a6cc2-26c7-4af5-ac86-bd0d00306b0c"));
        faseDivida.setObservacao("Teste");
        faseDivida.setNomeAnexo("Teste");
        faseDivida.setMotivoAtualizacaoFase(MotivoAtualizacaoTest.getMotivo());
        faseDivida.setFaseAtual(true);
        faseDivida.setDataFase(LocalDateTime.now());
        faseDivida.setFaseAtual(true);

        return faseDivida;
    }

    public static FaseDivida getFaseDividaAnterior() {
        FaseDivida faseDivida = new FaseDivida();
        faseDivida.setId(UUID.fromString("5d726d53-6892-4c3b-a15c-525af40e15f2"));
        faseDivida.setIdFase(UUID.fromString("a75a6cc2-26c7-4af5-ac86-bd0d00306b0c"));
        faseDivida.setObservacao("Teste");
        faseDivida.setNomeAnexo("Teste");
        faseDivida.setMotivoAtualizacaoFase(MotivoAtualizacaoTest.getMotivo());
        faseDivida.setFaseAtual(true);
        faseDivida.setDataFase(LocalDateTime.now());
        faseDivida.setFaseAtual(false);
        faseDivida.setFaseAnterior(true);

        return faseDivida;
    }

    @Test
    void asserts() {
        FaseDivida faseDivida = getFaseDivida();
        assertEquals(UUID.fromString("5d726d53-6892-4c3b-a15c-525af40e15f2"), faseDivida.getId());
        assertEquals(UUID.fromString("a75a6cc2-26c7-4af5-ac86-bd0d00306b0c"), faseDivida.getIdFase());
        assertEquals("Teste", faseDivida.getObservacao());
        assertEquals("Teste", faseDivida.getNomeAnexo());
        assertNotNull(faseDivida.getMotivoAtualizacaoFase());
        assertNotNull(faseDivida.getDataFase());
        assertEquals(true, faseDivida.getFaseAtual());
    }

}
