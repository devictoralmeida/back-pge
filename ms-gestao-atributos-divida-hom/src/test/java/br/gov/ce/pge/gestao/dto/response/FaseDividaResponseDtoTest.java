package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class FaseDividaResponseDtoTest {

	@Test
	public void test_criacao_fase_divida() {
		FaseDividaResponseDto dto = getResponse();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a", dto.getId());
		Assertions.assertEquals("Fase Cadastrada", dto.getNome());
		Assertions.assertEquals("teste", dto.getDescricao());
		Assertions.assertEquals("0001", dto.getCodigo());
		Assertions.assertEquals(true, dto.getExigeCobranca());
		Assertions.assertEquals(Set.of(TipoCobranca.AJUIZAMENTO), dto.getTipoCobranca());
		Assertions.assertEquals(TipoMovimentacaoFase.AUTOMATICA, dto.getTipoMovimentacao());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
	}

	@Test
	public void test_getters_setters() {
		FaseDividaResponseDto dto = new FaseDividaResponseDto();

		dto.setId("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a");
		Assertions.assertEquals("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a", dto.getId());

		dto.setNome("Fase Cadastrada");
		Assertions.assertEquals("Fase Cadastrada", dto.getNome());

		dto.setDescricao("teste");
		Assertions.assertEquals("teste", dto.getDescricao());

		dto.setSituacao(Situacao.ATIVA);
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());

		dto.setCodigo("0001");
		Assertions.assertEquals("0001", dto.getCodigo());

		dto.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
		Assertions.assertEquals(TipoMovimentacaoFase.AUTOMATICA, dto.getTipoMovimentacao());

		dto.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
		Assertions.assertEquals(Set.of(TipoCobranca.AJUIZAMENTO), dto.getTipoCobranca());

		dto.setExigeCobranca(true);
		Assertions.assertEquals(true, dto.getExigeCobranca());


	}

	public static FaseDividaResponseDto getResponse() {
		var response = new FaseDividaResponseDto();
		response.setId("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a");
		response.setDescricao("teste");
		response.setSituacao(Situacao.ATIVA);
		response.setNome("Fase Cadastrada");
		response.setCodigo("0001");
		response.setExigeCobranca(true);
		response.getTipoCobranca().add(TipoCobranca.AJUIZAMENTO);
		response.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
		return response;
	}

	public static FaseDividaResponseDto getResponseUpdate() {
		var response = new FaseDividaResponseDto();
		response.setId("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a");
		response.setDescricao("teste");
		response.setSituacao(Situacao.INATIVA);
		response.setNome("Fase Cadastrada Atualizada");
		response.setCodigo("0001");
		response.setExigeCobranca(true);
		response.getTipoCobranca().add(TipoCobranca.PROTESTO);
		response.setTipoMovimentacao(TipoMovimentacaoFase.AUTOMATICA);
		return response;
	}

}
