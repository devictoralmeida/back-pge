package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.entity.Inscricao;

import java.util.List;

public interface RegistroLivroService {
	
	void registrar(Inscricao inscricao);

	PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> findByFilterRegistroInscricao(RegistroLivroFilterRequestDto request, Integer page, Long limit, String orderBy);
}
