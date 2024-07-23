package br.gov.ce.pge.gestao.service.impl;

import br.gov.ce.pge.gestao.configs.*;
import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.*;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.entity.*;
import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoFase;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import br.gov.ce.pge.gestao.mappers.todto.DevedorUnicoMapperToDto;
import br.gov.ce.pge.gestao.mappers.todto.DividaUnicaMapperToDto;
import br.gov.ce.pge.gestao.mappers.tomodel.DividaMapperToModel;
import br.gov.ce.pge.gestao.repository.DividaRepository;
import br.gov.ce.pge.gestao.service.DividaService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;
import br.gov.ce.pge.gestao.shared.auditoria.CustomRevisionListener;
import br.gov.ce.pge.gestao.shared.exception.AtualizaFaseExtintaException;
import br.gov.ce.pge.gestao.shared.exception.AtualizaMesmaFaseException;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.NumeroInscricaoUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DividaServiceImpl implements DividaService {
    private final DividaRepository repository;
    private final RegistroLivroService registroLivroService;
    private final FeignClientConfig feignClientConfig;
    private final ServicosConfig servicosConfig;
    private final PessoaContatoConfig pessoaContatoConfig;
    private final DaoConfig daoConfig;
    private final ServicosDividaConfig servicosDividaConfig;

    public DividaServiceImpl(DividaRepository repository, RegistroLivroService registroLivroService, FeignClientConfig feignClientConfig,
                             ServicosConfig servicosConfig, PessoaContatoConfig pessoaContatoConfig, DaoConfig daoConfig, ServicosDividaConfig servicosDividaConfig) {
        this.repository = repository;
        this.registroLivroService = registroLivroService;
        this.feignClientConfig = feignClientConfig;
        this.servicosConfig = servicosConfig;
        this.pessoaContatoConfig = pessoaContatoConfig;
        this.daoConfig = daoConfig;
        this.servicosDividaConfig = servicosDividaConfig;
    }

    @Override
    public void save(DividaRequestDto request) {
        Divida divida = DividaMapperToModel.converter(request, feignClientConfig, servicosConfig, pessoaContatoConfig);
        verificaDividaPrevia(divida);

        Divida ultimoRegistro = getLast();
        String numeroInscricao = ultimoRegistro != null ? NumeroInscricaoUtil.formatarNumeroInscricao(ultimoRegistro.getNumeroInscricao()) : NumeroInscricaoUtil.formatarNumeroInscricao("");
        divida.setNumeroInscricao(numeroInscricao);

      repository.save(divida);
      registroLivroService.registrar(divida);
    }

    @Override
    public Divida findByIdModel(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NegocioException(MessageCommonsConstants.MENSAGEM_DIVIDA_NAO_ENCONTRADA));
    }

    @Override
    public Divida getLast() {
        return repository.findLast();
    }

    @Override
    public void atualizarFase(AtualizaFaseRequestDto request) {

        FaseDividaResponseDto faseResponse = feignClientConfig.getFaseDividaService().findById(UUID.fromString(request.getFase()));

        if(faseResponse != null && TipoFase.EXTINTO.equals(faseResponse.getTipoFase())) {
            if(request.getMotivo() == null) {
                throw new NegocioException(MessageCommonsConstants.MSG_MOTIVO_OBRIGATORIO);
            }
        }

        validacaoResponseFase(faseResponse);
        validarFaseFinalistica(request);
        validarFasesIguais(request, faseResponse);

        request.getDividas().stream().forEach(id -> {
            Divida divida = findByIdModel(UUID.fromString(id));
            List<FaseDivida> fasesDivida = divida.getFasesDivida();

            FaseDivida novaFase = getFaseDivida(request, divida);

            setAtributosNovaFase(request, fasesDivida, novaFase);

            FaseDivida faseSalva = servicosDividaConfig.getFaseDividaRepository().save(novaFase);

            atualizarAcaoJudicial(request, faseSalva);

          repository.save(divida);
        });

    }

    private void atualizarAcaoJudicial(AtualizaFaseRequestDto requestDto, FaseDivida faseSalva) {
        if(requestDto.getAcaoJudicial() != null && !requestDto.getAcaoJudicial().isEmpty()) {
            requestDto.getAcaoJudicial().stream().forEach(acao -> {
                AcaoJudicial acaoModels = servicosDividaConfig.getAcaoJudicialService().findByIdModel(UUID.fromString(acao));
                acaoModels.setFaseDivida(faseSalva);

                servicosDividaConfig.getAcaoJudicialService().update(acaoModels);

            });
        }
    }

    @Override
    public void registrarAcao(AcaoJudicialRequestDto request, UUID id) {

        Divida divida = findByIdModel(id);
        AcaoJudicial acaoJudicial = setAcaoJudicial(request, divida);
        acaoJudicial.setDivida(divida);

        TipoAcaoJudicial tipoAcao = servicosDividaConfig.getTipoAcaoJudicialService().findById(request.getTipoAcao());

        acaoJudicial.setTipoAcaoJudicial(tipoAcao);

      servicosDividaConfig.getAcaoJudicialService().save(acaoJudicial);

        divida.getAcoesJudiciais().add(acaoJudicial);

      repository.save(divida);
    }

    private AcaoJudicial setAcaoJudicial(AcaoJudicialRequestDto request, Divida divida) {

        AcaoJudicial acaoJudicial = new AcaoJudicial();

        ProvidenciaAcaoJudicial providencia = servicosDividaConfig.getProvidenciaJudicialService().findByNome(request.getProvidencia());

        if(providencia == null) {
            providencia = servicosDividaConfig.getProvidenciaJudicialService().save(request.getProvidencia());
        }

        acaoJudicial.setProvidenciaAcaoJudicial(providencia);
        acaoJudicial.setObservacao(request.getObservacao());
        acaoJudicial.setNomeAnexo(request.getAnexo());
        acaoJudicial.setReu(request.getReu());
        acaoJudicial.setAutor(request.getAutor());
        acaoJudicial.setJuizo(request.getJuizo());
        acaoJudicial.setNumeroOrdemJudicial(request.getNumeroOrdemJudicial());
        acaoJudicial.setFaseDivida(divida.getFasesDivida().stream().filter(FaseDivida::getFaseAtual).collect(Collectors.toList()).get(SharedConstant.INICIO_INDEX));
        acaoJudicial.setDataAcaoJudicial(LocalDate.parse(request.getDataAcaoJudicial()));

        return acaoJudicial;
    }

    private void setAtributosNovaFase(AtualizaFaseRequestDto request, List<FaseDivida> fasesDivida, FaseDivida novaFase) {
        List<AcaoJudicial> list = new ArrayList<>();
        if(request.getAcaoJudicial() != null) {
            request.getAcaoJudicial().stream().forEach(acao -> {
                AcaoJudicial model = servicosDividaConfig.getAcaoJudicialService().findByIdModel(UUID.fromString(acao));
                list.add(model);
            });
            novaFase.setAcoesJudiciais(list);
        }

        fasesDivida.stream().forEach(faseDivida -> {
            if(Boolean.TRUE.equals(faseDivida.getFaseAtual())) {
                faseDivida.setFaseAtual(false);
                faseDivida.setFaseAnterior(true);
            } else if(Boolean.TRUE.equals(faseDivida.getFaseAnterior())) {
                faseDivida.setFaseAnterior(false);
            }
        });
    }

    private FaseDivida getFaseDivida(AtualizaFaseRequestDto request, Divida divida) {
        FaseDivida novaFase = new FaseDivida();
        novaFase.setFaseAtual(true);
        novaFase.setObservacao(request.getObservacao());
        novaFase.setIdFase(UUID.fromString(request.getFase()));
        novaFase.setDataFase(LocalDateTime.now());
        novaFase.setNomeAnexo(request.getAnexo());

        if(request.getMotivo() != null) {
            MotivoAtualizacaoFase motivo = servicosDividaConfig.getMotivoService().findByIdModel(UUID.fromString(request.getMotivo()));
            novaFase.setMotivoAtualizacaoFase(motivo);
        }

        novaFase.setDivida(divida);
        return novaFase;
    }

    private void validacaoResponseFase(FaseDividaResponseDto faseResponse) {
        if(faseResponse == null) {
            throw new NegocioException(MessageCommonsConstants.MSG_FASE_DIVIDA);
        }

        if(!Situacao.ATIVA.equals(faseResponse.getSituacao()) && !TipoMovimentacaoFase.MANUAL.equals(faseResponse.getTipoMovimentacao())) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_ATUALIZA_FASE);
        }
    }

    private void validarFaseFinalistica(AtualizaFaseRequestDto request) {

        List<Divida> dividasModel = repository.findByIds(request.getDividas().stream().map(divida -> UUID.fromString(divida)).collect(Collectors.toList()));

        List<String> numerosInscricao = new ArrayList<>();

        dividasModel.stream().forEach(divida ->
            divida.getFasesDivida().stream().forEach(fase -> {
                if(Boolean.TRUE.equals(fase.getFaseAtual())) {
                    FaseDividaResponseDto faseAtual = feignClientConfig.getFaseDividaService().findById(fase.getIdFase());
                    if((TipoFase.QUITADO.equals(faseAtual.getTipoFase()) || TipoFase.EXTINTO.equals(faseAtual.getTipoFase()))) {
                        numerosInscricao.add(divida.getNumeroInscricao());
                    }
                }
            })
        );

        if(!numerosInscricao.isEmpty() && Boolean.FALSE.equals(request.getSalvar())) {
            throw new AtualizaFaseExtintaException("As inscrições de número " + String.join(", ", numerosInscricao) + " já foi finalizada (Quitada ou Extinta). Deseja realmente prosseguir com o cadastro da fase? Essa ação é irreversível e poderá acarretar a reativação da inscrição em dívida ativa.");
        }

    }

    private void validarFasesIguais(AtualizaFaseRequestDto request, FaseDividaResponseDto faseDividaResponseDto) {

        List<Divida> dividasModel = repository.findByIds(request.getDividas().stream().map(divida -> UUID.fromString(divida)).collect(Collectors.toList()));

        List<String> numerosInscricao = getNumerosInscricao(UUID.fromString(request.getFase()), dividasModel);

        if(!numerosInscricao.isEmpty() && Boolean.FALSE.equals(request.getSalvar())) {
            throw new AtualizaMesmaFaseException("As inscrições de números " + String.join(", ", numerosInscricao) + " já se encontram na fase " + faseDividaResponseDto.getDescricao() + "! Deseja realmente prosseguir com o cadastro da fase?");
        }
    }

    private List<String> getNumerosInscricao(UUID idFase, List<Divida> dividasModel) {
        return dividasModel.stream()
                .filter(divida -> divida.getFasesDivida().stream()
                        .anyMatch(fase -> fase.getFaseAtual() && fase.getIdFase().equals(idFase)))
                .map(Divida::getNumeroInscricao)
                .collect(Collectors.toList());
    }

    private void verificaDividaPrevia(Divida divida) {
        DividaUnicaRequestDto request = DividaUnicaMapperToDto.converter(divida, getListaDocumentosDevedores(divida));

        if (daoConfig.getDividaDao().findUnique(request)) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_REGISTRO_JA_CADASTRADO);
        }

        verificaDevedorPrevio(divida);
    }

    private void verificaDevedorPrevio(Divida divida) {
        DevedorUnicoRequestDto request = DevedorUnicoMapperToDto.converter(divida, getListaDocumentosDevedoresCorresponsaveis(divida));

        if (daoConfig.getDevedorDao().findUnique(request)) {
            throw new NegocioException(MessageCommonsConstants.MSG_ERRO_REGISTRO_JA_CADASTRADO);
        }
    }

    private List<String> getListaDocumentosDevedores(Divida divida) {
        return divida.getDividaPessoas().stream()
                .filter(dividaPessoa -> IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR.equals(dividaPessoa.getPapelPessoaDivida().getTipoPapelPessoaDivida().getId()))
                .map(dividaPessoa -> dividaPessoa.getPessoa().getDocumento())
                .toList();
    }

    private List<String> getListaDocumentosDevedoresCorresponsaveis(Divida divida) {
        return divida.getDividaPessoas().stream()
                .filter(dividaPessoa -> !IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_SUCESSOR.equals(dividaPessoa.getPapelPessoaDivida().getTipoPapelPessoaDivida().getId()))
                .map(dividaPessoa -> dividaPessoa.getPessoa().getDocumento())
                .toList();
    }

    @Override
    @SneakyThrows
    public void update(UUID id, DividaUpdateRequestDto request, String nomeUsuario) {
        Divida divida = findByIdModel(id);
        List<Pessoa> pessoasAssociadasDivida = divida.getDividaPessoas().stream().map(DividaPessoa::getPessoa).toList();

        boolean mudouDadosDivida = mudouDivida(divida, request);
        boolean mudouDebitos = mudouDebitos(divida.getDebitos(), request.getDebitos());
        boolean mudouPessoas = verificaMudancaPessoaDividaPessoa(pessoasAssociadasDivida, request.getPessoas());

        boolean houveMudanca = mudouDadosDivida || mudouDebitos || mudouPessoas;

        if (houveMudanca) {
            CustomRevisionListener.setDadosAntigos(divida.toStringMapper());
            CustomRevisionListener.setNomeUsuario(nomeUsuario);
            Divida dividaAtualizada = DividaMapperToModel.converterUpdate(request, divida, feignClientConfig, servicosConfig, pessoaContatoConfig);
          repository.save(dividaAtualizada);
        }
    }

    public boolean mudouDivida(Divida divida, DividaUpdateRequestDto request) {
        if (Objects.equals(divida.getDataDeclaracaoAusenciaCorresponsaveis() == null, request.getDeclaracaoAusenciaCorresponsaveis())) {
            return true;
        }

        VaraOrigem varaOrigemRequest = null;
        if (request.getVaraOrigem() != null) {
            varaOrigemRequest = servicosConfig.getVaraOrigemService().findByNome(request.getVaraOrigem());
        }

        if (divida.getTipoDocumento() != null) {
            if (!Objects.equals(divida.getTipoDocumento().getId(), request.getIdTipoDocumento())) {
                return true;
            }
        }

        boolean dividaIgual = Objects.equals(divida.getIdTipoReceita(), request.getIdTipoReceita()) &&
                              Objects.equals(divida.getIdOrigemDebito(), request.getIdOrigemDebito()) &&
                              Objects.equals(divida.getIdProdutoServico(), request.getIdProdutoServico()) &&
                              Objects.equals(divida.getDisposicoesLegais(), request.getDisposicoesLegais()) &&
                              Objects.equals(divida.getNaturezaFundamentacao(), request.getNaturezaFundamentacao()) &&
                              Objects.equals(divida.getInexistenciaCausaSuspensivas(), request.getInexistenciaCausaSuspensivas()) &&
                              Objects.equals(divida.getNumeroDocumento(), request.getNumeroDocumento()) &&
                              Objects.equals(divida.getDataDocumento(), request.getDataDocumento()) &&
                              Objects.equals(divida.getTermoRevelia(), request.getTermoRevelia()) &&
                              Objects.equals(divida.getDataTermoRevelia(), request.getDataTermoRevelia()) &&
                              Objects.equals(divida.getNumeroOficio(), request.getNumeroOficio()) &&
                              Objects.equals(divida.getNumeroProcessoAdministrativo(), request.getNumeroProcessoAdministrativo()) &&
                              Objects.equals(divida.getDataProcesso(), request.getDataProcesso()) &&
                              Objects.equals(divida.getNumeroAcordao(), request.getNumeroAcordao()) &&
                              Objects.equals(divida.getDataTransitoJulgado(), request.getDataTransitoJulgado()) &&
                              Objects.equals(divida.getDataConstituicaoDefinitivaCredito(), request.getDataConstituicaoDefinitivaCredito()) &&
                              Objects.equals(divida.getPlacaVeiculo(), request.getPlacaVeiculo()) &&
                              Objects.equals(divida.getNumeroChassi(), request.getNumeroChassi()) &&
                              Objects.equals(divida.getGuiaItcd(), request.getGuiaItcd()) &&
                              Objects.equals(divida.getSequencialParcelamento(), request.getSequencialParcelamento()) &&
                              Objects.equals(divida.getProtocoloJudicial(), request.getProtocoloJudicial()) &&
                              Objects.equals(divida.getVaraOrigem(), varaOrigemRequest) &&
                              Objects.equals(divida.getTipoProcesso(), servicosConfig.getTipoProcessoService().findByNome(request.getTipoProcesso()));
        return !dividaIgual;
    }

    public boolean mudouDebitos(List<Debito> debitos, List<DebitoRequestDto> requestList) {
        if (debitos.size() != requestList.size()) {
            return true;
        }

        List<Debito> sortedDebitos = debitos.stream().sorted(Comparator.comparing(Debito::getReferenciaInicial)).toList();
        List<DebitoRequestDto> sortedRequestList = requestList.stream().sorted(Comparator.comparing(DebitoRequestDto::getReferenciaInicial)).toList();

        return !IntStream.range(0, sortedDebitos.size())
                .allMatch(index -> {
                    Debito debito = sortedDebitos.get(index);
                    DebitoRequestDto requestDto = sortedRequestList.get(index);
                    return Objects.equals(debito.getReferenciaInicial(), (requestDto.getReferenciaInicial())) &&
                           Objects.equals(debito.getReferenciaFinal(), (requestDto.getReferenciaFinal())) &&
                           Objects.equals(debito.getDataVencimento(), (requestDto.getDataVencimento())) &&
                           Objects.equals(debito.getDataConstituicaoDefinitivaCredito(), requestDto.getDataConstituicaoDefinitivaCredito()) &&
                           Objects.equals(debito.getDataInicioAtualizacaoMonetaria(), requestDto.getDataInicioAtualizacaoMonetaria()) &&
                           comparaValores(debito.getValorPrincipal(), requestDto.getValorPrincipal()) &&
                           comparaValores(debito.getValorMulta(), requestDto.getValorMulta()) &&
                           Objects.equals(debito.getStatus().getId(), requestDto.getIdStatusDebito());
                });
    }

    public boolean comparaValores(BigDecimal valorEntidade, BigDecimal valorRequest) {
        if (valorEntidade == null) {
            valorEntidade = BigDecimal.ZERO;
        }

        if (valorRequest == null) {
            valorRequest = BigDecimal.ZERO;
        }

        return valorEntidade.compareTo(valorRequest) == 0;
    }

    public boolean mudouDadosPapelPessoaDivida(List<DividaPessoa> dividaPessoas, DividaPessoaRequestDto dividaPessoaRequestDto) {
        for (DividaPessoa dividaPessoa : dividaPessoas) {
            PapelPessoaDivida papelPessoaDivida = dividaPessoa.getPapelPessoaDivida();
            UUID tipoPapelPessoaDividaId = papelPessoaDivida.getTipoPapelPessoaDivida() != null ? papelPessoaDivida.getTipoPapelPessoaDivida().getId() : null;
            UUID qualificacaoCorresponsavelId = papelPessoaDivida.getQualificacaoCorresponsavel() != null ? papelPessoaDivida.getQualificacaoCorresponsavel().getId() : null;
            UUID tipoDevedorId = papelPessoaDivida.getTipoDevedor() != null ? papelPessoaDivida.getTipoDevedor().getId() : null;

            boolean dadosIguais = Objects.equals(tipoPapelPessoaDividaId, dividaPessoaRequestDto.getIdPapelPessoa()) &&
                                  Objects.equals(qualificacaoCorresponsavelId, dividaPessoaRequestDto.getIdQualificacaoCorresponsavel()) &&
                                  Objects.equals(tipoDevedorId, dividaPessoaRequestDto.getIdTipoDevedor());
            if (!dadosIguais) {
                return true;
            }
        }

        return false;
    }

    public boolean mudouInformacoesBasicas(Pessoa pessoa, PessoaRequestDto requestDto, Endereco enderecoPrincipal) {
        boolean pessoaIgual = Objects.equals(pessoa.getNomeRazaoSocial(), requestDto.getNomeRazaoSocial()) &&
                              Objects.equals(pessoa.getCgf(), requestDto.getCgf()) &&
                              Objects.equals(pessoa.getTipoPessoa().getId(), requestDto.getIdTipoPessoa()) &&
                              !mudouEndereco(Objects.requireNonNull(enderecoPrincipal), requestDto.getEndereco()) &&
                              !mudouContatos(pessoa.getContatos(), requestDto.getContatos());
        return !pessoaIgual;
    }

    public boolean verificaMudancaPessoaDividaPessoa(List<Pessoa> pessoas, List<PessoaRequestDto> requestList) {
        if (pessoas.size() != requestList.size()) {
            return true;
        }

        return requestList.stream().anyMatch(requestDto -> {
            Pessoa pessoa = pessoaContatoConfig.getConsultaPessoaService().findByDocumentoEntity(requestDto.getDocumento());

            if (pessoa == null) {
                return true;
            }

            if (pessoa.getDividaPessoas().size() != requestList.size()) {
                return true;
            }

            Endereco enderecoPrincipal = pessoa.getEnderecos().stream().filter(Endereco::getPrincipal).findFirst().orElse(null);
            DividaPessoaRequestDto dividaPessoaRequestDto = requestDto.getDividaPessoa();

            boolean pessoaHasContatos = !pessoa.getContatos().isEmpty();
            boolean hasDeclaracaoAusenciaContatos = Boolean.TRUE.equals(dividaPessoaRequestDto.getDeclaracaoAusenciaContatos());
            boolean mudouInformacoesBasicas = mudouInformacoesBasicas(pessoa, requestDto, enderecoPrincipal);
            boolean mudouDadosPapelPessoaDivida = mudouDadosPapelPessoaDivida(pessoa.getDividaPessoas(), requestDto.getDividaPessoa());

            return (pessoaHasContatos && hasDeclaracaoAusenciaContatos) ||
                   mudouInformacoesBasicas ||
                   mudouDadosPapelPessoaDivida;
        });
    }

    public boolean mudouContatos(List<Contato> contatos, List<ContatoRequestDto> requestList) {
        if (contatos.size() != requestList.size()) {
            return true;
        }

        List<Contato> sortedContatos = contatos.stream().sorted(Comparator.comparing(Contato::getValorContato)).toList();
        List<ContatoRequestDto> sortedRequestList = requestList.stream().sorted(Comparator.comparing(ContatoRequestDto::getValorContato)).toList();

        boolean contatoIgual = IntStream.range(0, sortedContatos.size())
                .allMatch(index -> {
                    Contato contato = sortedContatos.get(index);
                    ContatoRequestDto requestDto = sortedRequestList.get(index);
                    return Objects.equals(contato.getValorContato(), requestDto.getValorContato()) &&
                           Objects.equals(contato.getNumeroDdiContato(), requestDto.getNumeroDdiContato()) &&
                           Objects.equals(contato.getTipoContato().getId(), requestDto.getIdTipoContato());
                });
        return !contatoIgual;
    }

    public boolean mudouEndereco(Endereco endereco, EnderecoRequestDto request) {
        boolean enderecoIgual = Objects.equals(endereco.getCep(), request.getCep()) &&
                                Objects.equals(endereco.getLogradouro(), request.getLogradouro()) &&
                                Objects.equals(endereco.getNumero(), request.getNumero()) &&
                                Objects.equals(endereco.getBairro(), request.getBairro()) &&
                                Objects.equals(endereco.getComplemento(), request.getComplemento()) &&
                                Objects.equals(endereco.getDistrito(), request.getDistrito()) &&
                                Objects.equals(endereco.getMunicipio(), request.getMunicipio()) &&
                                Objects.equals(endereco.getUf(), request.getUf());

        return !enderecoIgual;
    }

}
