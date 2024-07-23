package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.ProvidenciaAcaoJudicial;
import br.gov.ce.pge.gestao.repository.ProvidenciaAcaoJudicialRepository;
import br.gov.ce.pge.gestao.service.ProvidenciaJudicialService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProvidenciaJudicialServiceImpl implements ProvidenciaJudicialService {

    private final ProvidenciaAcaoJudicialRepository repository;

    public ProvidenciaJudicialServiceImpl(ProvidenciaAcaoJudicialRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProvidenciaAcaoJudicial> findAll() {
        return repository.findAll();
    }

    @Override
    public ProvidenciaAcaoJudicial findById(UUID providencia) {
        return repository.findById(providencia).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MENSAGEM_PROVIDENCIA_NAO_ECONTRADA));
    }

    @Override
    public ProvidenciaAcaoJudicial save(String nome) {
        ProvidenciaAcaoJudicial providencia = new ProvidenciaAcaoJudicial();
        providencia.setNome(nome);
        return repository.save(providencia);
    }

    @Override
    public ProvidenciaAcaoJudicial findByNome(String nome) {
        return repository.findByNome(nome);
    }
}
