package br.gov.ce.pge.gestao.dto.response;

import java.util.UUID;

public class DividaResponseDtoTest {

    public static DividaResponseDto getResponse() {

        DividaResponseDto responseDto = new DividaResponseDto();
        responseDto.setId(UUID.fromString("e93fd062-c46e-4dfa-ad87-67ab0300dd37"));
        responseDto.setNumeroInscricao("2024000000001");

        return responseDto;
    }

}
