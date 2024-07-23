package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrigemDebitoService {

  OrigemDebitoResponseDto findById(UUID id);

}
