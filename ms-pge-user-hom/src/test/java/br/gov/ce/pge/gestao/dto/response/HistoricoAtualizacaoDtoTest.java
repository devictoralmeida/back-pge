package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.Test;

public class HistoricoAtualizacaoDtoTest {

	public static PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> getResponse() {

		HistoricoAtualizacaoResponseDto historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDataAlterado("21/11/2023 10:57:31");

		Document document = new Document();
		document.put("campoAtualizado", "situacao");
		document.put("valorAntigo", "ATIVA");
		document.put("valorNovo", "INATIVA");

		historico.setDadosAlterados(Arrays.asList(document));

		return PaginacaoResponseDto.fromResultado(Arrays.asList(historico), 1, 1, 1);
	}

	@Test
	public void test_getResponse() {
		PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> response = HistoricoAtualizacaoDtoTest.getResponse();

		assertNotNull(response);
		assertFalse(response.getList().isEmpty());

		HistoricoAtualizacaoResponseDto historico = response.getList().get(0);
		assertEquals("anônimo", historico.getResponsavel());
		assertEquals("21/11/2023 10:57:31", historico.getDataAlterado());
	}

	@Test
	public void test_historico_atualizacao_responseDto() {
		HistoricoAtualizacaoResponseDto historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("usuario");
		historico.setDataAlterado("22/11/2023 12:30:00");

		assertEquals("usuario", historico.getResponsavel());
		assertEquals("22/11/2023 12:30:00", historico.getDataAlterado());
	}

	@Test
	public void test_historico_atualizacao_responseDto_with_document() {
		Document document = new Document();
		document.put("campoAtualizado", "situacao");

		HistoricoAtualizacaoResponseDto historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDataAlterado("21/11/2023 10:57:31");
		historico.setDadosAlterados(Collections.singletonList(document));

		assertNotNull(historico.getDadosAlterados());
		assertFalse(historico.getDadosAlterados().isEmpty());
		assertEquals(document, historico.getDadosAlterados().get(0));
	}
}
