package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.dto.response.AuditoriaModuloDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.Modulo;
import br.gov.ce.pge.gestao.mappers.util.PermissaoUtil;

public class HistoricoAtualizacaoModuloToDtoMapper extends HistoricoAtualizacaoMapperCommons implements AuditoriaToDtoMapper {

	@Override
	public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object  model) {
		var modelAtual = (AuditoriaModuloDto) model;
		
		String dadosAntigos = modelAtual.getDadosAntigos();

		var modelAntigo =  dadosAntigos != null ? createObjeto(dadosAntigos) : new Modulo();

		List<Document> documents = new ArrayList<>();
		adicionarCampoAlterado(documents, "Nome do Módulo", modelAntigo.getNome() , modelAtual.getNome());

		Map<String, Object> listas = getListasIds(modelAtual);

		PermissaoUtil.adicionarCampoAlteradoPermissoes(documents, modelAntigo.getPermissoes(), listas);

		return preencherHistorico(modelAtual, documents);

	}

	private static Map<String, Object> getListasIds(AuditoriaModuloDto tipoReceita) {
		Map<String, Object> listas = new HashMap<>();
		listas.put("idsAdicionados", tipoReceita.getIdsAdicionados());
		listas.put("idsRemovidos", tipoReceita.getIdsRemovidos());
		return listas;
	}
	
	private static Modulo createObjeto(String stringTipoReceitaDadosAntigos) {

		var model = new Modulo();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var modulo = objectMapper.readValue(stringTipoReceitaDadosAntigos, Modulo.class);
			model.setId(modulo.getId());
			model.setNome(modulo.getNome());
			model.setPermissoes(modulo.getPermissoes());
		} catch (JsonMappingException e) {
			throw new NegocioException(MessageCommonsContanst.ERRO_MAPEAR_JSON);
		} catch (JsonProcessingException e) {
			throw new NegocioException(MessageCommonsContanst.ERRO_PROCESSAR_JSON);
		}
		
		return model;
	}


}
