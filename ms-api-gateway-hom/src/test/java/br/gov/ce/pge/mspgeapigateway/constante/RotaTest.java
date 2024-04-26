package br.gov.ce.pge.mspgeapigateway.constante;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RotaTest {

	@Test
	void teste_valor_constante() {
		assertEquals("/login/**", Rota.LOGIN);
		assertEquals("/usuario", Rota.USUARIO);
		assertEquals("/perfil-acesso", Rota.PERFIL_ACESSO);
		assertEquals("/sistema/**", Rota.SISTEMA);
		assertEquals("/modulo/**", Rota.MODULO);
		assertEquals("/permissao/**", Rota.PERMISSAO);
		assertEquals("/produto-servico", Rota.PRODUTO_SERVICO);
		assertEquals("/tipo-receita", Rota.TIPO_RECEITA);
		assertEquals("/origem-debito", Rota.ORIGEM_DEBITO);
		assertEquals("/condicao-acesso", Rota.CONDICAO_ACESSO);
		assertEquals("/termos-condicoes", Rota.TERMOS_CONDICOES);
		assertEquals("/fase-divida/**", Rota.FASE_DIVIDA);
	}

}
