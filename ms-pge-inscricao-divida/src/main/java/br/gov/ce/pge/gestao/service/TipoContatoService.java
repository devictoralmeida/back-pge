package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.TipoContato;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoContatoService {
  List<TipoContato> findAll();

  TipoContato findById(UUID id);
}
