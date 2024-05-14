package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LivroEletronicoService {
  List<LivroEletronicoFilterResponseDto> findByFilter(LivroEletronicoFilterRequestDto request);

  LivroEletronico findByLivroAberto();
}
