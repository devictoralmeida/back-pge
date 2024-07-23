package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.StatusDebito;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface StatusDebitoService {
  List<StatusDebito> findAll();

  StatusDebito findById(UUID id);
}
