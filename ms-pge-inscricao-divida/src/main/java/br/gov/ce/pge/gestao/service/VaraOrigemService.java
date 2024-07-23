package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.VaraOrigemResponseDto;
import br.gov.ce.pge.gestao.entity.VaraOrigem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VaraOrigemService {
    List<VaraOrigemResponseDto> findAll();

    VaraOrigem findByNome(String nomeVaraOrigem);

    VaraOrigem save(String nomeVaraOrigem);
}
