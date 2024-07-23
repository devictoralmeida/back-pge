package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    public static PaginacaoResponseDto<List<DividaFilterResponseDto>> getDividaFilter() {
        return PaginacaoResponseDto.fromResultado(List.of(DividaFilterResponseDtoTest.getDividaFilter()),1,1,1);
    }

    public static PaginacaoResponseDto<List<DividaFilterResponseDto>> getDividaFilterVazio() {
        return PaginacaoResponseDto.fromResultado(List.of(),0,0,1);
    }

    @Test
    public void test_from_resultado() {
        final String resultList = "Sample Result List";
        PaginacaoResponseDto<String> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(resultList, 50, 3, 2);

        assertEquals(50, paginacaoResponseDto.getTotalRegistros());
        assertEquals(3, paginacaoResponseDto.getTotalPaginas());
        assertEquals(2, paginacaoResponseDto.getPaginaAtual());
        assertEquals(resultList, paginacaoResponseDto.getList());
    }

    @Test
    public void test_set_list() {
        PaginacaoResponseDto<Boolean> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(true, 1, 1, 1);
        assertEquals(true, paginacaoResponseDto.getList());
    }

    @Test
    public void test_setters_and_getters() {

        PaginacaoResponseDto<Integer> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(42, 100, 5, 2);

        assertEquals(100, paginacaoResponseDto.getTotalRegistros());
        assertEquals(5, paginacaoResponseDto.getTotalPaginas());
        assertEquals(2, paginacaoResponseDto.getPaginaAtual());
        assertEquals(42, paginacaoResponseDto.getList());
    }

    @Test
    public void test_null_list() {
        PaginacaoResponseDto<Object> paginacaoResponseDto = PaginacaoResponseDto.fromResultado(null, 0, 1, 1);

        assertNull(paginacaoResponseDto.getList());
        assertEquals(0, paginacaoResponseDto.getTotalRegistros());
        assertEquals(1, paginacaoResponseDto.getTotalPaginas());
        assertEquals(1, paginacaoResponseDto.getPaginaAtual());
    }

}
