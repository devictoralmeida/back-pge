package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.VaraOrigemResponseDto;
import br.gov.ce.pge.gestao.entity.VaraOrigem;
import br.gov.ce.pge.gestao.repository.VaraOrigemRepository;
import br.gov.ce.pge.gestao.service.VaraOrigemService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaraOrigemServiceImpl implements VaraOrigemService {
    private final VaraOrigemRepository repository;

    public VaraOrigemServiceImpl(VaraOrigemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VaraOrigemResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VaraOrigem findByNome(String nomeVaraOrigem) {
        if (nomeVaraOrigem == null || nomeVaraOrigem.isEmpty()) {
            throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_VARA_ORIGEM);
        }

        Optional<VaraOrigem> model = repository.findByNomeIgnoreCase(nomeVaraOrigem);
        return model.orElseGet(() -> save(nomeVaraOrigem));
    }

    @Override
    public VaraOrigem save(String nomeVaraOrigem) {
        VaraOrigem newModel = toModel(nomeVaraOrigem);
        repository.save(newModel);
        return newModel;
    }

    private VaraOrigemResponseDto toDto(VaraOrigem entity) {
        VaraOrigemResponseDto response = new VaraOrigemResponseDto();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        return response;
    }

    private VaraOrigem toModel(String nomeVaraOrigem) {
        VaraOrigem model = new VaraOrigem();
        model.setNome(nomeVaraOrigem);
        return model;
    }
}
