package br.gov.ce.pge.gestao.service;

import java.util.UUID;

import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;

public interface TipoReceitaService {
	
	TipoReceitaResponseDto findById(UUID id);

}
