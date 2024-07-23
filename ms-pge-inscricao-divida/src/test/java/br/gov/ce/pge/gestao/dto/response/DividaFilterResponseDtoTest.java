package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DividaFilterResponseDtoTest {

    public static DividaFilterResponseDto getDividaFilter() {
        DividaFilterResponseDto response = new DividaFilterResponseDto();

        response.setId("1215d61e-5154-48a8-845e-b6be24f7a7bc");
        response.setStatusCobranca(true);
        response.setArquivos(true);
        response.setCgf("123456789");
        response.setDocumento("12345678912");
        response.setNomeDevedor("Teste");
        response.setNumeroAI("122321342432535");
        response.setIdFaseAtual("19e36372-0d0a-44ad-8a7c-f5ba842bcabb");
        response.setIdTipoReceita("b1d83b79-6730-4e93-ab33-ae7ff45c9868");
        response.setProtestada(true);
        response.setAjuizada(true);
        response.setNotificada(true);
        response.setQuitadoOuExtinto(true);
        response.setDocumentoSucessor("Teste");
        response.setFaseAtual("Inicial");
        response.setTipoReceita("001");
        response.setChassi("123");
        response.setPlaca("PNQ4545");
        response.setGuiaItcd("ITCD1234");
        response.setNumeroInscricao("12434");
        response.setIdOrigemDebito("b1d83b79-6730-4e93-ab33-ae7ff45c9868");
        response.setLote("12344");
        response.setTipoDevedor("Principal");
        response.setProtocoloJudicial("1245");
        response.setSaldoDevedor(new BigDecimal(12));
        response.setPagamentoAtrasado(true);

        return response;
    }

    @Test
    void test_divida_filter_response() {
        DividaFilterResponseDto dividaFilter = getDividaFilter();

        assertEquals("1215d61e-5154-48a8-845e-b6be24f7a7bc", dividaFilter.getId());
        assertEquals(true, dividaFilter.getStatusCobranca());
        assertEquals(true, dividaFilter.getArquivos());
        assertEquals("123456789", dividaFilter.getCgf());
        assertEquals("12345678912", dividaFilter.getDocumento());
        assertEquals("Teste", dividaFilter.getNomeDevedor());
        assertEquals("122321342432535", dividaFilter.getNumeroAI());
        assertEquals("19e36372-0d0a-44ad-8a7c-f5ba842bcabb", dividaFilter.getIdFaseAtual());
        assertEquals("b1d83b79-6730-4e93-ab33-ae7ff45c9868", dividaFilter.getIdTipoReceita());
        assertEquals(true, dividaFilter.getProtestada());
        assertEquals(true, dividaFilter.getAjuizada());
        assertEquals(true, dividaFilter.getNotificada());
        assertEquals(true, dividaFilter.getQuitadoOuExtinto());
        assertEquals("Inicial", dividaFilter.getFaseAtual());
        assertEquals("001", dividaFilter.getTipoReceita());
        assertEquals("123", dividaFilter.getChassi());
        assertEquals("PNQ4545", dividaFilter.getPlaca());
        assertEquals("ITCD1234", dividaFilter.getGuiaItcd());
        assertEquals("12434", dividaFilter.getNumeroInscricao());
        assertEquals("b1d83b79-6730-4e93-ab33-ae7ff45c9868", dividaFilter.getIdOrigemDebito());
        assertEquals("12344", dividaFilter.getLote());
        assertEquals("Principal", dividaFilter.getTipoDevedor());
        assertEquals("1245", dividaFilter.getProtocoloJudicial());
        assertEquals(new BigDecimal(12), dividaFilter.getSaldoDevedor());
        assertEquals(true, dividaFilter.getPagamentoAtrasado());
    }
}
