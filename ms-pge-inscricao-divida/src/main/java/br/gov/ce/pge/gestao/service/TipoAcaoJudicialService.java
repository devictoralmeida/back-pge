package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.TipoAcaoJudicial;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TipoAcaoJudicialService {

    List<TipoAcaoJudicial> findAll();

    TipoAcaoJudicial findById(UUID tipoAcao);
}
