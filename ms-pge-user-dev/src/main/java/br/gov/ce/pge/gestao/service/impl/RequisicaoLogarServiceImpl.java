package br.gov.ce.pge.gestao.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.entity.RequisicaoLogar;
import br.gov.ce.pge.gestao.repository.RequisicaoLogarRepository;
import br.gov.ce.pge.gestao.service.RequisicaoLogarService;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;

@Service
public class RequisicaoLogarServiceImpl implements RequisicaoLogarService {

    private final RequisicaoLogarRepository repository;

    private final UsuarioConsultaService usuarioService;
    
    public RequisicaoLogarServiceImpl(RequisicaoLogarRepository repository,
    		UsuarioConsultaService usuarioService) {
    	this.repository = repository;
    	this.usuarioService = usuarioService;
    }

    @Override
    public List<RequisicaoLogar> getRequestByUserId(UUID id) {
        var usuario = usuarioService.findByIdModel(id);
        return repository.findByUsuario(usuario);
    }

    @Override
    public void save(UUID id, boolean sucesso) {

        var usuario = usuarioService.findByIdModel(id);

        var model = new RequisicaoLogar();
        model.setHoraRequisicao(LocalDateTime.now());
        model.setSucesso(sucesso);
        model.setUsuario(usuario);
        this.repository.save(model);
    }
}
