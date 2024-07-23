package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.response.TipoDocumentoAnexoResponseDto;
import br.gov.ce.pge.gestao.entity.TipoDocumentoAnexo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TipoDocumentoAnexoService {
    List<TipoDocumentoAnexoResponseDto> findAll();

    TipoDocumentoAnexo findByTipoDocumento(String tipoDocumento);

    TipoDocumentoAnexo save(String tipoDocumento);
}
