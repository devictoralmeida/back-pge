package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProdutoServicoService {

    ProdutoServicoResponseDto findById(UUID id);

}
