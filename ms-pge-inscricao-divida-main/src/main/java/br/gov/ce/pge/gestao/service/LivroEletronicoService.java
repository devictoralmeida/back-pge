package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.LivroEletronicoResponseDto;
import br.gov.ce.pge.gestao.entity.LivroEletronico;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface LivroEletronicoService {

  List<LivroEletronicoFilterResponseDto> findByFilter(LivroEletronicoFilterRequestDto request);

  LivroEletronico findByLivroAberto();

  LivroEletronicoResponseDto findById(UUID id);

  LivroEletronico findByIdModel(UUID id);

}
