package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TermoCondicaoResponseDtoTest {

	public static TermoCondicaoResponseDto getTermoCondicaoResponseDto() {
		var termo = new TermoCondicaoResponseDto();
		termo.setId("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404");
		termo.setNomeSistema("Portal da Dívida Ativa");
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setNomeUsuario("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		return termo;
	}
	
	
	@Test
	void teste_termo() {
		var termo = new TermoCondicaoResponseDto();
		termo.setId("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404");
		termo.setNomeSistema("Portal da Dívida Ativa");
		termo.setConteudo("Termos e Condições .....");
		termo.setVersao("0.1");
		termo.setNomeUsuario("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));

		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termo.getId());
		assertEquals("Portal da Dívida Ativa", termo.getNomeSistema());
		assertEquals("Termos e Condições .....", termo.getConteudo());
		assertEquals("0.1", termo.getVersao());
		assertEquals("anônimo", termo.getNomeUsuario());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termo.getDataCriacao());
	}

}
