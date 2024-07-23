package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.RegistroInscricaoResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistroLivroService {

	void registrar(Divida divida);

	PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> findByFilterRegistroInscricao(RegistroLivroFilterRequestDto request, Integer page, Long limit, String orderBy);
}
