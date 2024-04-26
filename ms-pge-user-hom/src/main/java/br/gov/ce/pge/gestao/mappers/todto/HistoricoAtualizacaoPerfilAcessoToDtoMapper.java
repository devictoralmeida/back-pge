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

import br.gov.ce.pge.gestao.dto.response.AuditoriaPerfilAcessoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.mappers.util.PermissaoUtil;

public class HistoricoAtualizacaoPerfilAcessoToDtoMapper extends HistoricoAtualizacaoMapperCommons implements AuditoriaToDtoMapper {

	public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object model) {
		var modelAtual = (AuditoriaPerfilAcessoDto) model;
		
		String dadosAntigos = modelAtual.getDadosAntigos();

		var modelAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new PerfilAcesso();

		List<Document> documents = new ArrayList<>();
		adicionarCampoAlterado(documents, "Nome do Perfil Acesso", modelAntigo.getNome() , modelAtual.getNome());
		adicionarCampoAlterado(documents, "Situação Perfil Acesso", modelAntigo.getSituacao(), modelAtual.getSituacao());

		Map<String, Object> listas = getListasIds(modelAtual);

		adicionarCampoAlteradoSistemas(documents, modelAntigo.getSistemas(), listas);
		PermissaoUtil.adicionarCampoAlteradoPermissoes(documents, modelAntigo.getPermissoes(), listas);

		return preencherHistorico(modelAtual, documents);
	}

	private static Map<String, Object> getListasIds(AuditoriaPerfilAcessoDto model) {
		Map<String, Object> listas = new HashMap<>();
		listas.put("idsAdicionados", model.getIdsAdicionados());
		listas.put("idsRemovidos", model.getIdsRemovidos());
		listas.put("idsSistemaAdicionados", model.getIdsSistemaAdicionados());
		listas.put("idsSistemaRemovidos", model.getIdsSistemaRemovidos());
		return listas;
	}
	
	private static void adicionarCampoAlteradoSistemas(List<Document> documents, List<Sistema> listaSistemaAntigos, Map<String, Object> listas) {
	    String idsAdicionados = (String) listas.get("idsSistemaAdicionados");
	    String idsRemovidos = (String) listas.get("idsSistemaRemovidos");

	    Document document = new Document();

	    List<String> idsAntigos = listaSistemaAntigos != null ? listaSistemaAntigos.stream()
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
	        apendarCampoAlterado(document, "Sistemas", idsAntigos, idsAux);
	        documents.add(document);
	    }
	}

	private static PerfilAcesso createObjeto(String stringPerfilAcessoDadosAntigos) {

		var model = new PerfilAcesso();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var perfilAcesso = objectMapper.readValue(stringPerfilAcessoDadosAntigos, PerfilAcesso.class);
			model.setId(perfilAcesso.getId());
			model.setNome(perfilAcesso.getNome());
			model.setSituacao(perfilAcesso.getSituacao());
			model.setPermissoes(perfilAcesso.getPermissoes());
			model.setSistemas(perfilAcesso.getSistemas());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return model;
		
	}

}
