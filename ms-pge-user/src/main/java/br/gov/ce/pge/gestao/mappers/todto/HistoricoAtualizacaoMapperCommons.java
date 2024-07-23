package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import br.gov.ce.pge.gestao.dto.response.AuditoriaDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.shared.util.DateUtil;

public abstract class HistoricoAtualizacaoMapperCommons {
	
	protected HistoricoAtualizacaoMapperCommons() {}
	
    public static void adicionarCampoAlterado(List<Document> documents, String nomeCampo, Object valorAntigo, Object valorNovo) {
        Document campoAlterado = new Document();

        if(valorAntigo != null)
            valorAntigo = valorAntigo.toString().replace("'", "");

        if(valorNovo != null) {
            valorNovo = valorNovo.toString().replace("'", "");

            if (!valorNovo.equals(valorAntigo)) {
                apendarCampoAlterado(campoAlterado, nomeCampo, valorAntigo, valorNovo);
                documents.add(campoAlterado);
            }
        }
    }

    public static void apendarCampoAlterado(Document document, String nomeCampo, Object valorAntigo, Object valorNovo) {
        document.append("campoAtualizado", nomeCampo).append("valorAntigo", valorAntigo).append("valorNovo", valorNovo);
    }

    public static List<HistoricoAtualizacaoResponseDto> preencherHistorico(AuditoriaDto auditoria, List<Document> documents) {
    	if(documents.isEmpty()) {
			return Arrays.asList();
    	}
    	
    	List<HistoricoAtualizacaoResponseDto> historicosAlteracoes = new ArrayList<>();
    	
    	var  historico = new HistoricoAtualizacaoResponseDto();
        historico.setDataAlterado(DateUtil.formatDate(auditoria.getDataMovimento(), "dd/MM/yyyy HH:mm:ss"));
        historico.setResponsavel(auditoria.getNomeUsuario());
        historico.setDadosAlterados(Arrays.asList(documents));

        historicosAlteracoes.add(historico);
        return historicosAlteracoes;
    }

}
