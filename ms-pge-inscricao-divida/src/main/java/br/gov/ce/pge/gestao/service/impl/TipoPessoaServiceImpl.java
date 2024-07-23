package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.TipoPessoa;
import br.gov.ce.pge.gestao.repository.TipoPessoaRepository;
import br.gov.ce.pge.gestao.service.TipoPessoaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoPessoaServiceImpl implements TipoPessoaService {
  private final TipoPessoaRepository repository;

  public TipoPessoaServiceImpl(TipoPessoaRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<TipoPessoa> findAll() {
    return repository.findAll();
  }

  @Override
  public TipoPessoa findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_PESSOA_NAO_ENCONTRADO));
  }
}
