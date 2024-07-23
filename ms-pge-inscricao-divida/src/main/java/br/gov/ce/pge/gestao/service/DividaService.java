package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.AcaoJudicialRequestDto;
import br.gov.ce.pge.gestao.dto.request.AtualizaFaseRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaUpdateRequestDto;
import br.gov.ce.pge.gestao.entity.Divida;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface DividaService {
    void save(DividaRequestDto request);

    void update(UUID id, DividaUpdateRequestDto request, String nomeUsuario) throws JsonProcessingException;

    Divida findByIdModel(UUID id);

    Divida getLast();

    void atualizarFase(AtualizaFaseRequestDto request);

    void registrarAcao(AcaoJudicialRequestDto request, UUID id);
}
