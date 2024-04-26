package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ce.pge.gestao.dto.response.AuditoriaTipoReceitaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;
import br.gov.ce.pge.gestao.entity.TipoReceita;

public class HistoricoAtualizacaoTipoReceitaToDtoMapper extends HistoricoAtualizacaoMapperCommons {

	public static List<HistoricoAtualizacaoResponseDto> processarAuditoriaTipoReceita(AuditoriaTipoReceitaDto tipoReceita) {
		String dadosAntigos = tipoReceita.getDadosAntigos();

		TipoReceita tipoReceitaAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new TipoReceita();

		List<Document> documents = new ArrayList<>();
		if(dadosAntigos == null)
			adicionarCampoAlterado(documents, "Código do Tipo Receita", tipoReceitaAntigo.getCodigo(),tipoReceita.getCodigo());
		adicionarCampoAlterado(documents, "Descrição do Tipo Receita", tipoReceitaAntigo.getDescricao(),tipoReceita.getDescricao());
		adicionarCampoAlterado(documents, "Situação", tipoReceitaAntigo.getSituacao(),tipoReceita.getSituacao());
		adicionarCampoAlterado(documents, "Natureza", tipoReceitaAntigo.getNatureza(),tipoReceita.getNatureza());

		Map<String, Object> listas = getListasIds(tipoReceita);

		adicionarCampoAlteradoOrigemDebito(documents, tipoReceitaAntigo.getOrigemDebitos(), listas);

		return preencherHistorico(tipoReceita, documents);

	}

	private static Map<String, Object> getListasIds(AuditoriaTipoReceitaDto tipoReceita) {
		Map<String, Object> listas = new HashMap<>();
		listas.put("idsOrigemDebitoAdicionados", tipoReceita.getOrigensDebitosAdicionados());
		listas.put("idsOrigemDebitoRemovidos", tipoReceita.getOrigensDebitosRemovidos());
		return listas;
	}
	
	private static void adicionarCampoAlteradoOrigemDebito(List<Document> documents, List<OrigemDebito> listaOrigemDebitosAntigos, Map<String, Object> listas) {
	    String idOrigensDebitosAdicionados = (String) listas.get("idsOrigemDebitoAdicionados");
	    String idOrigensDebitosRemovidos = (String) listas.get("idsOrigemDebitoRemovidos");

	    Document document = new Document();

	    List<String> idsAntigos = listaOrigemDebitosAntigos != null ? listaOrigemDebitosAntigos.stream()
	            .map(origemDebito -> origemDebito.getId().toString())
	            .collect(Collectors.toList()) : Arrays.asList();

	    List<String> idsAux = new ArrayList<>(idsAntigos);

	    if (idOrigensDebitosRemovidos != null && !idOrigensDebitosRemovidos.isEmpty()) {
	        idsAux.removeAll(Arrays.asList(idOrigensDebitosRemovidos.split(",")));
	    }

	    if (idOrigensDebitosAdicionados != null && !idOrigensDebitosAdicionados.isEmpty()) {
	        idsAux.addAll(Arrays.asList(idOrigensDebitosAdicionados.split(",")));
	    }

	    if (!idsAux.equals(idsAntigos)) {
	        apendarCampoAlterado(document, "Origem do Débito", idsAntigos, idsAux);
	        documents.add(document);
	    }
	}

	private static TipoReceita createObjeto(String stringTipoReceitaDadosAntigos) {

		var model = new TipoReceita();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var tipoReceita = objectMapper.readValue(stringTipoReceitaDadosAntigos, TipoReceita.class);
			model.setId(tipoReceita.getId());
			model.setCodigo(tipoReceita.getCodigo());
			model.setSituacao(tipoReceita.getSituacao());
			model.setDescricao(tipoReceita.getDescricao());
			model.setNatureza(tipoReceita.getNatureza());
			model.setOrigemDebitos(tipoReceita.getOrigemDebitos());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return model;
	}

}
