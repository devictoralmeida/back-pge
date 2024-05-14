package br.gov.ce.pge.gestao.service;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.entity.OrigemDebito;

@Service
public interface OrigemDebitoService {

	OrigemDebitoResponseDto save(OrigemDebitoRequestDto request);

	OrigemDebitoResponseDto update(UUID id, @Valid OrigemDebitoRequestDto request) throws JsonProcessingException;
	
	OrigemDebitoResponseDto findById(UUID id);
	
	OrigemDebito findByIdModel(UUID id);

	List<OrigemDebitoResponseDto> findAll();

	List<OrigemDebitoConsultaResponseDto> findOrigemDebitoByFilter(OrigemDebitoFilterRequestDto request);

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page);
	
	void delete(UUID id);
}
