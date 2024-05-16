package br.gov.ce.pge.gestao.mappers.todto;

import java.util.ArrayList;
import java.util.List;

import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.mappers.strategy.AuditoriaMapperStratery;

public class HistoricoAtualizacaoToDtoMapper {
	
	private HistoricoAtualizacaoToDtoMapper() {}
	
    public static List<HistoricoAtualizacaoResponseDto> toDto(List<?> historicos, String historico) {

        List<HistoricoAtualizacaoResponseDto> historicosAlteracoes = new ArrayList<>();

        historicos.forEach(revision -> 
        	historicosAlteracoes.addAll(AuditoriaMapperStratery.mapperHistorico.get(historico).processarAuditoria(revision))
        );

        return historicosAlteracoes;
    }

}
