package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.AcaoJudicialDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.DividaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.DividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface DividaConsultaService {

    PaginacaoResponseDto<List<DividaFilterResponseDto>> findDividaByFilter(DividaFilterRequestDto request, Integer page, Long limit, String orderBy);

    DividaResponseDto findById(UUID id);

    AcaoJudicialDividaResponseDto findAcoesJudiciaisByDivida(UUID id);

    List<DividaResponseDto> findByIds(List<UUID> ids);

    DividaResponseDto findByNumeroInscricao(String numeroInscricao);

    Divida findByIdModel(UUID id);

}
