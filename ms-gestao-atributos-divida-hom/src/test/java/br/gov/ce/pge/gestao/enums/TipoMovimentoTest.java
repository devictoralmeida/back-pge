package br.gov.ce.pge.gestao.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoMovimentoTest {

	@Test
	public void test_tipo_movimento_cadastrar() {
		assertEquals(0, TipoMovimento.CADASTRAR.getValue());
	}

	@Test
	public void test_tipo_movimento_atualizar() {
		assertEquals(1, TipoMovimento.ATUALIZAR.getValue());
	}

	@Test
	public void test_tipo_movimento_deletar() {
		assertEquals(2, TipoMovimento.DELETAR.getValue());
	}

}
