package br.gov.ce.pge.gestao.contantes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MensagemFaseDividaTest {

	@Test
	public void test_mensagem_tipo_cobranca_invalido() {
		assertEquals("Não é possível informar um tipo de cobrança para uma fase que não exige cobrança.",
				MensagemFaseDivida.MENSAGEM_FASE_TIPO_COBRANCA_INVALIDO);
	}

	@Test
	public void test_mensagem_cadastrada() {
		assertEquals("O nome da fase é semelhante ao(s) registro(s) abaixo já cadastrado(s). Deseja prosseguir? %s", MensagemFaseDivida.MENSAGEM_FASE_SEMELHANTE);
	}

	@Test
	public void test_mensagem_fase_semelhante() {
		assertEquals("Fase da Dívida não encontrada.",
				MensagemFaseDivida.MENSAGEM_FASE_NAO_ENCONTRADA);
	}

	@Test
	public void test_mensagem_fase_nao_encontrada() {
		assertEquals(
				"Fase da Dívida não encontrada.",
				MensagemFaseDivida.MENSAGEM_FASE_NAO_ENCONTRADA);
	}

}
