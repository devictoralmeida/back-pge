package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;

public class TipoReceitaFilterRequestDtoTest {
	
	public static TipoReceitaFilterRequestDto getFilterCamposNull() {
		var dto = new TipoReceitaFilterRequestDto();
		
		return dto;
	}


	public static TipoReceitaFilterRequestDto getTipoReceita() {
		var dto = new TipoReceitaFilterRequestDto();
		dto.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		dto.setCodigo("0001");
		dto.setDescricao("tipo receita 1");
		dto.setSituacao(Situacao.ATIVA);
		return dto;
	}

	public static TipoReceitaFilterRequestDto getFilterRequest() {
		var dto = new TipoReceitaFilterRequestDto();
		dto.setId(null);
		dto.setCodigo(null);
		dto.setDescricao(null);
		dto.setSituacao(null);
		return dto;
	}

	@Test
	void test_filters() {
		TipoReceitaFilterRequestDto dto = getTipoReceita();

		Map<String, Object> filters = dto.filters();
		assertEquals("0001", filters.get("codigo"));
		assertEquals("%TIPO RECEITA 1%", filters.get("descricao"));
		assertEquals(Situacao.ATIVA, filters.get("situacao"));

		dto.setNatureza(null);
		dto.setCodigo("");
		dto.setDescricao("");
		dto.setOrigemDebitos(Collections.emptyList());
		dto.setSituacao(null);

		Map<String, Object> filters2 = dto.filters();

		assertNull(filters2.get("natureza"));
		assertNull(filters2.get("codigo"));
		assertNull(filters2.get("descricao"));
		assertNull(filters2.get("idsOrigem"));
		assertNull(filters2.get("situacao"));
		
		
		dto.setNatureza(null);
		dto.setCodigo(null);
		dto.setDescricao(null);
		dto.setOrigemDebitos(null);
		dto.setSituacao(null);

		Map<String, Object> filters3 = dto.filters();

		assertNull(filters3.get("natureza"));
		assertNull(filters3.get("codigo"));
		assertNull(filters3.get("descricao"));
		assertNull(filters3.get("idsOrigem"));
		assertNull(filters3.get("situacao"));
		
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setCodigo(null);
		dto.setDescricao(null);
		dto.setOrigemDebitos(List.of("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		dto.setSituacao(null);

		Map<String, Object> filters4 = dto.filters();

		assertNotNull(filters4.get("natureza"));
		assertNull(filters4.get("codigo"));
		assertNull(filters4.get("descricao"));
		assertNotNull(filters4.get("idsOrigem"));
		assertNull(filters4.get("situacao"));
	}
}
