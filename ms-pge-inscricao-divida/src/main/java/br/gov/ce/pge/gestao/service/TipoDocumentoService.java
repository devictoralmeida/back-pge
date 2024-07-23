package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.TipoDocumento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoDocumentoService {
  List<TipoDocumento> findAll();

  TipoDocumento findById(UUID id);
}
