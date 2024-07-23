package br.gov.ce.pge.gestao.service;

import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface TipoReceitaService {
	
	TipoReceitaResponseDto findById(UUID id);

	List<TipoReceitaResponseDto> findAll();
}
