package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoFase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface MotivoAtualizacaoFaseService {

    MotivoAtualizacaoFase findByIdModel(UUID id);

    List<MotivoAtualizacaoFase> findAll();
}
