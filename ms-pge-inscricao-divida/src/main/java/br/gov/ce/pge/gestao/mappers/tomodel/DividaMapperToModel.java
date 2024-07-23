package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.FeignClientConfig;
import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.configs.ServicosConfig;
import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DebitoRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.request.PessoaRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.MotivoAtualizacaoDivida;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DividaMapperToModel {
  private DividaMapperToModel() {
  }

  public static Divida converterUpdate(DividaUpdateRequestDto request, Divida divida, FeignClientConfig feignClientConfig, ServicosConfig servicosConfig, PessoaContatoConfig pessoaContatoConfig) {
    validacaoCampos(request, feignClientConfig);
    validaDebitos(request.getDebitos());
    validaSucessor(request.getPessoas());
    validaDevedores(request.getPessoas());
    validaCorresponsaveis(request.getPessoas(), divida, request);

    BeanUtils.copyProperties(request, divida, "dataCriacao", "nomeUsuarioCadastro", "id");

    if (request.getIdAcaoJudicial() != null) {
      divida.getAcoesJudiciais().add(servicosConfig.getAcaoJudicialService().findByIdModel(request.getIdAcaoJudicial()));
    }

    divida.setVaraOrigem(ValidateDadosUtil.isNullOrStringVazia(request.getVaraOrigem()) ? null : servicosConfig.getVaraOrigemService().findByNome(request.getVaraOrigem()));
    divida.setTipoProcesso(servicosConfig.getTipoProcessoService().findByNome(request.getTipoProcesso()));
    divida.setTipoDocumento(request.getIdTipoDocumento() == null ? null : servicosConfig.getTipoDocumentoService().findById(request.getIdTipoDocumento()));
    divida.setDividaPessoas(DividaPessoaMapperToModel.converterListUpdate(request.getPessoas(), divida, pessoaContatoConfig));
    divida.setDebitos(new ArrayList<>(DebitoMapperToModel.converterList(request.getDebitos(), divida, servicosConfig)));
    divida.getMotivosAtualizacaoDivida().add(new MotivoAtualizacaoDivida(divida, request));
    return divida;
  }

  public static Divida converter(DividaRequestDto request, FeignClientConfig feignClientConfig, ServicosConfig servicosConfig, PessoaContatoConfig pessoaContatoConfig) {
    validacaoCampos(request, feignClientConfig);
    validaDebitos(request.getDebitos());
    validaSucessor(request.getPessoas());
    validaDevedores(request.getPessoas());

    Divida divida = new Divida();
    validaCorresponsaveis(request.getPessoas(), divida, request);
    BeanUtils.copyProperties(request, divida, "dataCriacao", "nomeUsuarioCadastro");

    divida.setVaraOrigem(ValidateDadosUtil.isNullOrStringVazia(request.getVaraOrigem()) ? null : servicosConfig.getVaraOrigemService().findByNome(request.getVaraOrigem()));
    divida.setTipoProcesso(servicosConfig.getTipoProcessoService().findByNome(request.getTipoProcesso()));
    divida.setTipoDocumento(request.getIdTipoDocumento() == null ? null : servicosConfig.getTipoDocumentoService().findById(request.getIdTipoDocumento()));
    divida.setDebitos(DebitoMapperToModel.converterList(request.getDebitos(), divida, servicosConfig));
    divida.setDividaPessoas(DividaPessoaMapperToModel.converterList(request.getPessoas(), divida, pessoaContatoConfig));
    divida.getFasesDivida().add(FaseDividaMapperToModel.criarFaseInicial(divida));
    return divida;
  }

  public static void validacaoCampos(DividaRequestDto request, FeignClientConfig feignClientConfig) {
    OrigemDebitoResponseDto origemDebito = feignClientConfig.getOrigemDebitoService().findById(request.getIdOrigemDebito());
    TipoReceitaResponseDto tipoReceita = feignClientConfig.getTipoReceitaService().findById(request.getIdTipoReceita());
    ProdutoServicoResponseDto produtoServico = feignClientConfig.getProdutoServicoService().findById(request.getIdProdutoServico());

    verificaSituacao(origemDebito, tipoReceita, produtoServico);
    verificaAssociacaoTipoReceita(request, tipoReceita);
    verificaAssociacaoProdutoServico(request, produtoServico);

    validaTipoDocumento(request, origemDebito);

    isTipoReceitaNaoTributario(request, tipoReceita);

    isOrigemAiOuAiam(request, origemDebito);
    isOrigemRestoParcelamento(request, origemDebito);
    isOrigemItcd(request, origemDebito);
    isOrigemTce(request, origemDebito);
    isOrigemIpvaOuDetran(request, origemDebito);
    isOrigemCustasJudiciais(request, origemDebito);
    isOrigemTj(request, origemDebito);
    isOrigemIpvaOuIcms(request.getDebitos(), origemDebito);

    if (request.getGuiaItcd() != null) {
      validaOrigensPermitidasGuiaITCD(origemDebito);
    }

    if (request.getDataConstituicaoDefinitivaCredito() != null) {
      validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(origemDebito, tipoReceita);
    }
  }

  private static void verificaSituacao(OrigemDebitoResponseDto origemDebito, TipoReceitaResponseDto tipoReceita, ProdutoServicoResponseDto produtoServico) {
    if (origemDebito.isInativa()) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_ORIGEM_DEBITO_INATIVA);
    }

    if (tipoReceita.isInativa()) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_RECEITA_INATIVO);
    }

    if (produtoServico.isInativa()) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_PRODUTO_SERVICO_INATIVO);
    }
  }

  /**
   * @param request
   * @param tipoReceita
   * @return Verificando_tipo_receita_nao_triburaria
   */

  public static void isTipoReceitaNaoTributario(DividaRequestDto request, TipoReceitaResponseDto tipoReceita) {
    if (Natureza.NAO_TRIBUTARIA.equals(tipoReceita.getNatureza())) {
      verificaNaturezaFundamentacao(request);
      verificaDataConstituicaoDefinitivaCreditoDivida(request);
    }
  }

  public static void verificaNaturezaFundamentacao(DividaRequestDto request) {
    if (request.getNaturezaFundamentacao() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_NATUREZA_FUNDAMENTACAO);
    }
  }

  /**
   * @param request
   * @param origemDebito
   * @return Verificando_algumas_Origens
   */

  public static void isOrigemAiOuAiam(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    boolean isOrigemAiOuAiam = IdsConstants.ID_ORIGEM_DEBITO_AI.equals(origemDebito.getId()) ||
                               IdsConstants.ID_ORIGEM_DEBITO_AIAM.equals(origemDebito.getId());

    if (isOrigemAiOuAiam) {
      verificaTipoDocumento(request);
      verificaDataTransitoJulgado(request);
    }
  }

  public static void isOrigemRestoParcelamento(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    if (IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO.equals(origemDebito.getId())) {
      verificaDataConstituicaoDefinitivaCreditoDivida(request);
      verificaSequencialParcelamento(request);
    }
  }

  private static void isOrigemTce(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    if (!IdsConstants.ID_ORIGEM_DEBITO_TCE.equals(origemDebito.getId())) {
      verificaNumeroAcordao(request);
    }
  }

  public static void isOrigemItcd(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    if (IdsConstants.ID_ORIGEM_DEBITO_ITCD.equals(origemDebito.getId())) {
      verificaDataConstituicaoDefinitivaCreditoDivida(request);
      verificaGuiaItcd(request);
    }
  }

  public static void isOrigemIpvaOuDetran(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    boolean isOrigemIpvaOuDetran = IdsConstants.ID_ORIGEM_DEBITO_IPVA.equals(origemDebito.getId()) ||
                                   IdsConstants.ID_ORIGEM_DEBITO_DETRAN.equals(origemDebito.getId());

    if (isOrigemIpvaOuDetran) {
      verificaPlacaVeiculo(request);
      verificaChassi(request);
    }
  }

  public static void isOrigemCustasJudiciais(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    if (IdsConstants.ID_ORIGEM_DEBITO_CUSTAS_JUDICIAIS.equals(origemDebito.getId())) {
      verificaProtocoloJudicial(request);
    }
  }

  public static void isOrigemTj(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    if (!IdsConstants.ID_ORIGEM_DEBITO_TJ.equals(origemDebito.getId()) && request.getVaraOrigem() != null) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_VARA_ORIGEM_ORIGEM_DEBITO_NAO_PERMITIDA);

    }
  }

  public static void isOrigemIpvaOuIcms(List<DebitoRequestDto> debitos, OrigemDebitoResponseDto origemDebito) {
    boolean isOrigemIpvaOuIcms = IdsConstants.ID_ORIGEM_DEBITO_IPVA.equals(origemDebito.getId()) ||
                                 IdsConstants.ID_ORIGEM_DEBITO_ICMS.equals(origemDebito.getId());

    if (isOrigemIpvaOuIcms) {
      validaDataConstituicaoDefinitivaCreditoDebito(debitos);
    }
  }

  /**
   * @param request
   * @return Verificando_campos_dependentes
   */

  public static void validaTipoDocumento(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
    if (request.getIdTipoDocumento() != null && !isOrigemDebitoPermitida(origemDebito)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_DOCUMENTO_ORIGEM_DEBITO_NAO_PERMITIDA);
    }
  }

  public static boolean isOrigemDebitoPermitida(OrigemDebitoResponseDto origemDebito) {
    return IdsConstants.ID_ORIGEM_DEBITO_AI.equals(origemDebito.getId()) ||
           IdsConstants.ID_ORIGEM_DEBITO_AIAM.equals(origemDebito.getId()) ||
           IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO.equals(origemDebito.getId());
  }

  public static void verificaNumeroAcordao(DividaRequestDto request) {
    if (request.getNumeroAcordao() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_NUMERO_ACORDAO);
    }
  }

  public static void verificaProtocoloJudicial(DividaRequestDto request) {
    if (request.getProtocoloJudicial() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_PROTOCOLO_JUDICIAL);
    }
  }

  public static void verificaChassi(DividaRequestDto request) {
    if (request.getNumeroChassi() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_CHASSI);
    }
  }

  public static void verificaPlacaVeiculo(DividaRequestDto request) {
    if (request.getPlacaVeiculo() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_PLACA);
    }
  }

  public static void verificaGuiaItcd(DividaRequestDto request) {
    if (request.getGuiaItcd() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_GUIA_ITCD);
    }
  }

  public static void validaOrigensPermitidasGuiaITCD(OrigemDebitoResponseDto origemDebito) {
    boolean isOrigemPermitida = IdsConstants.ID_ORIGEM_DEBITO_ITCD.equals(origemDebito.getId()) ||
                                IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO.equals(origemDebito.getId());

    if (!isOrigemPermitida) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_GUIA_ITCD_ORIGEM_DEBITO_NAO_PERMITIDA);
    }
  }

  public static void verificaSequencialParcelamento(DividaRequestDto request) {
    if (request.getSequencialParcelamento() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_SEQUENCIAL_PARCELAMENTO);
    }
  }

  public static void verificaDataConstituicaoDefinitivaCreditoDivida(DividaRequestDto request) {
    if (request.getDataConstituicaoDefinitivaCredito() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_CONSTITUICAO_DEFINITIVA_CREDITO);
    }
  }

  public static void verificaDataTransitoJulgado(DividaRequestDto request) {
    if (request.getDataTransitoJulgado() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_TRANSITO_JULGADO);
    } else {
      request.setDataConstituicaoDefinitivaCredito(request.getDataTransitoJulgado());
    }
  }

  public static void verificaTermoRevelia(DividaRequestDto request) {
    if (request.getTermoRevelia() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_TERMO_REVELIA);
    } else {
      verificaDataTermoRevelia(request);
    }
  }

  public static void verificaDataTermoRevelia(DividaRequestDto request) {
    if (request.getDataTermoRevelia() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_TERMO_REVELIA);
    }
  }

  public static void verificaTipoDocumento(DividaRequestDto request) {
    boolean isOrigemRestoParcelamento = IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO.equals(request.getIdOrigemDebito());

    if (request.getIdTipoDocumento() == null && !isOrigemRestoParcelamento) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_DOCUMENTO);
    } else {
      verificaNumeroDocumento(request);
      verificaDataDocumento(request);
      verificaSeTipoDocumentoDiferenteAI(request);
    }
  }

  public static void verificaSeTipoDocumentoDiferenteAI(DividaRequestDto request) {
    if (!IdsConstants.ID_TIPO_DOCUMENTO_AI.equals(request.getIdTipoDocumento())) {
      verificaTermoRevelia(request);
    }
  }

  public static void verificaDataDocumento(DividaRequestDto request) {
    if (request.getDataDocumento() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_DOCUMENTO);
    }
  }

  public static void verificaNumeroDocumento(DividaRequestDto request) {
    if (request.getNumeroDocumento() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_NUMERO_DOCUMENTO);
    }
  }

  public static void validaDataConstituicaoDefinitivaCreditoDebito(List<DebitoRequestDto> debitos) {
    boolean debitoSemDataConstituicaoDefinitivaCredito = debitos.stream()
            .anyMatch(debito -> debito.getDataConstituicaoDefinitivaCredito() == null);

    if (debitoSemDataConstituicaoDefinitivaCredito) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_DATA_CONSTITUICAO_DEFINITIVA_CREDITO_DEBITO);
    }

    debitos.forEach(DividaMapperToModel::comparaPayloadDataCorreta);
  }

  public static void comparaPayloadDataCorreta(DebitoRequestDto debito) {
    LocalDate dataUmDiaAposVencimento = debito.getDataVencimento().plusDays(SharedConstant.INCREMENTO);

    if (!debito.getDataConstituicaoDefinitivaCredito().isEqual(dataUmDiaAposVencimento)) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_DATA_CONSTITUICAO_DEFINITIVA_CREDITO_INCORRETA);
    } else {
      debito.setDataConstituicaoDefinitivaCredito(dataUmDiaAposVencimento);
    }
  }

  public static void validaOrigensPermitidasTipoReceitaDataConstituicaoDefinitivaCreditoDivida(OrigemDebitoResponseDto origemDebito, TipoReceitaResponseDto tipoReceita) {
    boolean isOrigemPermitida = IdsConstants.ID_ORIGEM_DEBITO_ITCD.equals(origemDebito.getId())
                                || IdsConstants.ID_ORIGEM_DEBITO_RESTO_PARCELAMENTO.equals(origemDebito.getId())
                                || IdsConstants.ID_ORIGEM_DEBITO_AI.equals(origemDebito.getId())
                                || IdsConstants.ID_ORIGEM_DEBITO_AIAM.equals(origemDebito.getId());

    boolean isNaturezaTributaria = Natureza.TRIBUTARIA.equals(tipoReceita.getNatureza());

    if (!isOrigemPermitida && isNaturezaTributaria) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_DATA_CONSTITUICAO_DEFINITIVA_ORIGEM_DEBITO_TIPO_RECEITA_NAO_PERMITIDA);
    }
  }

  public static void verificaAssociacaoProdutoServico(DividaRequestDto request, ProdutoServicoResponseDto produtoServico) {
    if (!produtoServico.getIdsTipoReceitas().contains(request.getIdTipoReceita())) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_PRODUTO_SERVICO_NAO_ASSOCIADO_TIPO_RECEITA);
    }
  }

  public static void verificaAssociacaoTipoReceita(DividaRequestDto request, TipoReceitaResponseDto tipoReceita) {
    if (!tipoReceita.getOrigemDebitos().contains(request.getIdOrigemDebito())) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_TIPO_RECEITA_NAO_ASSOCIADO_ORIGEM_DEBITO);
    }
  }

  public static void validaDevedores(List<PessoaRequestDto> pessoas) {
    List<PessoaRequestDto> devedores = pessoas.stream()
            .filter(pessoa -> IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR.equals(pessoa.getDividaPessoa().getIdPapelPessoa()))
            .toList();

    if (devedores.isEmpty()) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_DEVEDORES);
    }

    verificaTiposDevedores(devedores);
  }

  public static void verificaTiposDevedores(List<PessoaRequestDto> devedores) {
    boolean hasDevedorSolidario = devedores.stream().anyMatch(devedor -> IdsConstants.ID_TIPO_DEVEDOR_SOLIDARIO.equals(devedor.getDividaPessoa().getIdTipoDevedor()));
    boolean hasDevedorSubsidiario = devedores.stream().anyMatch(devedor -> IdsConstants.ID_TIPO_DEVEDOR_SUBSIDIARIO.equals(devedor.getDividaPessoa().getIdTipoDevedor()));
    boolean hasDevedorPrincipal = devedores.stream().anyMatch(devedor -> IdsConstants.ID_TIPO_DEVEDOR_PRINCIPAL.equals(devedor.getDividaPessoa().getIdTipoDevedor()));

    if (hasDevedorPrincipal) {
      validaDevedorTipoPrincipal(hasDevedorSolidario, devedores);
    }

    if (hasDevedorSubsidiario) {
      validaDevedorTipoSubsidiario(hasDevedorSolidario, hasDevedorPrincipal);
    }

    if (hasDevedorSolidario) {
      validaDevedorTipoSolidario(devedores);
    }
  }

  public static void validaSucessor(List<PessoaRequestDto> pessoas) {
    long countSucessores = pessoas.stream()
            .filter(pessoa -> IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_SUCESSOR.equals(pessoa.getDividaPessoa().getIdPapelPessoa()))
            .count();

    if (countSucessores > SharedConstant.INCREMENTO) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_MAIS_DE_UM_SUCESSOR);
    }
  }

  public static void validaCorresponsaveis(List<PessoaRequestDto> pessoas, Divida divida, DividaRequestDto request) {
    long countCorresponsaveis = pessoas.stream()
            .filter(pessoa -> IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_CORRESPONSAVEL.equals(pessoa.getDividaPessoa().getIdPapelPessoa()))
            .count();

    if (countCorresponsaveis == SharedConstant.SOMA_INICIO) {
      validaDeclaracaoAusenciaCorresponsaveis(divida, request);
    }
  }

  public static void validaDeclaracaoAusenciaCorresponsaveis(Divida divida, DividaRequestDto request) {
    if (Boolean.FALSE.equals(request.getDeclaracaoAusenciaCorresponsaveis())) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_DECLARACAO_AUSENCIA_CORRESPONSAVEL);
    } else {
      divida.setDataDeclaracaoAusenciaCorresponsaveis(LocalDateTime.now());
    }
  }

  public static void validaDebitos(List<DebitoRequestDto> debitos) {
    if (debitos.isEmpty()) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_QUANTIDADE_INSUFICIENTE_DEBITOS);
    }
  }

  public static void validaDevedorTipoSubsidiario(boolean hasDevedorSolidario, boolean hasDevedorPrincipal) {
    if (hasDevedorSolidario) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_ADICAO_DEVEDOR_SUBSIDIARIO_INSCRICAO_POSSUI_DEVEDOR_SOLIDARIO);
    }

    if (!hasDevedorPrincipal) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_QUANTIDADE_INSUFICIENTE_DEVEDOR_PRINCIPAL);
    }
  }

  public static void validaDevedorTipoSolidario(List<PessoaRequestDto> devedores) {
    long countDevedoresSolidarios = devedores.stream()
            .filter(devedor -> IdsConstants.ID_TIPO_DEVEDOR_SOLIDARIO.equals(devedor.getDividaPessoa().getIdTipoDevedor()))
            .count();

    if (countDevedoresSolidarios < SharedConstant.QUANTIDADE_MINIMA_DEVEDOR_TIPO_SOLIDARIO) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_QUANTIDADE_INSUFICIENTE_DEVEDOR_SOLIDARIO);
    }
  }

  public static void validaDevedorTipoPrincipal(boolean hasDevedorSolidario, List<PessoaRequestDto> devedores) {
    if (hasDevedorSolidario) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_ADICAO_DEVEDOR_SOLIDARIO_INSCRICAO_POSSUI_DEVEDOR_PRINCIPAL);
    }

    long countDevedoresPrincipais = devedores.stream()
            .filter(devedor -> IdsConstants.ID_TIPO_DEVEDOR_PRINCIPAL.equals(devedor.getDividaPessoa().getIdTipoDevedor()))
            .count();

    if (countDevedoresPrincipais > SharedConstant.QUANTIDADE_MAXIMA_DEVEDOR_TIPO_PRINCIPAL) {
      throw new NegocioException(MessageCommonsConstants.MSG_ERRO_EXCEDEU_QUANTIDADE_MAXIMA_DEVEDOR_PRINCIPAL);
    }
  }
}
