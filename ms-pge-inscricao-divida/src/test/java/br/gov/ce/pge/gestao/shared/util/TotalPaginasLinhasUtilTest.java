package br.gov.ce.pge.gestao.shared.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalPaginasLinhasUtilTest {

    @Test
    void test_deve_retornar_o_numero_correto_de_paginas() {
        final int totalRegistros = 125;
        final int expectedTotalPaginas = 3;
        assertEquals(expectedTotalPaginas, TotalPaginasLinhasUtil.getTotalPaginas(totalRegistros));
    }

    @Test
    void test_deve_retornar_um_para_valores_menores_que_50_registros() {
        final int totalRegistros = 30;
        final int expectedTotalPaginas = 1;
        assertEquals(expectedTotalPaginas, TotalPaginasLinhasUtil.getTotalPaginas(totalRegistros));
    }

    @Test
    void test_deve_retornar_resto_divisao_correto() {
        final int totalRegistrosLivro = 125;
        final int expectedResto = 25;
        assertEquals(expectedResto, TotalPaginasLinhasUtil.getRestoDivisao(totalRegistrosLivro));
    }

    @Test
    void test_deve_retornar_resto_divisao_zerO_multiplos_registros_exatos() {
        final int totalRegistrosLivro = 100;
        final int expectedResto = 0;
        assertEquals(expectedResto, TotalPaginasLinhasUtil.getRestoDivisao(totalRegistrosLivro));
    }
}
