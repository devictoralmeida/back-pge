package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.AuditoriaOrigemDebitoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.shared.exception.BadValueException;

class HistoricoAtualizacaoOrigemDebitoToDtoMapperTest {

	@Test
	public void test_processar_auditoria_origem_debito_dados_antigos_nulo() {

		AuditoriaOrigemDebitoDto origemDebitoDto = new AuditoriaOrigemDebitoDto();
		origemDebitoDto.setDadosAntigos(null);

		List<HistoricoAtualizacaoResponseDto> historicos = HistoricoAtualizacaoOrigemDebitoToDtoMapper
				.processarAuditoriaOrigemDebito(origemDebitoDto);

		assertTrue(historicos.isEmpty());
	}

}
