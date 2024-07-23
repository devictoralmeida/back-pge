package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.PerfilAcessoResponseDto;
import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public interface PerfilAcessoService {

	PerfilAcesso save(@Valid PerfilAcessoRequestDto request, String nomeUsuario);

	PerfilAcesso update(UUID id, @Valid PerfilAcessoRequestDto request, String nomeUsuario) throws JsonProcessingException;

	PerfilAcessoResponseDto findById(UUID id);

	PerfilAcesso findByIdModel(UUID id);

	List<PerfilAcesso> findAll();

	PaginacaoResponseDto<List<PerfilAcessoFilterResponseDto>> findByFilter(PerfilAcessoFilterRequestDto request, Integer page, String orderBy, Integer size);

	PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> findHistorys(UUID id, Integer page, Integer size);

	void delete(UUID id);

}
