package br.gov.ce.pge.gestao.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.InscricaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.Inscricao;
import br.gov.ce.pge.gestao.mappers.todto.InscricaoMapperToDto;
import br.gov.ce.pge.gestao.mappers.tomodel.InscricaoMapperToModel;
import br.gov.ce.pge.gestao.repository.InscricaoRepository;
import br.gov.ce.pge.gestao.service.FileService;
import br.gov.ce.pge.gestao.service.InscricaoService;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

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
        var inscricao = InscricaoMapperToModel.converter(request, this.getTipoReceita(request), this.getOrigemDebito(request), fileService);
        this.repository.save(inscricao);
        this.registroLivroService.registrar(inscricao);
    }

    @Override
    public void update(UUID id, InscricaoRequestDto request) {
        var inscricaoAlterada = InscricaoMapperToModel.converterUpdate(request, this.findByIdModel(id), this.getTipoReceita(request), this.getOrigemDebito(request), fileService);
        this.repository.save(inscricaoAlterada);
    }

    @Override
    public Inscricao findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException("Dívida não encontrada."));
    }

    @Override
    public InscricaoResponseDto findById(UUID id) {
        return InscricaoMapperToDto.converter(this.findByIdModel(id), fileService);
    }

    private OrigemDebitoResponseDto getOrigemDebito(InscricaoRequestDto request) {
        return request.getDivida() == null ? null : origemDebitoService.findById(request.getDivida().getIdOrigemDebito());
    }

    private TipoReceitaResponseDto getTipoReceita(InscricaoRequestDto request) {
        return request.getDivida() == null ? null : tipoReceitaService.findById(request.getDivida().getIdTipoReceita());
    }

}
