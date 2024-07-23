package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.entity.ProdutoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProdutoServicoService {

	ProdutoServicoResponseDto save(ProdutoServicoRequestDto request, String nomeUsuario);

	ProdutoServicoResponseDto findById(UUID id);

	ProdutoServicoResponseDto update(UUID id, ProdutoServicoUpdateRequestDto request, String nomeUsuario) throws JsonProcessingException;

	List<ProdutoServicoResponseDto> findAll();

	ProdutoServico findByIdModel(UUID id);

	List<ProdutoServicoConsultaFilterResponseDto> findProdutoServicosByFilter(ProdutoServicoFilterRequestDto request);

	void delete(UUID id, String nomeUsuario);

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer size);
}
