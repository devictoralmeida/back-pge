package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.dto.response.AuditoriaSistemaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.Modulo;
import br.gov.ce.pge.gestao.entity.Sistema;

public class HistoricoAtualizacaoSistemaToDtoMapper extends HistoricoAtualizacaoMapperCommons implements AuditoriaToDtoMapper {

	public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object model) {
		
		var modelAtual = (AuditoriaSistemaDto) model;
		
		String dadosAntigos = modelAtual.getDadosAntigos();

		var modelAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new Sistema();

		List<Document> documents = new ArrayList<>();
		adicionarCampoAlterado(documents, "Nome do Sistema", modelAntigo.getNome() , modelAtual.getNome());

		Map<String, Object> listas = getListasIds(modelAtual);

		adicionarCampoAlteradoPermissoes(documents, modelAntigo.getModulos(), listas);

		return preencherHistorico(modelAtual, documents);
	}

	private static Map<String, Object> getListasIds(AuditoriaSistemaDto model) {
		Map<String, Object> listas = new HashMap<>();
		listas.put("idsAdicionados", model.getIdsAdicionados());
		listas.put("idsRemovidos", model.getIdsRemovidos());
		return listas;
	}
	
	private static void adicionarCampoAlteradoPermissoes(List<Document> documents, List<Modulo> listaModulosAntigos, Map<String, Object> listas) {
	    String idsAdicionados = (String) listas.get("idsAdicionados");
	    String idsRemovidos = (String) listas.get("idsRemovidos");

	    Document document = new Document();

	    List<String> idsAntigos = listaModulosAntigos != null ? listaModulosAntigos.stream()
	            .map(model -> model.getId().toString())
	            .collect(Collectors.toList()) : Arrays.asList();

	    List<String> idsAux = new ArrayList<>(idsAntigos);

	    if (idsRemovidos != null && !idsRemovidos.isEmpty()) {
	        idsAux.removeAll(Arrays.asList(idsRemovidos.split(",")));
	    }

	    if (idsAdicionados != null && !idsAdicionados.isEmpty()) {
	        idsAux.addAll(Arrays.asList(idsAdicionados.split(",")));
	    }

	    if (!idsAux.equals(idsAntigos)) {
	        apendarCampoAlterado(document, "Modulos", idsAntigos, idsAux);
	        documents.add(document);
	    }
	}

	private static Sistema createObjeto(String stringSistemaDadosAntigos) {

		var model = new Sistema();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var sistema = objectMapper.readValue(stringSistemaDadosAntigos, Sistema.class);
			model.setId(sistema.getId());
			model.setNome(sistema.getNome());
			model.setModulos(sistema.getModulos());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
}
