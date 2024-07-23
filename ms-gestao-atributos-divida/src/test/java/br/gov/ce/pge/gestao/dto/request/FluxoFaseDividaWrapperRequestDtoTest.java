package br.gov.ce.pge.gestao.dto.request;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class FluxoFaseDividaWrapperRequestDtoTest {

    @Test
    void test_filters() {
        FluxoFaseDividaWrapperRequestDto dto = getFluxoFaseRequest();

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(2, dto.getFases().size());
    }

    public static FluxoFaseDividaWrapperRequestDto getFluxoFaseRequest() {
        FluxoFaseDividaWrapperRequestDto result = new FluxoFaseDividaWrapperRequestDto();

        FluxoFaseDividaRequestDto dto = new FluxoFaseDividaRequestDto();
        dto.setId(UUID.fromString("a5da1209-1bdc-4648-af37-b91bc5f91a1a"));
        dto.getFasesPermitidas().add("1a93fa58-3304-4810-bc1b-8e535a4295f4");

        FluxoFaseDividaRequestDto dto2 = new FluxoFaseDividaRequestDto();
        dto.setId(UUID.fromString("e917c9c5-d5ed-41b9-b2bf-0c0d0a349fc8"));
        dto.getFasesPermitidas().add("76426def-a362-400b-82da-a68f74c3f1f3");

        result.getFases().add(dto);
        result.getFases().add(dto2);

        return result;
    }

    public static FluxoFaseDividaWrapperRequestDto getFluxoFaseRequestMesmoId() {
        FluxoFaseDividaWrapperRequestDto result = new FluxoFaseDividaWrapperRequestDto();

        FluxoFaseDividaRequestDto dto = new FluxoFaseDividaRequestDto();
        dto.setId(UUID.fromString("a5da1209-1bdc-4648-af37-b91bc5f91a1a"));
        dto.getFasesPermitidas().add("1a93fa58-3304-4810-bc1b-8e535a4295f4");

        FluxoFaseDividaRequestDto dto2 = new FluxoFaseDividaRequestDto();
        dto.setId(UUID.fromString("e917c9c5-d5ed-41b9-b2bf-0c0d0a349fc8"));
        dto.getFasesPermitidas().add("e917c9c5-d5ed-41b9-b2bf-0c0d0a349fc8");

        result.getFases().add(dto);
        result.getFases().add(dto2);

        return result;
    }
}
