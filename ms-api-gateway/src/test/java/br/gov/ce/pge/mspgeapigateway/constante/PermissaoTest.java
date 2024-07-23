package br.gov.ce.pge.mspgeapigateway.constante;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PermissaoTest {

	@Test
	void teste_valor_constante() {
		assertEquals("EDITAR", Permissao.EDITAR);
		assertEquals("EXCLUIR", Permissao.EXCLUIR);
		assertEquals("CADASTRAR", Permissao.CADASTRAR);
		assertEquals("HISTORICO_ALTERACAO", Permissao.HISTORICO);
	}

}
