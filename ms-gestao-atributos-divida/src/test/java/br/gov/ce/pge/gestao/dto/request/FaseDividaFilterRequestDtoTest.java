package br.gov.ce.pge.gestao.dto.request;


import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FaseDividaFilterRequestDtoTest {

    public static FaseDividaFilterRequestDto getFilterRequest() {
        var dto = new FaseDividaFilterRequestDto();
        dto.setNome("cadastrada");
        dto.setSituacao(Situacao.ATIVA);
        dto.setCodigo("0001");
        dto.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
        dto.setExigeCobranca(true);
        dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
        return dto;
    }


    @Test
    void test_filters() {
        FaseDividaFilterRequestDto dto = getFilterRequest();

        Map<String, Object> filters = dto.filters();
        assertEquals("%CADASTRADA%", filters.get("nome"));
        assertEquals(TipoMovimentacaoFase.AUTOMATICA, filters.get("tipoMovimentacao"));
        assertEquals(Situacao.ATIVA, filters.get("situacao"));
        assertEquals("%0001%", filters.get("codigo"));
        assertEquals("AJUIZAMENTO", filters.get("tipoCobranca"));
        assertEquals(true, filters.get("exigeCobranca"));


        dto.setNome("");
        dto.setTipoCobranca(null);
        dto.setSituacao(null);
        dto.setCodigo(null);
        dto.setTipoMovimentacao(null);
        dto.setExigeCobranca(null);

        Map<String, Object> filters2 = dto.filters();

        assertNull(filters2.get("nome"));
        assertNull(filters2.get("tipoCobranca"));
        assertNull(filters2.get("tipoMovimentacao"));
        assertNull(filters2.get("situacao"));
        assertNull(filters2.get("codigo"));
        assertNull(filters2.get("exigeCobranca"));

        dto.setNome(null);
        dto.setTipoCobranca(new ArrayList<>());
        dto.setSituacao(null);
        dto.setCodigo("");
        dto.setTipoMovimentacao(null);
        dto.setExigeCobranca(null);

        Map<String, Object> filters3 = dto.filters();

        assertNull(filters3.get("nome"));
        assertNull(filters3.get("tipoCobranca"));
        assertNull(filters3.get("tipoMovimentacao"));
        assertNull(filters3.get("situacao"));
        assertNull(filters3.get("codigo"));
        assertNull(filters3.get("exigeCobranca"));
    }


}
