package br.gov.ce.pge.gestao.dto.request;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.Situacao;

public class OrigemDebitoFilterRequestDtoTest {

    public static OrigemDebitoFilterRequestDto getFilterRequest() {
        var dto = new OrigemDebitoFilterRequestDto();
        dto.setNome(null);
        dto.setDescricao(null);
        dto.setSituacao(null);
        return dto;
    }

    public static OrigemDebitoFilterRequestDto getOrigemDebito() {
        var dto = new OrigemDebitoFilterRequestDto();
        dto.setNome("origem 1");
        dto.setDescricao("origem descricao 1");
        dto.setSituacao(Situacao.ATIVA);
        return dto;
    }

    @Test
    void test_filters() {
        OrigemDebitoFilterRequestDto dto = getOrigemDebito();

        Map<String, Object> filters = dto.filters();
        assertEquals("%ORIGEM 1%", filters.get("nome"));
        assertEquals("%ORIGEM DESCRICAO 1%", filters.get("descricao"));
        assertEquals(Situacao.ATIVA, filters.get("situacao"));

        dto.setNome("");
        dto.setDescricao("");
        dto.setSituacao(null);

        Map<String, Object> filters2 = dto.filters();

        assertNull(filters2.get("nome"));
        assertNull(filters2.get("descricao"));
        assertNull(filters2.get("situacao"));
        
        dto.setNome("teste");
        dto.setDescricao("teste");
        dto.setSituacao(null);

        Map<String, Object> filters3 = dto.filters();

        assertNotNull(filters3.get("nome"));
        assertNotNull(filters3.get("descricao"));
        assertNull(filters3.get("situacao"));
        
        
        var origem = new OrigemDebitoFilterRequestDto();
        origem.setDescricao(null);
        origem.setNome(null);
        
        Map<String, Object> filters4 = origem.filters();
        assertEquals(null, filters4.get("nome"));
        assertEquals(null, filters4.get("descricao"));
    }

}
