package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class TermoCondicaoTest {

	public static TermoCondicao getTermoCondicao() {
		var termo = new TermoCondicao();
		termo.setId(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setSistema(SistemaTest.getSistema());
		termo.setNomeUsuarioCadastro("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		termo.setUsuarios(List.of(UsuarioTest.getUsuario()));
		return termo;
	}

	public static TermoCondicao getTermoCondicaoPendente() {
		var termo = new TermoCondicao();
		termo.setId(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setSistema(SistemaTest.getSistema());
		termo.setNomeUsuarioCadastro("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		termo.setUsuarios(List.of(UsuarioTest.getUsuarioInterno()));
		return termo;
	}

	public static TermoCondicao getTermoCondicaoPortalOrigens() {
		var termo = new TermoCondicao();
		termo.setId(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setSistema(SistemaTest.getSistemaPortalOrigens());
		termo.setNomeUsuarioCadastro("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		termo.setUsuarios(List.of(UsuarioTest.getUsuario()));
		return termo;
	}

	public static TermoCondicao getTermoCondicaoPortalOrigensPendente() {
		var termo = new TermoCondicao();
		termo.setId(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setSistema(SistemaTest.getSistemaPortalOrigens());
		termo.setNomeUsuarioCadastro("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		termo.setUsuarios(List.of(UsuarioTest.getUsuarioInterno()));
		return termo;
	}

	public static List<TermoCondicao> getListTermoCondicao() {
		return List.of(getTermoCondicao());
	}

	public static List<TermoCondicao> getListTermoCondicaoPortalOrigens() {
		return List.of(getTermoCondicaoPortalOrigens());
	}

	public static List<TermoCondicao> getListTermoCondicaoPendentes() {
		return List.of(getTermoCondicaoPendente());
	}

	public static List<TermoCondicao> getListTermoCondicaoPortalOrigensPendentes() {
		return List.of(getTermoCondicaoPortalOrigensPendente());
	}

	public static TermoCondicao getTermoCondicaoOther() {
		var termo = new TermoCondicao();
		termo.setId(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.10");
		termo.setSistema(SistemaTest.getSistema());
		termo.setUsuarios(Arrays.asList(UsuarioTest.getUsuario()));
		return termo;
	}

	@Test
	void teste_termo() {
		var termo = new TermoCondicao();
		termo.setId(UUID.fromString("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404"));
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setSistema(SistemaTest.getSistema());
		termo.setNomeUsuarioCadastro("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));

		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termo.getId().toString());
		assertEquals("Termos e Condições .....", termo.getConteudo());
		assertEquals("0.1", termo.getVersao());
		assertEquals("anônimo", termo.getNomeUsuarioCadastro());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termo.getDataCriacao());
	}
}
