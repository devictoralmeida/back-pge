package br.gov.ce.pge.gestao.shared.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HistoricoUtilTest {

	@Test
	void test_offset() {
		Long offset = HistoricoUtil.getOffset(1, 10);
		assertEquals(0, offset);
	}
	
	@Test
	void test_total_pagina() {
		Integer total = HistoricoUtil.getTotalPaginas(12, 10);
		assertEquals(2, total);
	}

}
