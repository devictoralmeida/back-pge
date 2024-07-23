package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.configs.DaoConfig;
import br.gov.ce.pge.gestao.configs.FeignClientConfig;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.DividaPessoa;
import br.gov.ce.pge.gestao.entity.FaseDivida;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoFase;
import br.gov.ce.pge.gestao.mappers.todto.AcaoJudicialDividaMapperToDto;
import br.gov.ce.pge.gestao.repository.DividaPessoaRepository;
import br.gov.ce.pge.gestao.repository.DividaRepository;
import br.gov.ce.pge.gestao.service.DividaConsultaService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.PaginacaoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DividaConsultaServiceImpl implements DividaConsultaService {

    private final FeignClientConfig feignClientConfig;

    private final DaoConfig daoConfig;

    private final DividaPessoaRepository dividaPessoaRepository;

    private final DividaRepository repository;

    public DividaConsultaServiceImpl(FeignClientConfig feignClientConfig, DaoConfig daoConfig, DividaPessoaRepository dividaPessoaRepository, DividaRepository repository) {
        this.feignClientConfig = feignClientConfig;
        this.daoConfig = daoConfig;
        this.dividaPessoaRepository = dividaPessoaRepository;
        this.repository = repository;
    }

    @Override
    public DividaResponseDto findById(UUID id) {

        DividaResponseDto responseDto = new DividaResponseDto();

        Divida model = findByIdModel(id);

        BeanUtils.copyProperties(model, responseDto);

        return responseDto;
    }

    @Override
    public AcaoJudicialDividaResponseDto findAcoesJudiciaisByDivida(UUID id) {
        Divida model = findByIdModel(id);
        return AcaoJudicialDividaMapperToDto.toDto(model);
    }

    @Override
    public List<DividaResponseDto> findByIds(List<UUID> ids) {
        List<Divida> dividas = repository.findByIds(ids);

        List<DividaResponseDto> list = new ArrayList<>();

        dividas.stream().forEach(divida -> {
            DividaResponseDto responseDto = new DividaResponseDto();
            BeanUtils.copyProperties(divida, responseDto);

            if(divida.getFasesDivida() != null && !divida.getFasesDivida().isEmpty()) {
                List<FaseDivida> faseAtual = divida.getFasesDivida().stream().filter(FaseDivida::getFaseAtual).collect(Collectors.toList());
                responseDto.setFaseAtualEm(faseAtual.get(SharedConstant.INICIO_INDEX).getDataFase().toString());
            }

            list.add(responseDto);
        });

        return list;
    }

    @Override
    public DividaResponseDto findByNumeroInscricao(String numeroInscricao) {

        DividaResponseDto responseDto = new DividaResponseDto();

        Divida model = repository.findByNumeroInscricao(numeroInscricao);

        if(model.getFasesDivida() != null && !model.getFasesDivida().isEmpty()) {
            List<FaseDivida> faseAtual = model.getFasesDivida().stream().filter(FaseDivida::getFaseAtual).collect(Collectors.toList());
            responseDto.setFaseAtualEm(faseAtual.get(SharedConstant.INICIO_INDEX).getDataFase().toString());
        }

        BeanUtils.copyProperties(model, responseDto);

        return responseDto;
    }

    @Override
    public Divida findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MENSAGEM_DIVIDA_NAO_ENCONTRADA));
    }

    @Override
    public PaginacaoResponseDto<List<DividaFilterResponseDto>> findDividaByFilter(DividaFilterRequestDto request, Integer page, Long limit, String orderBy) {
        
        validaCampoLote(request);
        
        Long offset = PaginacaoUtil.getOffset(page, limit);

        request.setOffset(offset);
        request.setLimit(limit);
        request.setOrderBy(orderBy);

        List<String> idsReceitas = filtroNaturezaTipoReceita(request);

        List<String> idsFases = filtroFases(request);

        Map<String, Object> map = Map.of("idsReceitas", idsReceitas, "idsFases", idsFases);

        Integer totalRegistros = daoConfig.getDividaDao().countfindByFilter(request, map);

        Integer totalPaginas = PaginacaoUtil.getTotalPaginas(totalRegistros, limit);

        List<DividaFilterResponseDto> registros = daoConfig.getDividaDao().findByFilterDivida(request, map);

        definirFaseAtual(registros);

        definirTipoReceita(registros);

        definirDadosSucecssor(registros);

        return PaginacaoResponseDto.fromResultado(registros, totalRegistros, totalPaginas, page);

    }

    private List<String> filtroNaturezaTipoReceita(DividaFilterRequestDto request) {

        List<TipoReceitaResponseDto> receitas = feignClientConfig.getTipoReceitaService().findAll();

        if(request.getNatureza() != null) {

            List<TipoReceitaResponseDto> filtros = new ArrayList<>();

            filtros.addAll(receitas.stream().filter(receita -> receita.getNatureza().equals(request.getNatureza())).collect(Collectors.toList()));

            return request.getTipoReceita() != null && !request.getTipoReceita().isEmpty() ?
                    filtros.stream().filter(filtro -> request.getTipoReceita().contains(filtro.getId().toString())).map(receta -> receta.getId().toString()).collect(Collectors.toList()) :
                    filtros.stream().map(filtro -> filtro.getId().toString()).collect(Collectors.toList());

        }

        List<String> ids = receitas.stream().map(receita -> receita.getId().toString()).collect(Collectors.toList());

        return request.getTipoReceita() != null && !request.getTipoReceita().isEmpty() ? ids.stream().filter(id -> request.getTipoReceita().contains(id)).collect(Collectors.toList()): receitas.stream().map(receita -> receita.getId().toString()).collect(Collectors.toList());
    }

    private void validaCampoLote(DividaFilterRequestDto request) {

        Pattern naoTributaria = Pattern.compile(SharedConstant.REGEX_NAO_TRIBUTARIA);
        Pattern tributaria = Pattern.compile(SharedConstant.REGEX_TRIBUTARIA);

        if(request.getLote() != null && ((Natureza.NAO_TRIBUTARIA.equals(request.getNatureza())
                    && !naoTributaria.matcher(request.getLote()).matches())
                    || (Natureza.TRIBUTARIA.equals(request.getNatureza())
                    && !tributaria.matcher(request.getLote()).matches()))) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_LOTE);
        }
    }

    private void definirDadosSucecssor(List<DividaFilterResponseDto> registros) {

        registros.stream().forEach(registro -> {
            DividaPessoa sucessor = dividaPessoaRepository.getSucessor(UUID.fromString(registro.getId()));
            if(sucessor != null) {
                registro.setNomeSucessor(sucessor.getPessoa().getNomeRazaoSocial());
                registro.setDocumentoSucessor(sucessor.getPessoa().getDocumento());
            }

            if(registro.getDataVencimento() != null) {
                LocalDate dataVencimento = LocalDate.parse(registro.getDataVencimento());
                registro.setPagamentoAtrasado(!LocalDate.now().isBefore(dataVencimento));
            }
        });

    }

    private void definirFaseAtual(List<DividaFilterResponseDto> registros) {
        registros.stream().forEach(registro -> {
            try {
                if(registro.getIdFaseAtual() != null) {
                    FaseDividaResponseDto faseAtual = feignClientConfig.getFaseDividaService().findById(UUID.fromString(registro.getIdFaseAtual()));
                    registro.setFaseAtual(faseAtual.getNome());
                    registro.setStatusCobranca(faseAtual.getExigeCobranca());
                    registro.setNotificada(faseAtual.getTipoCobranca().contains(TipoCobranca.NOTIFICACAO_EXTRA_JUDICIAL));
                    registro.setAjuizada(faseAtual.getTipoCobranca().contains(TipoCobranca.AJUIZAMENTO));
                    registro.setProtestada(faseAtual.getTipoCobranca().contains(TipoCobranca.PROTESTO));
                    registro.setQuitadoOuExtinto(faseAtual.getNome().contains(SharedConstant.NOME_QUITADO) || faseAtual.getNome().contains(SharedConstant.NOME_EXTINTO));
                    registro.setCobrancaSuspensa(faseAtual.getExigeCobranca());
                }
            } catch (NegocioException e) {
                if (MessageCommonsConstants.MSG_FASE_DIVIDA.equals(e.getMessage())) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void definirTipoReceita(List<DividaFilterResponseDto> registros) {
        registros.stream().forEach(registro -> {
            try {
                TipoReceitaResponseDto tipoReceita = feignClientConfig.getTipoReceitaService().findById(UUID.fromString(registro.getIdTipoReceita()));
                registro.setTipoReceita(tipoReceita.getCodigo());
            } catch (NegocioException e) {
                if (MessageCommonsConstants.MSG_TIPO_RECEITA.equals(e.getMessage())) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<String> filtroFases(DividaFilterRequestDto request) {

        List<FaseDividaResponseDto> listaFases = feignClientConfig.getFaseDividaService().findAll();

        List<FaseDividaResponseDto> filtros = new ArrayList<>();

        if(listaFases != null) {

            List<String> idsFases;

            filtroAtributosFases(request, listaFases, filtros);

            idsFases = !filtros.isEmpty() ? filtros.stream().map(filtro -> filtro.getId().toString()).collect(Collectors.toList()) : listaFases.stream().map(lista -> lista.getId().toString()).collect(Collectors.toList());

            return idsFases;
        }
        return listaFases.stream().map(Objects::toString).collect(Collectors.toList());
    }

    private void filtroAtributosFases(DividaFilterRequestDto request, List<FaseDividaResponseDto> listaFases, List<FaseDividaResponseDto> filtros) {
        if(Boolean.FALSE.equals(request.getAjuizada())){
            filtros.addAll(listaFases.stream().filter(fase -> !fase.getTipoCobranca().contains(TipoCobranca.AJUIZAMENTO)).collect(Collectors.toList()));
        }

        if(Boolean.FALSE.equals(request.getNotificada())) {
            filtros.addAll(listaFases.stream().filter(fase -> !fase.getTipoCobranca().contains(TipoCobranca.NOTIFICACAO_EXTRA_JUDICIAL)).collect(Collectors.toList()));
        }

        if(Boolean.FALSE.equals(request.getProtestada())) {
            filtros.addAll(listaFases.stream().filter(fase -> !fase.getTipoCobranca().contains(TipoCobranca.PROTESTO)).collect(Collectors.toList()));
        }

        if(Boolean.FALSE.equals(request.getQuitadoOuExtinto())) {
            filtros.addAll(listaFases.stream().filter(fase -> !TipoFase.QUITADO.equals(fase.getTipoFase()) || !TipoFase.EXTINTO.equals(TipoFase.EXTINTO)).collect(Collectors.toList()));
        }

        if(request.getCobrancaSuspensa() != null) {
            filtros.addAll(listaFases.stream().filter(fase -> fase.getExigeCobranca().equals(request.getCobrancaSuspensa())).collect(Collectors.toList()));
        }
    }

}
