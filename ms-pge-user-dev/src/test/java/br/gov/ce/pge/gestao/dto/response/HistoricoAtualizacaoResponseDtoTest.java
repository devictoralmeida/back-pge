package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HistoricoAtualizacaoResponseDtoTest {

	public static HistoricoAtualizacaoResponseDto getHistoricoAtualizacaoPerfilAcesso() {
		var historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());
		return historico;
	}

	public static HistoricoAtualizacaoResponseDto getHistoricoAtualizacaoPermissao() {
		var historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());
		return historico;
	}

	public static HistoricoAtualizacaoResponseDto getHistoricoAtualizacaoModulo() {
		var historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());
		return historico;
	}

	public static HistoricoAtualizacaoResponseDto getHistoricoAtualizacaoSistema() {
		var historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());
		return historico;
	}

	public static HistoricoAtualizacaoResponseDto getHistoricoAtualizacaoUsuario() {
		var historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());
		return historico;
	}

	public static HistoricoAtualizacaoResponseDto getHistoricoAtualizacaoTermo() {
		var historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());
		return historico;
	}

	@Test
	public void test_historico_atualizacao_responseDto() {
		HistoricoAtualizacaoResponseDto historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDataAlterado("21/11/2023 10:57:31");

		assertEquals("anônimo", historico.getResponsavel());
		assertEquals("21/11/2023 10:57:31", historico.getDataAlterado());
	}

	@Test
	public void test_paginacao_responseDto() {
		List<HistoricoAtualizacaoResponseDto> historicos = new ArrayList<>();

		HistoricoAtualizacaoResponseDto historico1 = new HistoricoAtualizacaoResponseDto();
		historico1.setResponsavel("usuario1");
		historico1.setDataAlterado("22/11/2023 11:30:00");
		historicos.add(historico1);

		HistoricoAtualizacaoResponseDto historico2 = new HistoricoAtualizacaoResponseDto();
		historico2.setResponsavel("usuario2");
		historico2.setDataAlterado("23/11/2023 12:45:00");
		historicos.add(historico2);

		PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> result = PaginacaoResponseDto
				.fromResultado(historicos, 1, 1, 1);

		assertEquals(historicos, result.getList());
	}

	@Test
	public void test_getResponse() {
		PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> paginacaoResponse = HistoricoAtualizacaoDtoTest
				.getResponse();

		assertNotNull(paginacaoResponse);
		assertFalse(paginacaoResponse.getList().isEmpty());
	}

	@Test
	public void test_historico_atualizacao_responseDto_dados() {
		HistoricoAtualizacaoResponseDto historico = new HistoricoAtualizacaoResponseDto();
		historico.setResponsavel("anônimo");
		historico.setDadosAlterados(null);
		historico.setDataAlterado(LocalDateTime.now().toString());

		assertEquals("anônimo", historico.getResponsavel());
		assertNull(historico.getDadosAlterados());
		assertNotNull(historico.getDataAlterado());
	}

	@Test
	public void test_get_historico_atualizacao_perfil_acesso() {
		HistoricoAtualizacaoResponseDto historico = HistoricoAtualizacaoResponseDtoTest
				.getHistoricoAtualizacaoPerfilAcesso();

		assertNotNull(historico);
		assertEquals("anônimo", historico.getResponsavel());
		assertNull(historico.getDadosAlterados());
		assertNotNull(historico.getDataAlterado());
	}

}
