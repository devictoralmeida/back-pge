package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.TipoDevedor;
import br.gov.ce.pge.gestao.repository.TipoDevedorRepository;
import br.gov.ce.pge.gestao.service.TipoDevedorService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoDevedorServiceImpl implements TipoDevedorService {
  private final TipoDevedorRepository repository;

  public TipoDevedorServiceImpl(TipoDevedorRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<TipoDevedor> findAll() {
    return repository.findAll();
  }

  @Override
  public TipoDevedor findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_DEVEDOR_NAO_ENCONTRADO));
  }
}
