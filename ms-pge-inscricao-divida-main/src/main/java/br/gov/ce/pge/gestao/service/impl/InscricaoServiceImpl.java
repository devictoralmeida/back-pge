package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.InscricaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.mappers.todto.InscricaoMapperToDto;
import br.gov.ce.pge.gestao.mappers.tomodel.InscricaoMapperToModel;
import br.gov.ce.pge.gestao.repository.InscricaoRepository;
import br.gov.ce.pge.gestao.service.*;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.NumeroInscricaoUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InscricaoServiceImpl implements InscricaoService {

    private final InscricaoRepository repository;

    private final OrigemDebitoService origemDebitoService;

    private final TipoReceitaService tipoReceitaService;

    private final FileService fileService;

    private final RegistroLivroService registroLivroService;

    public InscricaoServiceImpl(InscricaoRepository repository, OrigemDebitoService origemDebitoService,
                                TipoReceitaService tipoReceitaService, @Qualifier("FileServiceGCP") FileService fileService,
                                RegistroLivroService registroLivroService) {
        this.repository = repository;
        this.origemDebitoService = origemDebitoService;
        this.tipoReceitaService = tipoReceitaService;
        this.fileService = fileService;
        this.registroLivroService = registroLivroService;
    }

    @Override
    public void save(InscricaoRequestDto request) {
        Inscricao inscricao = InscricaoMapperToModel.converter(request, getTipoReceita(request), getOrigemDebito(request), fileService);

        Inscricao ultimoRegistro = getLast();

        String numeroInscricao = ultimoRegistro != null ? NumeroInscricaoUtil.formatarNumeroInscricao(ultimoRegistro.getNumeroInscricao()) : NumeroInscricaoUtil.formatarNumeroInscricao("");
        inscricao.setNumeroInscricao(numeroInscricao);

        repository.save(inscricao);
        registroLivroService.registrar(inscricao);
    }

    @Override
    public void update(UUID id, InscricaoRequestDto request) {
        var inscricaoAlterada = InscricaoMapperToModel.converterUpdate(request, findByIdModel(id), getTipoReceita(request), getOrigemDebito(request), fileService);
        repository.save(inscricaoAlterada);
    }

    @Override
    public Inscricao findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsContanst.MENSAGEM_DIVIDA_NAO_ENCONTRADA));
    }

    @Override
    public InscricaoResponseDto findById(UUID id) {
        return InscricaoMapperToDto.converter(findByIdModel(id), fileService);
    }

    @Override
    public Inscricao getLast() {
        return repository.findLast();
    }

    private OrigemDebitoResponseDto getOrigemDebito(InscricaoRequestDto request) {
        return request.getDivida() == null ? null : origemDebitoService.findById(request.getDivida().getIdOrigemDebito());
    }

    private TipoReceitaResponseDto getTipoReceita(InscricaoRequestDto request) {
        return request.getDivida() == null ? null : tipoReceitaService.findById(request.getDivida().getIdTipoReceita());
    }

}
