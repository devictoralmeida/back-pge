package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.entity.RequisicaoLogar;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RequisicaoLogarService {

    List<RequisicaoLogar> getRequestByUserId(UUID id);

    void save(UUID id, boolean sucesso);

}
