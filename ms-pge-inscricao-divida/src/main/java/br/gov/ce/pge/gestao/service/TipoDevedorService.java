package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.TipoDevedor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoDevedorService {
  List<TipoDevedor> findAll();

  TipoDevedor findById(UUID id);
}
