package br.gov.ce.pge.gestao.entity;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TipoReceitaTest {

	@Test
	public void test_criacao_tipo_receita() {
		TipoReceita dto = getTipoReceita();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"), dto.getId());
		Assertions.assertEquals("Receita 01", dto.getDescricao());
		Assertions.assertEquals("0001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
		Assertions.assertEquals(Natureza.TRIBUTARIA, dto.getNatureza());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
	}

	public static TipoReceita getTipoReceita() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		model.setCodigo("0001");
		model.setDescricao("Receita 01");
		model.setNatureza(Natureza.TRIBUTARIA);
		model.setSituacao(Situacao.ATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigem()));
		return model;
	}

	public static TipoReceita getTipoReceitaDiferente() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("bb5b7b6f-cb6e-4ec3-8ddf-ad4489d011b4"));
		model.setCodigo("0002");
		model.setDescricao("Receita 02");
		model.setNatureza(Natureza.TRIBUTARIA);
		model.setSituacao(Situacao.ATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigem()));
		return model;
	}
	
	public static TipoReceita getTipoReceitaUpdate() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		model.setCodigo("0001");
		model.setDescricao("Receita 01 update");
		model.setNatureza(Natureza.TRIBUTARIA);
		model.setSituacao(Situacao.ATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigem()));
		return model;
	}

	public static TipoReceita getTipoReceitaComOutraDescricao() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		model.setCodigo("0001");
		model.setDescricao("Receita 02");
		model.setNatureza(Natureza.TRIBUTARIA);
		model.setSituacao(Situacao.ATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigem()));
		return model;
	}

	public static TipoReceita getTipoReceitaComOutraSituacao() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		model.setCodigo("0001");
		model.setDescricao("Receita 01");
		model.setNatureza(Natureza.TRIBUTARIA);
		model.setSituacao(Situacao.INATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigem()));
		return model;
	}

	public static TipoReceita getTipoReceitaComOutraNatureza() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		model.setCodigo("0001");
		model.setDescricao("Receita 01");
		model.setNatureza(Natureza.NAO_TRIBUTARIA);
		model.setSituacao(Situacao.ATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigem()));
		return model;
	}

	public static TipoReceita getTipoReceitaComOutraOrigem() {
		var model = new TipoReceita();
		model.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		model.setCodigo("0001");
		model.setDescricao("Receita 01");
		model.setNatureza(Natureza.TRIBUTARIA);
		model.setSituacao(Situacao.ATIVA);
		model.setOrigemDebitos(List.of(OrigemDebitoTest.getOrigemDiferente()));
		return model;
	}

}
