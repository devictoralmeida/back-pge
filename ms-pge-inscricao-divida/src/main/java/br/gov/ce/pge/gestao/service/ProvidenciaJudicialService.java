package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicial;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProvidenciaJudicialService {

    List<ProvidenciaAcaoJudicial> findAll();

    ProvidenciaAcaoJudicial findById(UUID providencia);

    ProvidenciaAcaoJudicial save(String nome);

    ProvidenciaAcaoJudicial findByNome(String nome);
}
