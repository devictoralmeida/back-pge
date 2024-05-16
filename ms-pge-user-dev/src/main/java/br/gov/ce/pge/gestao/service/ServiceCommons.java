package br.gov.ce.pge.gestao.service;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;

@Service
public interface ServiceCommons<R, M> {
	
	M save(@Valid R request);

	M update(UUID id, @Valid R request) throws JsonProcessingException;

	M findById(UUID id);

	List<M> findAll();

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page);

	void delete(UUID id);

}
