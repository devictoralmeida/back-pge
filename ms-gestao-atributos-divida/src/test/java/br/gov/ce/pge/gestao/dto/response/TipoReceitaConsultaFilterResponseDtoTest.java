package br.gov.ce.pge.gestao.dto.response;

import java.util.List;

import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TipoReceitaConsultaFilterResponseDtoTest {

	@Test
	public void test_criacao_produto_servico() {
		TipoReceitaConsultaFilterResponseDto dto = getResponseConsulta();

		Assertions.assertNotNull(dto);
		Assertions.assertEquals("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed", dto.getId());
		Assertions.assertEquals("Receita 01", dto.getDescricao());
		Assertions.assertEquals("0001", dto.getCodigo());
		Assertions.assertEquals(Situacao.ATIVA.toString(), dto.getSituacao());
		long tamanhoEsperado = 1;
		Assertions.assertEquals(tamanhoEsperado, dto.getOrigemDebitos().size());
	}


	public static TipoReceitaConsultaFilterResponseDto getResponseConsulta() {
		var dto = new TipoReceitaConsultaFilterResponseDto();
		dto.setId("3e4a0c60-22e6-496d-a4b4-fab372b0c0ed");
		dto.setCodigo("0001");
		dto.setDescricao("Receita 01");
		dto.setNatureza(Natureza.TRIBUTARIA.toString());
		dto.setSituacao(Situacao.ATIVA.toString());
		dto.setOrigemDebitos(List.of(OrigemDebitoConsultaResponseDtoTest.getOrigemDebito()));
		return dto;
	}

}
