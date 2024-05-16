package br.gov.ce.pge.mspgeapigateway.constante;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IdentificadorTest {

	@Test
	void teste_valor_constante() {
		assertEquals("ORIGEM_DEBITO_", Identificador.IDF_ORIGEM_DEBITO);
		assertEquals("USUARIO_", Identificador.IDF_USUARIO);
		assertEquals("PRODUTO_SERVICO_", Identificador.IDF_PRODUTO_SERVICO);
		assertEquals("TIPO_RECEITA_", Identificador.IDF_TIPO_RECEITA);
		assertEquals("PERFIL_ACESSO_", Identificador.IDF_PERFIL_ACESSO);
		assertEquals("CONDICOES_ACESSO_", Identificador.IDF_CONDICOES_ACESSO);
		assertEquals("TERMOS_CONDICOES_", Identificador.IDF_TERMOS_CONDICOES);
	}

}
