package br.gov.ce.pge.gestao.entity;

import java.util.UUID;

import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDto;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrigemDebitoTest {

	@Test
	public void test_criacao_origem() {
		OrigemDebito dto = getOrigem();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"), dto.getId());
		Assertions.assertEquals("teste nome origem", dto.getNome());
		Assertions.assertEquals("teste", dto.getDescricao());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	public static OrigemDebito getOrigem() {
		var origem = new OrigemDebito();
		origem.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		origem.setNome("teste nome origem");
		origem.setSituacao(Situacao.ATIVA);
		origem.setDescricao("teste");
		return origem;
	}

	public static OrigemDebito getOrigemDiferente() {
		var origem = new OrigemDebito();
		origem.setId(UUID.fromString("b88bc5e8-6126-4af9-9c61-cea471800974"));
		origem.setNome("teste nome origem2");
		origem.setSituacao(Situacao.ATIVA);
		origem.setDescricao("teste");
		return origem;
	}
	
	public static OrigemDebito getOrigemUpdate() {
		var origem = new OrigemDebito();
		origem.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		origem.setNome("teste nome origem");
		origem.setSituacao(Situacao.INATIVA);
		origem.setDescricao("teste");
		return origem;
	}

	public static OrigemDebito getOrigemUpdateOutroNome() {
		var origem = new OrigemDebito();
		origem.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		origem.setNome("teste nome origem update");
		origem.setSituacao(Situacao.ATIVA);
		origem.setDescricao("teste");
		return origem;
	}

	public static OrigemDebito getOrigemUpdateOutraDescricao() {
		var origem = new OrigemDebito();
		origem.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		origem.setNome("teste nome origem");
		origem.setSituacao(Situacao.ATIVA);
		origem.setDescricao("teste descricao diferente");
		return origem;
	}

	public static OrigemDebito getOrigemUpdateOutraSituacao() {
		var origem = new OrigemDebito();
		origem.setId(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a"));
		origem.setNome("teste nome origem");
		origem.setSituacao(Situacao.INATIVA);
		origem.setDescricao("teste");
		return origem;
	}
}
