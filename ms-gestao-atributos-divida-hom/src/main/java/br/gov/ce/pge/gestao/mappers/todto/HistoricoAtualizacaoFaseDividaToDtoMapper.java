package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.dto.response.AuditoriaFaseDividaDto;
import br.gov.ce.pge.gestao.dto.response.AuditoriaProdutoServicoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import br.gov.ce.pge.gestao.entity.TipoReceita;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class HistoricoAtualizacaoFaseDividaToDtoMapper extends HistoricoAtualizacaoMapperCommons {

	public static List<HistoricoAtualizacaoResponseDto> processarAuditoriaFaseDivida(AuditoriaFaseDividaDto faseDivida) {
		String dadosAntigos = faseDivida.getDadosAntigos();

		var faseDividaAntiga = dadosAntigos != null ? createObjeto(dadosAntigos) : new FaseDivida();

		List<Document> documents = new ArrayList<>();
		adicionarCampoAlterado(documents, "Código", faseDividaAntiga.getCodigo(), faseDivida.getCodigo());
		adicionarCampoAlterado(documents, "Nome da Fase", faseDividaAntiga.getNome(), faseDivida.getNome());
		adicionarCampoAlterado(documents, "Descrição da Fase", faseDividaAntiga.getDescricao(), faseDivida.getDescricao());
		adicionarCampoAlterado(documents, "Situação", faseDividaAntiga.getSituacao(), faseDivida.getSituacao());
		adicionarCampoAlterado(documents, "Tipo de Movimentação", faseDividaAntiga.getTipoMovimentacao(), faseDivida.getTipoMovimentacao());
		adicionarCampoAlterado(documents, "Exigível", faseDividaAntiga.getExigeCobranca(), faseDivida.getExigeCobranca());

		Map<String, Object> listas = getListasIds(faseDivida);

		adicionarCampoAlteradoTipoCobranca(documents, faseDividaAntiga.getTipoCobranca(), listas);

		return preencherHistorico(faseDivida, documents);

	}

	private static void adicionarCampoAlteradoTipoCobranca(List<Document> documents, Set<TipoCobranca> tipoCobrancas, Map<String, Object> listas) {
		String idsTipoCobrancaAdicionados = (String) listas.get("idsTipoCobrancaAdicionados");
		String idsTipoCobrancaRemovidos = (String) listas.get("idsTipoCobrancaRemovidos");

		List<String> idsAntigos = tipoCobrancas != null ?
				tipoCobrancas.stream().map(Enum::name).collect(Collectors.toList())
				: List.of();
		List<String> idsAux = new ArrayList<>(idsAntigos);
		Document document = new Document();

		if (idsTipoCobrancaRemovidos != null && !idsTipoCobrancaRemovidos.isEmpty()) {
			idsAux.removeAll(Arrays.asList(idsTipoCobrancaRemovidos.split(",")));
		}

		if (idsTipoCobrancaAdicionados != null && !idsTipoCobrancaAdicionados.isEmpty()) {
			idsAux.addAll(Arrays.asList(idsTipoCobrancaAdicionados.split(",")));
		}

		if (!idsAux.equals(idsAntigos)) {
			apendarCampoAlterado(document, "Tipo Cobrança", idsAntigos, idsAux);
			documents.add(document);
		}
	}

	private static Map<String, Object> getListasIds(AuditoriaFaseDividaDto faseDivida) {
		Map<String, Object> listas = new HashMap<>();
		listas.put("idsTipoCobrancaAdicionados", faseDivida.getTipoCobrancasAdicionados());
		listas.put("idsTipoCobrancaRemovidos", faseDivida.getTipoCobrancasRemovidos());
		return listas;
	}

	private static FaseDivida createObjeto(String stringFaseDividaDadosAntigos) {

		var model = new FaseDivida();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var faseDivida = objectMapper.readValue(stringFaseDividaDadosAntigos, FaseDivida.class);
			model.setId(faseDivida.getId());
			model.setCodigo(faseDivida.getCodigo());
			model.setNome(faseDivida.getNome());
			model.setDescricao(faseDivida.getDescricao());
			model.setTipoMovimentacao(faseDivida.getTipoMovimentacao());
			model.setSituacao(faseDivida.getSituacao());
			model.setExigeCobranca(faseDivida.getExigeCobranca());
			model.setTipoCobranca(faseDivida.getTipoCobranca());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return model;
	}

}
