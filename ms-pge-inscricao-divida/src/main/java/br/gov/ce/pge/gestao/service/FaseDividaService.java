package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface FaseDividaService {

    FaseDividaResponseDto findById(UUID id);

    List<FaseDividaResponseDto> findAll();
}
