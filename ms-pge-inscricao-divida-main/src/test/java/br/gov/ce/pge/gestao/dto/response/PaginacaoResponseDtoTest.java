package br.gov.ce.pge.gestao.dto.response;

import java.util.List;

public class PaginacaoResponseDtoTest {

    public static PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> getPaginacaoFilterRegistroInscricaoPagina() {
        return PaginacaoResponseDto.fromResultado(List.of(RegistroInscricaoResponseDtoTest.getRegistroInscricaoPagina()),1,1,1);
    }

    public static PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> getPaginacaoFilterRegistroInscricaoLinha() {
        return PaginacaoResponseDto.fromResultado(List.of(RegistroInscricaoResponseDtoTest.getRegistroInscricaoLinha()),1,1,1);
    }

    public static PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> getPaginacaoFilterRegistroInscricaoVazio() {
        return PaginacaoResponseDto.fromResultado(List.of(),0,0,1);
    }

}
