package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.contantes.MensagemFaseDivida;
import br.gov.ce.pge.gestao.dao.AuditoriaDao;
import br.gov.ce.pge.gestao.dao.FaseDividaDao;
import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.FluxoFaseDividaWrapperRequestDto;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import br.gov.ce.pge.gestao.mappers.todto.FaseDividaToDtoMapper;
import br.gov.ce.pge.gestao.mappers.todto.HistoricoAtualizacaoToDtoMapper;
import br.gov.ce.pge.gestao.mappers.tomodel.FaseDividaToModelMapper;
import br.gov.ce.pge.gestao.repository.FaseDividaRepository;
import br.gov.ce.pge.gestao.service.FaseDividaService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.FaseDividaNotFoundException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FaseDividaServiceImpl implements FaseDividaService {

    private final FaseDividaRepository repository;

    private final FaseDividaDao faseDividaDao;

    private final AuditoriaDao auditoriaDao;

    FaseDividaServiceImpl(FaseDividaRepository repository, FaseDividaDao faseDividaDao, AuditoriaDao auditoriaDao) {
        this.repository = repository;
        this.faseDividaDao = faseDividaDao;
        this.auditoriaDao = auditoriaDao;
    }

    @Override
    public FaseDividaResponseDto save(FaseDividaRequestDto request, String nomeUsuario) {
        validarFaseJaCadastrada(request);
        validarTipoCobranca(request);
        var faseDividaModel = FaseDividaToModelMapper.toModel(request);
        CustomRevisionListener.setNomeUsuario(nomeUsuario);
        int codigoNumerico = Integer.parseInt(repository.findMaxCodigo()) + 1;
        faseDividaModel.setCodigo(String.format("%05d", codigoNumerico));
        repository.save(faseDividaModel);
        return FaseDividaToDtoMapper.toDto(faseDividaModel);
    }


    @Override
    public FaseDividaResponseDto update(UUID id, FaseDividaRequestDto request, String nomeUsuario) throws JsonProcessingException {
        var faseDivida = findByIdModel(id);
        validarTipoCobranca(request);
        if (!faseDivida.getNome().equalsIgnoreCase(request.getNome())) {
            validarFaseJaCadastrada(request);
        }
        CustomRevisionListener.setDadosAntigos(faseDivida.toStringMapper());
        CustomRevisionListener.setNomeUsuario(nomeUsuario);
        var faseDividaAlterada = FaseDividaToModelMapper.toModelUpdate(request, faseDivida);
        repository.save(faseDividaAlterada);
        return FaseDividaToDtoMapper.toDto(faseDividaAlterada);
    }

    private void validarTipoCobranca(FaseDividaRequestDto request) {
        if (!request.isExigeCobranca() && !request.getTipoCobranca().isEmpty()) {
            throw new NegocioException(MensagemFaseDivida.MENSAGEM_FASE_TIPO_COBRANCA_INVALIDO);
        }
    }

    private void validarFaseJaCadastrada(FaseDividaRequestDto faseDividaRequestDto) {
        var faseCadastrada = repository.findByNome(faseDividaRequestDto.getNome().trim().toUpperCase());
        if (faseCadastrada.isPresent()) {
            throw new NegocioException(String.format(MensagemFaseDivida.MENSAGEM_FASE_CADASTRADA, faseCadastrada.get().getNome()));
        }
    }

    private FaseDivida findByIdModel(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new FaseDividaNotFoundException(MensagemFaseDivida.MENSAGEM_FASE_NAO_ENCONTRADA));
    }

    @Override
    public List<FaseDividaResponseDto> findAll() {
        var listaEntidades = repository.findAllFase();
        return listaEntidades.stream().map(FaseDividaToDtoMapper::toDto).toList();
    }

    @Override
    public FaseDividaResponseDto findById(UUID id) {
        var faseDivida = findByIdModel(id);
        return FaseDividaToDtoMapper.toDto(faseDivida);
    }

    @Override
    public List<FaseDividaResponseDto> findByFilter(FaseDividaFilterRequestDto request) {
        return faseDividaDao.findFaseDividaByFilter(request);
    }

    @Override
    public void delete(UUID id, String nomeUsuario) {
        var faseDivida = findByIdModel(id);
        CustomRevisionListener.setNomeUsuario(nomeUsuario);
        repository.delete(faseDivida);
    }

    @Override
    public PaginacaoResponseDto<List<HistoricoAtualizacaoResponseDto>> viewChangeHistoryById(UUID id, Integer page, Integer tamanho) {
        Integer quantidadeItensHistorico = auditoriaDao.countHistorysUpdatesFaseDivida(id);
        Long offSet = PaginacaoUtil.getOffset(page, tamanho);
        List<AuditoriaDto> changeHistory = auditoriaDao.findHistorysUpdates(id, offSet, tamanho, "FaseDivida");
        Integer totalPaginas = PaginacaoUtil.getTotalPaginas(quantidadeItensHistorico, tamanho);
        return PaginacaoResponseDto.fromResultado(HistoricoAtualizacaoToDtoMapper.toDto(changeHistory), quantidadeItensHistorico, totalPaginas, page);

    }

    @Override
    @Transactional
    public void salvarFluxoFases(FluxoFaseDividaWrapperRequestDto request) {
        request.getFases().forEach(fase -> {
            var faseDivida = findByIdModel(fase.getId());
            faseDivida.getFasesPermitidas().clear();
            fase.getFasesPermitidas().forEach(fasePermitida -> {
                validarFasePermitida(fase.getId(), UUID.fromString(fasePermitida));

                var fasePermitidaModel = findByIdModel(UUID.fromString(fasePermitida));
                faseDivida.getFasesPermitidas().add(fasePermitidaModel);
            });

            repository.save(faseDivida);
        });
    }

    private void validarFasePermitida(UUID idFaseAtual, UUID idFasePermitida) {
        if(idFaseAtual.equals(idFasePermitida)) {
            throw new NegocioException(MensagemFaseDivida.MENSAGEM_FASE_FLUXO_IGUAL_INVALIDA);
        }
    }

    @Override
    public List<FluxoFaseDividaResponseDto> obterFluxoFase() {
        List<FaseDivida> fasesAtivas = repository.findFasesAtivas();
        return fasesAtivas.stream().map(FaseDividaToDtoMapper::toFluxoFase).toList();
    }

    @Override
    public List<FaseDividaResponseDto> findSemelhantes(String nome) {
        List<FaseDivida> faseSemelhantes = repository.findByNomeSemelhante(nome);
        return faseSemelhantes.stream().map(FaseDividaToDtoMapper::toDto).toList();

    }


}
