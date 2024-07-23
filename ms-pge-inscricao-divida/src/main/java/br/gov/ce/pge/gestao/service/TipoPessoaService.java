package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.TipoPessoa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoPessoaService {
  List<TipoPessoa> findAll();

  TipoPessoa findById(UUID id);
}
