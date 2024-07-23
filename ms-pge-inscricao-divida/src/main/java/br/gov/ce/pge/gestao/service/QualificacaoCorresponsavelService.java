package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.QualificacaoCorresponsavel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface QualificacaoCorresponsavelService {
  List<QualificacaoCorresponsavel> findAll();

  QualificacaoCorresponsavel findById(UUID id);
}
