package br.gov.ce.pge.gestao.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProdutoServicoFilterRequestDtoTest {

	public static ProdutoServicoFilterRequestDto getFilterCamposNull() {
		var dto = new ProdutoServicoFilterRequestDto();
		return dto;
	}

	public static ProdutoServicoFilterRequestDto getProdutoServico() {
		var dto = new ProdutoServicoFilterRequestDto();
		dto.setId(UUID.fromString("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));
		dto.setCodigo("00001");
		dto.setDescricao("produto servico 1");
		dto.setSituacao(Situacao.ATIVA);
		return dto;
	}

	public static ProdutoServicoFilterRequestDto getFilterRequest() {
		var dto = new ProdutoServicoFilterRequestDto();
		dto.setId(null);
		dto.setCodigo(null);
		dto.setDescricao(null);
		dto.setSituacao(null);
		return dto;
	}

	@Test
	void test_filters() {
		ProdutoServicoFilterRequestDto dto = getProdutoServico();

		Map<String, Object> filters = dto.filters();
		assertEquals("00001", filters.get("codigo"));
		assertEquals("%PRODUTO SERVICO 1%", filters.get("descricao"));
		assertEquals(Situacao.ATIVA, filters.get("situacao"));

	}

	@Test
	void test_filtros_nulos() {
		ProdutoServicoFilterRequestDto dto = getProdutoServico();

		dto.setCodigo(null);
		dto.setDescricao(null);
		dto.setSituacao(null);
		dto.setIdsTipoReceitas(null);

		Map<String, Object> filters2 = dto.filters();

		assertNull(filters2.get("descricao"));
		assertNull(filters2.get("situacao"));
		assertNull(filters2.get("codigo"));
		assertNull(filters2.get("codigo"));
	}

	@Test
	void test_filtros_vazios() {
		ProdutoServicoFilterRequestDto dto = getProdutoServico();

		dto.setCodigo("");
		dto.setDescricao("");
		dto.setSituacao(null);
		dto.setIdsTipoReceitas(Collections.emptyList());

		Map<String, Object> filters2 = dto.filters();

		assertNull(filters2.get("descricao"));
		assertNull(filters2.get("situacao"));
		assertNull(filters2.get("codigo"));
		assertNull(filters2.get("codigo"));
	}
	
	@Test
	void test_filtros_by_idTiposReceita() {
		ProdutoServicoFilterRequestDto dto = getProdutoServico();

		dto.setCodigo(null);
		dto.setDescricao(null);
		dto.setSituacao(null);
		dto.setIdsTipoReceitas(List.of("1b95724c-4fa2-4e2b-b40e-5518ff5eb8d1"));

		Map<String, Object> filters2 = dto.filters();

		assertNull(filters2.get("descricao"));
		assertNull(filters2.get("situacao"));
		assertNull(filters2.get("codigo"));
		assertNull(filters2.get("codigo"));
	}
}
