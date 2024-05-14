package br.gov.ce.pge.gestao.shared.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PaginacaoUtilTest {

	@Test
	void test() {
		assertEquals(1, PaginacaoUtil.getTotalPaginas(10, 10));
		assertEquals(0, PaginacaoUtil.getOffset(1, 10));
	}

}
