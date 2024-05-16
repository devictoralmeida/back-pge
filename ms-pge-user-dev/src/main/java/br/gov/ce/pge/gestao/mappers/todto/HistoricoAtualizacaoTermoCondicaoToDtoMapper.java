package br.gov.ce.pge.gestao.mappers.todto;

import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.response.AuditoriaTermoCondicaoDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class HistoricoAtualizacaoTermoCondicaoToDtoMapper extends HistoricoAtualizacaoMapperCommons {

    public List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object model, Integer page, Integer totalPages) {

        List<AuditoriaTermoCondicaoDto> historys = (List<AuditoriaTermoCondicaoDto>) model;

        List<Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto>> pares = new ArrayList<>();

        agruparRegistros(page, totalPages, historys, pares);

        return getHistoricoAtualizacaoResponseDtos(pares);
    }

    private void agruparRegistros(Integer page, Integer totalPages, List<AuditoriaTermoCondicaoDto> historys, List<Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto>> pares) {
        if(historys.size() > SharedConstant.UNICO_REGISTRO) {
            for (int i = 0; i < historys.size() - SharedConstant.SUBTRACAO_INDICE; i++) {

                AuditoriaTermoCondicaoDto registro1 = historys.get(i);
                AuditoriaTermoCondicaoDto registro2 = historys.get(i + 1);

                Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto> par = Pair.of(registro2, registro1);
                pares.add(par);
            }

            if(totalPages.equals(page)) {
                AuditoriaTermoCondicaoDto registro = historys.get(historys.size() - SharedConstant.SUBTRACAO_INDICE);

                Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto> parInicial = Pair.of(new AuditoriaTermoCondicaoDto(), registro);
                pares.add(parInicial);
            }
        }else {
            AuditoriaTermoCondicaoDto registro = historys.get(SharedConstant.INDICE_INICIAL);

            Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto> par = Pair.of(new AuditoriaTermoCondicaoDto(), registro);
            pares.add(par);
        }
    }

    private List<HistoricoAtualizacaoResponseDto> getHistoricoAtualizacaoResponseDtos(List<Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto>> pares) {
        List<HistoricoAtualizacaoResponseDto> historicosAlteracoes = new ArrayList<>();

        for (Pair<AuditoriaTermoCondicaoDto, AuditoriaTermoCondicaoDto> pair : pares) {

            List<Document> documents = new ArrayList<>();

            adicionarCampoAlterado(documents, "Versão do Termo", pair.getKey().getVersao(), pair.getValue().getVersao());
            adicionarCampoAlterado(documents, "Conteúdo do Termo", pair.getKey().getConteudo(), pair.getValue().getConteudo());

            historicosAlteracoes.addAll(preencherHistorico(pair.getValue(), documents));
        }
        return historicosAlteracoes;
    }
}
