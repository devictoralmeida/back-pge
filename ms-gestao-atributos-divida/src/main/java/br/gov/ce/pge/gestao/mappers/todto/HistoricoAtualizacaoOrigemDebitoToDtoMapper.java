package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.gov.ce.pge.gestao.contantes.SharedConstantes;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import br.gov.ce.pge.gestao.dto.response.AuditoriaOrigemDebitoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;

public class HistoricoAtualizacaoOrigemDebitoToDtoMapper extends HistoricoAtualizacaoMapperCommons {

	public static List<HistoricoAtualizacaoResponseDto> processarAuditoriaOrigemDebito(AuditoriaOrigemDebitoDto origemDebito) {
		String dadosAntigos = origemDebito.getDadosAntigos();

		var origemDebitoAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new OrigemDebito();

		List<Document> documents = new ArrayList<>();
		adicionarCampoAlterado(documents, "Nome da Origem do Débito", origemDebitoAntigo.getNome(), origemDebito.getNome());
		adicionarCampoAlterado(documents, "Descrição da Origem do Débito", origemDebitoAntigo.getDescricao(), origemDebito.getDescricao());
		adicionarCampoAlterado(documents, "Situação", origemDebitoAntigo.getSituacao(), origemDebito.getSituacao());

		return preencherHistorico(origemDebito, documents);

	}

	private static OrigemDebito createObjeto(String stringOrigemDebitoDadosAntigos) {

		var model = new OrigemDebito();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var origemDebito = objectMapper.readValue(stringOrigemDebitoDadosAntigos, OrigemDebito.class);
			model.setId(origemDebito.getId());
			model.setNome(origemDebito.getNome());
			model.setSituacao(origemDebito.getSituacao());
			model.setDescricao(origemDebito.getDescricao());
		} catch (JsonMappingException e) {
			throw new NegocioException(SharedConstantes.ERRO_MAPEAR_JSON);
		} catch (JsonProcessingException e) {
			throw new NegocioException(SharedConstantes.ERRO_PROCESSAR_JSON);
		}

		return model;
	}

}
