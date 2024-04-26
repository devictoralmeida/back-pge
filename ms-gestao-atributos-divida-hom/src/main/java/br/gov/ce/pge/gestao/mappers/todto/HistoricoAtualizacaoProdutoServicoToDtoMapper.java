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

import br.gov.ce.pge.gestao.dto.response.AuditoriaProdutoServicoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.entity.ProdutoServico;
import br.gov.ce.pge.gestao.entity.TipoReceita;

public class HistoricoAtualizacaoProdutoServicoToDtoMapper extends HistoricoAtualizacaoMapperCommons {

    public static List<HistoricoAtualizacaoResponseDto> processarAuditoriaProdutoServico(AuditoriaProdutoServicoDto produtoServico) {
        String dadosAntigos = produtoServico.getDadosAntigos();

        var produtoServicoAntigo = dadosAntigos != null ? createObjeto(dadosAntigos) : new ProdutoServico();

        List<Document> documents = new ArrayList<>();
        if(dadosAntigos == null)
            adicionarCampoAlterado(documents, "Código do Produto/Serviço", produtoServicoAntigo.getCodigo(), produtoServico.getCodigo());
        adicionarCampoAlterado(documents, "Descrição do Produto/Serviço", produtoServicoAntigo.getDescricao(), produtoServico.getDescricao());
        adicionarCampoAlterado(documents, "Situação", produtoServicoAntigo.getSituacao(), produtoServico.getSituacao());

        Map<String, Object> listas = getListasIds(produtoServico);

        adicionarCampoAlteradoTipoReceita(documents, produtoServicoAntigo.getTipoReceitas(), listas);

        return preencherHistorico(produtoServico, documents);
    }
    
    private static void adicionarCampoAlteradoTipoReceita(List<Document> documents, List<TipoReceita> tipoReceitas, Map<String, Object> listas) {
        String idsTipoReceitasAdicionados = (String) listas.get("idsTipoReceitaAdicionados");
        String idsTipoReceitasRemovidos = (String) listas.get("idsTipoReceitaRemovidos");

        List<String> idsAntigos = tipoReceitas != null ?
                tipoReceitas.stream().map(tipoReceita -> tipoReceita.getId().toString()).collect(Collectors.toList())
                : Arrays.asList();
        List<String> idsAux = new ArrayList<>(idsAntigos);
        Document document = new Document();

        if (idsTipoReceitasRemovidos != null && !idsTipoReceitasRemovidos.isEmpty()) {
            idsAux.removeAll(Arrays.asList(idsTipoReceitasRemovidos.split(",")));
        }

        if (idsTipoReceitasAdicionados != null && !idsTipoReceitasAdicionados.isEmpty()) {
            idsAux.addAll(Arrays.asList(idsTipoReceitasAdicionados.split(",")));
        }

        if (!idsAux.equals(idsAntigos)) {
            apendarCampoAlterado(document, "Tipo Receita", idsAntigos, idsAux);
            documents.add(document);
        }
    }

	private static Map<String, Object> getListasIds(AuditoriaProdutoServicoDto produtoServico) {
    	Map<String, Object> listas = new HashMap<>();
		listas.put("idsTipoReceitaAdicionados", produtoServico.getTipoReceitasAdicionados());
		listas.put("idsTipoReceitaRemovidos", produtoServico.getTipoReceitasRemovidos());
		return listas;
	}

    private static ProdutoServico createObjeto(String stringProdutoServicoDadosAntigo) {

        var model = new ProdutoServico();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var produtoServico = objectMapper.readValue(stringProdutoServicoDadosAntigo, ProdutoServico.class);
            model.setId(produtoServico.getId());
            model.setCodigo(produtoServico.getCodigo());
            model.setSituacao(produtoServico.getSituacao());
            model.setDescricao(produtoServico.getDescricao());
            model.setTipoReceitas(produtoServico.getTipoReceitas());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return model;
    }

}
