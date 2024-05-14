package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.List;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.dto.response.AuditoriaPermissaoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.Permissao;

public class HistoricoAtualizacaoPermissaoToDtoMapper extends HistoricoAtualizacaoMapperCommons implements AuditoriaToDtoMapper {

	public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object model) {
		
		var modelAtual = (AuditoriaPermissaoDto) model;
		
		String dadosAntigos = modelAtual.getDadosAntigos();

		var modelAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new Permissao();

		List<Document> documents = new ArrayList<>();
		adicionarCampoAlterado(documents, "Nome da Permissão", modelAntigo.getNome(), modelAtual.getNome());

		return preencherHistorico(modelAtual, documents);
		
	}
	
	private static Permissao createObjeto(String stringPermissaoDadosAntigos) {

		var model = new Permissao();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var permissao = objectMapper.readValue(stringPermissaoDadosAntigos, Permissao.class);
			model.setId(permissao.getId());
			model.setNome(permissao.getNome());
		} catch (JsonMappingException e) {
			throw new NegocioException(MessageCommonsContanst.ERRO_MAPEAR_JSON);
		} catch (JsonProcessingException e) {
			throw new NegocioException(MessageCommonsContanst.ERRO_PROCESSAR_JSON);
		}
		
		return model;
	}

}
