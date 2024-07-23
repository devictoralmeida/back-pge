package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.List;

import br.gov.ce.pge.gestao.dto.response.*;

public class HistoricoAtualizacaoToDtoMapper {

    private HistoricoAtualizacaoToDtoMapper() {}

    public static List<HistoricoAtualizacaoResponseDto> toDto(List<?> historicos) {

        List<HistoricoAtualizacaoResponseDto> historicosAlteracoes = new ArrayList<>();

        historicos.forEach(revision -> {

            if (revision instanceof AuditoriaOrigemDebitoDto) {
            	historicosAlteracoes.addAll(HistoricoAtualizacaoOrigemDebitoToDtoMapper.processarAuditoriaOrigemDebito((AuditoriaOrigemDebitoDto) revision));
            } else if (revision instanceof AuditoriaTipoReceitaDto) {
            	historicosAlteracoes.addAll(HistoricoAtualizacaoTipoReceitaToDtoMapper.processarAuditoriaTipoReceita((AuditoriaTipoReceitaDto) revision));
            } else if(revision instanceof AuditoriaProdutoServicoDto) {
                historicosAlteracoes.addAll(HistoricoAtualizacaoProdutoServicoToDtoMapper.processarAuditoriaProdutoServico((AuditoriaProdutoServicoDto) revision));
            } else if(revision instanceof AuditoriaFaseDividaDto) {
                historicosAlteracoes.addAll(HistoricoAtualizacaoFaseDividaToDtoMapper.processarAuditoriaFaseDivida((AuditoriaFaseDividaDto) revision));
            }
        });

        return historicosAlteracoes;
    }

}
