package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.TipoDocumento;
import br.gov.ce.pge.gestao.repository.TipoDocumentoRepository;
import br.gov.ce.pge.gestao.service.TipoDocumentoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {
  private final TipoDocumentoRepository repository;

  public TipoDocumentoServiceImpl(TipoDocumentoRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<TipoDocumento> findAll() {
    return repository.findAll();
  }

  @Override
  public TipoDocumento findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_DOCUMENTO_NAO_ENCONTRADO));
  }
}
