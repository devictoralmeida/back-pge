package br.gov.ce.pge.gestao.service;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.TipoReceita;

@Service
public interface TipoReceitaService {

	TipoReceitaResponseDto save(TipoReceitaRequestDto request);
	
	TipoReceitaResponseDto findById(UUID id);
	
	TipoReceitaResponseDto update(UUID id, TipoReceitaUpdateRequestDto request) throws JsonProcessingException;
	
	List<TipoReceitaResponseDto> findAll();
	
	TipoReceita findByIdModel(UUID id);
	
	List<TipoReceitaConsultaFilterResponseDto> findTipoReceitasByFilter(TipoReceitaFilterRequestDto request);
	
	void delete(UUID id);

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page);

}
