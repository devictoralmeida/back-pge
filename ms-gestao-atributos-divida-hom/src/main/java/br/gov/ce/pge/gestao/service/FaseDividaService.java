package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.FluxoFaseDividaWrapperRequestDto;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.FluxoFaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.HistoricoAtualizacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface FaseDividaService {


    FaseDividaResponseDto save(FaseDividaRequestDto request);

    FaseDividaResponseDto update(UUID id, FaseDividaRequestDto request) throws JsonProcessingException;

    List<FaseDividaResponseDto> findAll();

    FaseDividaResponseDto findById(UUID id);

    List<FaseDividaResponseDto> findByFilter(FaseDividaFilterRequestDto request);

    void delete(UUID id);

    PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> viewChangeHistoryById(UUID id, Integer page);

    void salvarFluxoFases(FluxoFaseDividaWrapperRequestDto request);

    List<FluxoFaseDividaResponseDto> obterFluxoFase();

    List<FaseDividaResponseDto> findSemelhantes(String nome);
}
