package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.CondicaoAcessoRequestDto;
import br.gov.ce.pge.gestao.entity.CondicaoAcesso;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public interface CondicaoAcessoService {
    CondicaoAcesso save(@Valid CondicaoAcessoRequestDto request);

    CondicaoAcesso update(UUID id, @Valid CondicaoAcessoRequestDto request);

    List<CondicaoAcesso> findAll();
}
