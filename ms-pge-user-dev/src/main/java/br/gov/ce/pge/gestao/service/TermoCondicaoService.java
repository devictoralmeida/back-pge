package br.gov.ce.pge.gestao.service;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDto;
import br.gov.ce.pge.gestao.entity.TermoCondicao;

@Service
public interface TermoCondicaoService {

	void updateExistente(UUID id, @Valid TermoCondicaoRequestDto request);

	void update(UUID id, @Valid TermoCondicaoRequestDto request);

	TermoCondicao findByIdModel(UUID id);
	
	TermoCondicaoResponseDto findById(UUID id);
	
	List<TermoCondicaoSistemaResponseDto> findBySistema();
	
	List<TermoCondicao> findByNomeSistema();

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page);
}
