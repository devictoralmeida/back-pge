package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.TipoProcessoResponseDto;
import br.gov.ce.pge.gestao.entity.TipoProcesso;
import br.gov.ce.pge.gestao.repository.TipoProcessoRepository;
import br.gov.ce.pge.gestao.service.TipoProcessoService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProcessoServiceImpl implements TipoProcessoService {
    private final TipoProcessoRepository repository;

    public TipoProcessoServiceImpl(TipoProcessoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TipoProcessoResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public TipoProcesso findByNome(String tipoProcesso) {
        if (tipoProcesso == null || tipoProcesso.isEmpty()) {
            throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_PROCESSO);
        }

        Optional<TipoProcesso> model = repository.findByNomeIgnoreCase(tipoProcesso);
        return model.orElseGet(() -> save(tipoProcesso));
    }

    @Override
    public TipoProcesso save(String tipoProcesso) {
        TipoProcesso newModel = toModel(tipoProcesso);
        repository.save(newModel);
        return newModel;
    }

    private TipoProcessoResponseDto toDto(TipoProcesso entity) {
        TipoProcessoResponseDto response = new TipoProcessoResponseDto();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        return response;
    }

    private TipoProcesso toModel(String tipoProcesso) {
        TipoProcesso model = new TipoProcesso();
        model.setNome(tipoProcesso);
        return model;
    }
}
