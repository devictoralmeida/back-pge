package br.gov.ce.pge.gestao.service;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoResponseDto;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;

@Service
public interface PerfilAcessoService {

	PerfilAcesso save(@Valid PerfilAcessoRequestDto request);

	PerfilAcesso update(UUID id, @Valid PerfilAcessoRequestDto request) throws JsonProcessingException;

	PerfilAcessoResponseDto findById(UUID id);
	
	PerfilAcesso findByIdModel(UUID id);

	List<PerfilAcesso> findAll();

	PaginacaoResponseDto<List<PerfilAcessoFilterResponseDto>> findByFilter(PerfilAcessoFilterRequestDto request, Integer page, String orderBy);

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page);

	void delete(UUID id);
	
}
