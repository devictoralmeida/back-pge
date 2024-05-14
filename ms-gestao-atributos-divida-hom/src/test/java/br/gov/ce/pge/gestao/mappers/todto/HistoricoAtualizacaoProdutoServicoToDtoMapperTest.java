package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.dto.response.AuditoriaProdutoServicoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.shared.exception.BadValueException;

class HistoricoAtualizacaoProdutoServicoToDtoMapperTest {

	@Test
	public void test_processar_auditoria_produto_servico_dados_antigos_nulo() {

		AuditoriaProdutoServicoDto produtoServicoDto = new AuditoriaProdutoServicoDto();
		produtoServicoDto.setDadosAntigos(null);

		List<HistoricoAtualizacaoResponseDto> historicos = HistoricoAtualizacaoProdutoServicoToDtoMapper
				.processarAuditoriaProdutoServico(produtoServicoDto);

		assertTrue(historicos.isEmpty());
	}

	@Test
	public void test_processar_auditoria_produto_servico_sem_historico() {

		AuditoriaProdutoServicoDto produtoServicoDto = new AuditoriaProdutoServicoDto();
		produtoServicoDto.setDadosAntigos(null);

		List<HistoricoAtualizacaoResponseDto> historicos = HistoricoAtualizacaoProdutoServicoToDtoMapper
				.processarAuditoriaProdutoServico(produtoServicoDto);

		assertEquals(Collections.emptyList(), historicos);
	}

}
