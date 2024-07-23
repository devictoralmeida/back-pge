package br.gov.ce.pge.gestao.mappers.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import br.gov.ce.pge.gestao.entity.Permissao;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoMapperCommons;

public class PermissaoUtil {
	
	private PermissaoUtil() {}
	
	public static void adicionarCampoAlteradoPermissoes(List<Document> documents, List<Permissao> listaPermissoesAntigos, Map<String, Object> listas) {
	    String idsAdicionados = (String) listas.get("idsAdicionados");
	    String idsRemovidos = (String) listas.get("idsRemovidos");

	    Document document = new Document();

	    List<String> idsAntigos = listaPermissoesAntigos != null ? listaPermissoesAntigos.stream()
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
	    	HistoricoAtualizacaoMapperCommons.apendarCampoAlterado(document, "Permissões", idsAntigos, idsAux);
	        documents.add(document);
	    }
	}

}
