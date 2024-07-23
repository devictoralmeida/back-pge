package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoFase;
import br.gov.ce.pge.gestao.repository.MotivoAtualizacaoFaseRepository;
import br.gov.ce.pge.gestao.service.MotivoAtualizacaoFaseService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MotivoAtualizacaoFaseServiceImpl implements MotivoAtualizacaoFaseService {

    private final MotivoAtualizacaoFaseRepository repository;

    private MotivoAtualizacaoFaseServiceImpl(MotivoAtualizacaoFaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public MotivoAtualizacaoFase findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MENSAGEM_MOTIVO_NAO_ENCONTRADO));
    }

    @Override
    public List<MotivoAtualizacaoFase> findAll() {
        return repository.findAll();
    }
}
