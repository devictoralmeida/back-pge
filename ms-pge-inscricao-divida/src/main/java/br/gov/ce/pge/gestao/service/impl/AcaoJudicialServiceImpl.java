package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.AcaoJudicial;
import br.gov.ce.pge.gestao.repository.AcaoJudicialRepository;
import br.gov.ce.pge.gestao.service.AcaoJudicialService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AcaoJudicialServiceImpl implements AcaoJudicialService {
    private final AcaoJudicialRepository repository;

    public AcaoJudicialServiceImpl(AcaoJudicialRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AcaoJudicial> findAll() {
        return repository.findAll();
    }

    @Override
    public AcaoJudicial findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MSG_ERRO_ACAO_JUDICIAL_NAO_ENCONTRADA));
    }

    @Override
    public void save(AcaoJudicial acaoJudicial) {

        AcaoJudicial model = repository.findByNumeroOrdemJudicial(acaoJudicial.getNumeroOrdemJudicial());

        if(model != null) {
            throw new NegocioException(MessageCommonsConstants.MSG_ACAO_JUSICIAL_EXISTENTE);
        }

        repository.save(acaoJudicial);
    }

    @Override
    public void update(AcaoJudicial acaoJudicial) {

        AcaoJudicial model = repository.findByNumeroOrdemJudicial(acaoJudicial.getNumeroOrdemJudicial());

        if(model == null) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_ACAO_JUDICIAL_NAO_ENCONTRADA);
        }

        repository.save(acaoJudicial);
    }

}
