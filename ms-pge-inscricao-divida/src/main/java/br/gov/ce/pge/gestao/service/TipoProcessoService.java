package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.TipoProcessoResponseDto;
import br.gov.ce.pge.gestao.entity.TipoProcesso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TipoProcessoService {
    List<TipoProcessoResponseDto> findAll();

    TipoProcesso findByNome(String tipoProcesso);

    TipoProcesso save(String tipoProcesso);
}
