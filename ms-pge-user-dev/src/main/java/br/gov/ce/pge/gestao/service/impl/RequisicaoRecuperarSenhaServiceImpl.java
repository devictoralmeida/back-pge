package br.gov.ce.pge.gestao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.entity.RequisicaoRecuperarSenha;
import br.gov.ce.pge.gestao.repository.RequisicaoRecuperarSenhaRepository;
import br.gov.ce.pge.gestao.service.RequisicaoRecuperarSenhaService;

@Service
public class RequisicaoRecuperarSenhaServiceImpl implements RequisicaoRecuperarSenhaService {

    private final RequisicaoRecuperarSenhaRepository repository;
    
    public RequisicaoRecuperarSenhaServiceImpl(RequisicaoRecuperarSenhaRepository repository) {
    	this.repository = repository;
    }

    @Override
    public List<RequisicaoRecuperarSenha> getRequestByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void save(RequisicaoRecuperarSenha requisicao) {
        this.repository.save(requisicao);
    }
}
