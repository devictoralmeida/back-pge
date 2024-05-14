package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.enums.TipoProcesso;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DividaTest {


    public static Divida get_divida() {
        var model = new Divida();
        model.setId(UUID.fromString("8f7c10c5-0fcc-42a9-ba83-1049ba377760"));
        model.setIdOrigemDebito(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"));
        model.setIdTipoReceita(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"));
        model.setDisposicoesLegais("teste");
        model.setNaturezaFundamentacao(null);
        model.setInexistenciaCausaSuspensivas(null);
        model.setTipoDocumento(TipoDocumento.AIAM.name());
        model.setNumeroDocumento("1234567890");
        model.setDataDocumento(LocalDate.parse("2024-03-20"));
        model.setTermoRevelia("123456789");
        model.setDataTermoRevelia(LocalDate.parse("2024-03-20"));
        model.setNumeroOficio("ABC123");
        model.setTipoProcesso(TipoProcesso.CONAT);
        model.setNumeroProcessoAdministrativo("12345/2024");
        model.setDataProcesso(LocalDate.parse("2024-03-20"));
        model.setNumeroAcordao("6789");
        model.setDataTransitoJulgado(LocalDate.parse("2024-03-20"));
        model.setDataConstituicaoDefinitivaCredito(LocalDate.parse("2024-03-20"));
        model.setPlacaVeiculo(null);
        model.setNumeroChassi(null);
        model.setGuiaItcd("XYZ789");
        model.setSequencialParcelamento(null);
        model.setProtocoloJudicial(null);
        model.setNomeAnexoProcessoDigitalizado(null);

        return model;
    }

    @Test
    public void teste_divida() {
        var model = get_divida();
        assertEquals(UUID.fromString("f9a62fe7-9838-45a3-abf3-0fb7e559d386"), model.getIdOrigemDebito());
        assertEquals(UUID.fromString("457d0cce-48b6-458d-b912-bb6d80fac211"), model.getIdTipoReceita());
        assertEquals("teste", model.getDisposicoesLegais());
        assertEquals(null, model.getNaturezaFundamentacao());
        assertEquals(null, model.getInexistenciaCausaSuspensivas());
        assertEquals(TipoDocumento.AIAM.name(), model.getTipoDocumento());
        assertEquals("1234567890", model.getNumeroDocumento());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataDocumento());
        assertEquals("123456789", model.getTermoRevelia());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataTermoRevelia());
        assertEquals("ABC123", model.getNumeroOficio());
        assertEquals(TipoProcesso.CONAT, model.getTipoProcesso());
        assertEquals("12345/2024", model.getNumeroProcessoAdministrativo());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataProcesso());
        assertEquals("6789", model.getNumeroAcordao());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataTransitoJulgado());
        assertEquals(LocalDate.parse("2024-03-20"), model.getDataConstituicaoDefinitivaCredito());
        assertEquals(null, model.getPlacaVeiculo());
        assertEquals(null, model.getNumeroChassi());
        assertEquals("XYZ789", model.getGuiaItcd());
        assertEquals(null, model.getSequencialParcelamento());
        assertEquals(null, model.getProtocoloJudicial());
        assertEquals(null, model.getNomeAnexoProcessoDigitalizado());
    }

}
