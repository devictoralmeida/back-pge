package br.gov.ce.pge.gestao.dto.response;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;

public class TipoReceitaResponseDtoTest {

	public static TipoReceitaResponseDto getTipoReceita() {
		var dto = new TipoReceitaResponseDto();
		dto.setId(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"));
		dto.setCodigo("0001");
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.TRIBUTARIA);
		dto.setSituacao(Situacao.ATIVA);
		dto.setOrigemDebitos(List.of(UUID.fromString("a2b9e6a7-81c4-4d8d-b0af-4424dbb9e18a")));
		return dto;
	}
	
	@Test
	public void test_criacao_tipo_receita() {
		TipoReceitaResponseDto dto = getTipoReceita();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals(UUID.fromString("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed"), dto.getId());
		Assertions.assertEquals("Receita 01", dto.getDescricao());
		Assertions.assertEquals("0001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA, dto.getSituacao());
		Assertions.assertEquals(Natureza.TRIBUTARIA, dto.getNatureza());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
	}

}
