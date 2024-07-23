package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public interface ServiceCommons<R, M> {

	M save(@Valid R request);

	M update(UUID id, @Valid R request) throws JsonProcessingException;

	M findById(UUID id);

	List<M> findAll();

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer size);

	void delete(UUID id);

}
