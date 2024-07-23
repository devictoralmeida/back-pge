package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.TipoPapelPessoaDivida;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoPapelPessoaDividaService {
  List<TipoPapelPessoaDivida> findAll();

  TipoPapelPessoaDivida findById(UUID id);
}
