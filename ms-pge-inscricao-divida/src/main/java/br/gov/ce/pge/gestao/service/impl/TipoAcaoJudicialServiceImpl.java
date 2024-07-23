package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.TipoAcaoJudicial;
import br.gov.ce.pge.gestao.repository.TipoAcaoJudicialRepository;
import br.gov.ce.pge.gestao.service.TipoAcaoJudicialService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TipoAcaoJudicialServiceImpl implements TipoAcaoJudicialService {

    private final TipoAcaoJudicialRepository repository;

    public TipoAcaoJudicialServiceImpl(TipoAcaoJudicialRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TipoAcaoJudicial> findAll() {
        return repository.findAll();
    }

    @Override
    public TipoAcaoJudicial findById(UUID tipoAcao) {
        return repository.findById(tipoAcao).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MENSAGEM_TIPO_ACAO_NAO_ENCONTRADO));
    }
}
