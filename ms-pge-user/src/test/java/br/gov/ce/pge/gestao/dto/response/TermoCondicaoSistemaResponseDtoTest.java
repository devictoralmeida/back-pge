package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TermoCondicaoSistemaResponseDtoTest {

	public static TermoCondicaoSistemaResponseDto getTermoCondicaoSistemaResponseDto() {
		var termo = new TermoCondicaoSistemaResponseDto();
		termo.setId("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404");
		termo.setNomeSistema("Portal da Dívida Ativa");
		termo.setVersao("0.1");
		termo.setNomeUsuario("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		return termo;
	}
	
	public static List<TermoCondicaoSistemaResponseDto> listaTermoCondicaoSistemaResponseDto() {
		return List.of(getTermoCondicaoSistemaResponseDto());
	}
	
	@Test
	void teste_termo_response() {
		var termo = new TermoCondicaoSistemaResponseDto();
		termo.setId("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404");
		termo.setNomeSistema("Portal da Dívida Ativa");
		termo.setVersao("0.1");
		termo.setNomeUsuario("anônimo");
		termo.setDataCriacao(LocalDateTime.of(2024, 2, 8, 16, 30));
		
		
		assertEquals("6cb0b4a6-03bd-40a7-b899-efcbbaa4b404", termo.getId());
		assertEquals("Portal da Dívida Ativa", termo.getNomeSistema());
		assertEquals("0.1", termo.getVersao());
		assertEquals("anônimo", termo.getNomeUsuario());
		assertEquals(LocalDateTime.of(2024, 2, 8, 16, 30), termo.getDataCriacao());
	}
	
}
