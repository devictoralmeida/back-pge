package br.gov.ce.pge.gestao.service;

import java.util.UUID;

import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.InscricaoResponseDto;
import br.gov.ce.pge.gestao.entity.Inscricao;

public interface InscricaoService {

	void save(InscricaoRequestDto request);
	
	void update(UUID id, InscricaoRequestDto request);

	Inscricao findByIdModel(UUID id);
	
	InscricaoResponseDto findById(UUID id);

}
