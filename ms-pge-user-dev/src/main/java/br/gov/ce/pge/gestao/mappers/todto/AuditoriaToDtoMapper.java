package br.gov.ce.pge.gestao.mappers.todto;

import java.util.List;

import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;

public interface AuditoriaToDtoMapper {
	
	List<HistoricoAtualizacaoResponseDto> processarAuditoria(Object revision);
}
