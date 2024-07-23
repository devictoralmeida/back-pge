package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.QualificacaoCorresponsavel;
import br.gov.ce.pge.gestao.repository.QualificacaoCorresponsavelRepository;
import br.gov.ce.pge.gestao.service.QualificacaoCorresponsavelService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QualificacaoCorresponsavelServiceImpl implements QualificacaoCorresponsavelService {
  private final QualificacaoCorresponsavelRepository repository;

  public QualificacaoCorresponsavelServiceImpl(QualificacaoCorresponsavelRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<QualificacaoCorresponsavel> findAll() {
    return repository.findAll();
  }

  @Override
  public QualificacaoCorresponsavel findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_QUALIFICACAO_CORRESPONSAVEL_NAO_ENCONTRADA));
  }
}
