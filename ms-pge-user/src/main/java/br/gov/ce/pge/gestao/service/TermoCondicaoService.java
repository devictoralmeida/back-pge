package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TermoCondicaoSistemaResponseDto;
import br.gov.ce.pge.gestao.entity.TermoCondicao;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public interface TermoCondicaoService {

	void updateExistente(UUID id, @Valid TermoCondicaoRequestDto request);

	void update(UUID id, @Valid TermoCondicaoRequestDto request, String nomeUsuario);

	TermoCondicao findByIdModel(UUID id);

	TermoCondicaoResponseDto findById(UUID id);

	List<TermoCondicaoSistemaResponseDto> findBySistema();

	List<TermoCondicao> findByNomeSistema();

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer size);
}
