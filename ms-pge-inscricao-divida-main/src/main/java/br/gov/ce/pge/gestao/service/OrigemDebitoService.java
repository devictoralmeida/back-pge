package br.gov.ce.pge.gestao.service;

import java.util.UUID;

import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;

public interface OrigemDebitoService {
	
	OrigemDebitoResponseDto findById(UUID id);

}
