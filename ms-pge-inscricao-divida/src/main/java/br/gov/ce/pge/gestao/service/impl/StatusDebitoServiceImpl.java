package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.StatusDebito;
import br.gov.ce.pge.gestao.repository.StatusDebitoRepository;
import br.gov.ce.pge.gestao.service.StatusDebitoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatusDebitoServiceImpl implements StatusDebitoService {
  private final StatusDebitoRepository repository;

  public StatusDebitoServiceImpl(StatusDebitoRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<StatusDebito> findAll() {
    return repository.findAll();
  }

  @Override
  public StatusDebito findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_STATUS_DEBITO_NAO_ENCONTRADO));
  }
}
