package br.gov.ce.pge.gestao.mappers.todto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.entity.AuditoriaTest;

class HistoricoAtualizacaoMapperCommonsTest {

	@Test
	void test() throws JsonProcessingException {
		
	  var retorno = HistoricoAtualizacaoMapperCommons.preencherHistorico(AuditoriaTest.getAuditoriaModulo(), List.of());
	  assertNotNull(retorno);
	  assertEquals(0, retorno.size());
	}

}
