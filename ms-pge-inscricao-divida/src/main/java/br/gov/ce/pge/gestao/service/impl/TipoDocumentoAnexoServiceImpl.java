package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.TipoDocumentoAnexoResponseDto;
import br.gov.ce.pge.gestao.entity.TipoDocumentoAnexo;
import br.gov.ce.pge.gestao.repository.TipoDocumentoAnexoRepository;
import br.gov.ce.pge.gestao.service.TipoDocumentoAnexoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoAnexoServiceImpl implements TipoDocumentoAnexoService {
    private final TipoDocumentoAnexoRepository repository;

    public TipoDocumentoAnexoServiceImpl(TipoDocumentoAnexoRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TipoDocumentoAnexoResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public TipoDocumentoAnexo findByTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null || tipoDocumento.isEmpty()) {
            throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_DOCUMENTO_ANEXO);
        }

        Optional<TipoDocumentoAnexo> model = repository.findByTipoDocumento(tipoDocumento);
        return model.orElseGet(() -> save(tipoDocumento));
    }

    @Override
    public TipoDocumentoAnexo save(String tipoDocumento) {
        TipoDocumentoAnexo newModel = toModel(tipoDocumento);
        repository.save(newModel);
        return newModel;
    }

    private TipoDocumentoAnexoResponseDto toDto(TipoDocumentoAnexo entity) {
        TipoDocumentoAnexoResponseDto response = new TipoDocumentoAnexoResponseDto();
        response.setId(entity.getId());
        response.setTipoDocumento(entity.getTipoDocumento());
        return response;
    }

    private TipoDocumentoAnexo toModel(String tipoDocumento) {
        TipoDocumentoAnexo model = new TipoDocumentoAnexo();
        model.setTipoDocumento(tipoDocumento);
        return model;
    }
}
