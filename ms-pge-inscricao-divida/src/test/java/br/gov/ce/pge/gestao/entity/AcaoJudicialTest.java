package br.gov.ce.pge.gestao.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AcaoJudicialTest {

    public static AcaoJudicial getAcaoJudicial() {

        AcaoJudicial acaoJudicial = new AcaoJudicial();
        acaoJudicial.setId(UUID.fromString("4b5746a6-b42b-4af3-ae09-2bbf820a3479"));
        acaoJudicial.setAutor("Teste");
        acaoJudicial.setJuizo("Juizo Teste");
        acaoJudicial.setNomeAnexo("Teste");
        acaoJudicial.setFaseDivida(FaseDividaTest.getFaseDivida());
        acaoJudicial.setNumeroOrdemJudicial("234");
        acaoJudicial.setReu("Teste");
        acaoJudicial.setTipoAcaoJudicial(TipoAcaoJudicialTest.getTipoAcaoJudicial());
        acaoJudicial.setProvidenciaAcaoJudicial(ProvidenciaAcaoJudicialTest.getProvidencia());
        acaoJudicial.setObservacao("Teste");

        return acaoJudicial;
    }

    @Test
    void asserts() {
        AcaoJudicial acaoJudicial = getAcaoJudicial();

        assertNotNull(acaoJudicial);
        assertEquals(UUID.fromString("4b5746a6-b42b-4af3-ae09-2bbf820a3479"), acaoJudicial.getId());
        assertEquals("Teste", acaoJudicial.getAutor());
        assertNotNull(acaoJudicial.getFaseDivida());
        assertEquals("Juizo Teste", acaoJudicial.getJuizo());
        assertEquals("Teste", acaoJudicial.getNomeAnexo());
        assertEquals("234", acaoJudicial.getNumeroOrdemJudicial());
        assertEquals("Teste", acaoJudicial.getReu());
        assertNotNull(acaoJudicial.getTipoAcaoJudicial());
        assertNotNull(acaoJudicial.getProvidenciaAcaoJudicial());
        assertEquals("Teste", acaoJudicial.getObservacao());
    }
}
