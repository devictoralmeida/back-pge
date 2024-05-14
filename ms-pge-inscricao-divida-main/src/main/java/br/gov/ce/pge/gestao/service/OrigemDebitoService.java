package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;

import java.util.UUID;

public interface OrigemDebitoService {

  OrigemDebitoResponseDto findById(UUID id);

}
