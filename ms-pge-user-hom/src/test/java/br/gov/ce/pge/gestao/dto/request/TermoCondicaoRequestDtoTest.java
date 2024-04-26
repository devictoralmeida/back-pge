package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TermoCondicaoRequestDtoTest {

	public static TermoCondicaoRequestDto getRequest() {
		var request = new TermoCondicaoRequestDto();
		request.setConteudo("Novo Termos e Condições");
		return request;
	}

	@Test
	void test_get_set_conteudo() {
		var termoCondicaoRequestDto = new TermoCondicaoRequestDto();
		String conteudo = "Conteúdo do termo de condição";

		termoCondicaoRequestDto.setConteudo(conteudo);

		String conteudoObtido = termoCondicaoRequestDto.getConteudo();

		assertEquals(conteudo, conteudoObtido);
	}
}
