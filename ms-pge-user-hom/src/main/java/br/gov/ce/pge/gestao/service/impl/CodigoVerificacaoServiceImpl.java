package br.gov.ce.pge.gestao.service.impl;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.entity.CodigoVerificacao;
import br.gov.ce.pge.gestao.repository.CodigoVerificacaoRepository;
import br.gov.ce.pge.gestao.service.CodigoVerificacaoService;

@Service
public class CodigoVerificacaoServiceImpl implements CodigoVerificacaoService {

    private final CodigoVerificacaoRepository repository;
    
    public CodigoVerificacaoServiceImpl(CodigoVerificacaoRepository repository) {
    	this.repository = repository;
    }

    @Override
    public CodigoVerificacao save(CodigoVerificacao codigo) {
        return this.repository.save(codigo);
    }
}
