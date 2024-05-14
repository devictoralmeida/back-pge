package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class FluxoFaseDividaResponseDtoTest {

    @Test
    void test_response() {
        FluxoFaseDividaResponseDto dto = getFluxoFase();

        Assertions.assertNotNull(dto);

        Assertions.assertEquals(1, dto.getFasesPermitidas().size());
        Assertions.assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());
    }

    public static FluxoFaseDividaResponseDto getFluxoFase() {
        FluxoFaseDividaResponseDto fluxoFaseDividaResponseDto = new FluxoFaseDividaResponseDto();
        fluxoFaseDividaResponseDto.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
        fluxoFaseDividaResponseDto.getFasesPermitidas().add(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18b"));
        return fluxoFaseDividaResponseDto;
    }
}
