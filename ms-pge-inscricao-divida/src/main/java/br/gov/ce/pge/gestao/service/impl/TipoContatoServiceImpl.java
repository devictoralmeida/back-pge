package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.TipoContato;
import br.gov.ce.pge.gestao.repository.TipoContatoRepository;
import br.gov.ce.pge.gestao.service.TipoContatoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoContatoServiceImpl implements TipoContatoService {
  private final TipoContatoRepository repository;

  public TipoContatoServiceImpl(TipoContatoRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<TipoContato> findAll() {
    return repository.findAll();
  }

  @Override
  public TipoContato findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_CONTATO_NAO_ENCONTRADO));
  }
}
