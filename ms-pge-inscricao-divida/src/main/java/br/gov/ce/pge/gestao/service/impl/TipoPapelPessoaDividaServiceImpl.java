package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.TipoPapelPessoaDivida;
import br.gov.ce.pge.gestao.repository.TipoPapelPessoaDividaRepository;
import br.gov.ce.pge.gestao.service.TipoPapelPessoaDividaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoPapelPessoaDividaServiceImpl implements TipoPapelPessoaDividaService {
  private final TipoPapelPessoaDividaRepository repository;

  public TipoPapelPessoaDividaServiceImpl(TipoPapelPessoaDividaRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<TipoPapelPessoaDivida> findAll() {
    return repository.findAll();
  }

  @Override
  public TipoPapelPessoaDivida findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_PAPEL_PESSOA_DIVIDA_NAO_ENCONTRADO));
  }
}
