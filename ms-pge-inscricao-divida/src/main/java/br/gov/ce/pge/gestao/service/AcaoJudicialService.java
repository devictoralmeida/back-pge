package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.AcaoJudicial;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AcaoJudicialService {
    List<AcaoJudicial> findAll();

    AcaoJudicial findByIdModel(UUID id);

    void save(AcaoJudicial acaoJudicial);

    void update(AcaoJudicial acaoJudicial);
}
