package br.gov.ce.pge.gestao.mappers.tomodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.dto.request.DividaPessoaRequestDto;
import br.gov.ce.pge.gestao.dto.request.PessoaRequestDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.entity.DividaPessoa;
import br.gov.ce.pge.gestao.entity.Endereco;
import br.gov.ce.pge.gestao.entity.PapelPessoaDivida;
import br.gov.ce.pge.gestao.entity.Pessoa;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;

public class DividaPessoaMapperToModel {
  private DividaPessoaMapperToModel() {
  }

  public static List<DividaPessoa> converterList(List<PessoaRequestDto> request, Divida divida, PessoaContatoConfig pessoaContatoConfig) {
    validarDocumentosUnicos(request);

    List<DividaPessoa> dividaPessoas = new ArrayList<>();
    request.forEach(pessoaRequestDto -> dividaPessoas.add(criarPessoaAndDividaPessoa(pessoaRequestDto, divida, pessoaContatoConfig)));
    return dividaPessoas;
  }
  
  public static List<DividaPessoa> converterListUpdate(List<PessoaRequestDto> request, Divida divida, PessoaContatoConfig pessoaContatoConfig) {
	    validarDocumentosUnicos(request);
//	    List<DividaPessoa> dividaPessoas = new ArrayList<>();
//	    List<Pessoa> pessoasDaDividaCadastrada = divida.getDividaPessoas().stream().map(dp -> dp.getPessoa()).collect(Collectors.toList());
//	    
//	    for (PessoaRequestDto pessoaRequest : request) {
//			
//	    	Pessoa pessoa = pessoasDaDividaCadastrada.stream().filter(p -> p.getDocumento().equals(pessoaRequest.getDocumento())).findFirst().orElse(new Pessoa());
//	    	BeanUtils.copyProperties(request, pessoa, "id", "dataCriacao");
//	    	if (!ValidateDadosUtil.isNullOrVazio(pessoaRequest.getContatos())) 
//	    		pessoa.setContatos(ContatoMapperToModel.converterList(pessoaRequest.getContatos(), pessoa, pessoaContatoConfig));
//	    	
//	    	pessoa.setEnderecos(Arrays.asList(new Endereco(pessoaRequest.getEndereco(), pessoa)));
//	    	
//	    	DividaPessoa dividaPessoa = criarDividaPessoa(pessoaRequest, divida, pessoa, pessoaContatoConfig);
//	    	dividaPessoas.add(dividaPessoa);
//		}
	    return divida.getDividaPessoas();
  }

  public static DividaPessoa criarPessoaAndDividaPessoa(PessoaRequestDto request, Divida divida, PessoaContatoConfig pessoaContatoConfig) {
    verificaPapelPessoaDivida(request);
    verificaTipoPessoa(request);
    request.validarContatos();

    Pessoa pessoa = pessoaContatoConfig.getConsultaPessoaService().findByDocumentoEntity(request.getDocumento());

    if (pessoa == null) {
      pessoa = new Pessoa();
      pessoa.setDocumento(request.getDocumento());
      pessoa.setTipoPessoa(pessoaContatoConfig.getTipoPessoaService().findById(request.getIdTipoPessoa()));
      pessoa.setNomeRazaoSocial(request.getNomeRazaoSocial());
      pessoa.setCgf(request.getCgf());

      if (!ValidateDadosUtil.isNullOrVazio(request.getContatos())) 
    	  pessoa.setContatos(ContatoMapperToModel.converterList(request.getContatos(), pessoa, pessoaContatoConfig));

      pessoa.setEnderecos(Arrays.asList(new Endereco(request.getEndereco(), pessoa)));
      
    } else {
    	
    	BeanUtils.copyProperties(request, pessoa);
    	if (!ValidateDadosUtil.isNullOrVazio(request.getContatos())) 
    		pessoa.setContatos(ContatoMapperToModel.converterList(request.getContatos(), pessoa, pessoaContatoConfig));
    	
    	pessoa.setEnderecos(Arrays.asList(new Endereco(request.getEndereco(), pessoa)));
    	
    }

    DividaPessoa dividaPessoa = criarDividaPessoa(request, divida, pessoa, pessoaContatoConfig);

    return dividaPessoa;
  }
  
  private static void limparAssociacoesExistentes(Pessoa pessoa, Divida divida) {
    List<DividaPessoa> dividaPessoasParaRemover = new ArrayList<>();
    for (DividaPessoa dividaPessoa : pessoa.getDividaPessoas()) {
      if (dividaPessoa.getDivida().equals(divida)) {
        dividaPessoasParaRemover.add(dividaPessoa);
      }
    }
    pessoa.getDividaPessoas().removeAll(dividaPessoasParaRemover);
  }


  private static DividaPessoa criarDividaPessoa(PessoaRequestDto request, Divida divida, Pessoa pessoa, PessoaContatoConfig pessoaContatoConfig) {
    DividaPessoa dividaPessoa = new DividaPessoa();
    dividaPessoa.setDivida(divida);
    dividaPessoa.setPessoa(pessoa);
    dividaPessoa.setPapelPessoaDivida(criarPapelPessoaDivida(request, pessoaContatoConfig));

    verificaDataDeclaracaoAusenciaContato(request, dividaPessoa);
    return dividaPessoa;
  }

  private static PapelPessoaDivida criarPapelPessoaDivida(PessoaRequestDto request, PessoaContatoConfig pessoaContatoConfig) {
    DividaPessoaRequestDto dividaPessoa = request.getDividaPessoa();
    PapelPessoaDivida papelPessoaDivida = new PapelPessoaDivida();
    papelPessoaDivida.setTipoPapelPessoaDivida(pessoaContatoConfig.getTipoPapelPessoaDividaService().findById(dividaPessoa.getIdPapelPessoa()));
    papelPessoaDivida.setQualificacaoCorresponsavel(dividaPessoa.getIdQualificacaoCorresponsavel() == null ? null : pessoaContatoConfig.getQualificacaoCorresponsavelService().findById(dividaPessoa.getIdQualificacaoCorresponsavel()));
    papelPessoaDivida.setTipoDevedor(dividaPessoa.getIdTipoDevedor() == null ? null : pessoaContatoConfig.getTipoDevedorService().findById(dividaPessoa.getIdTipoDevedor()));
    return papelPessoaDivida;
  }

  private static void verificaTipoPessoa(PessoaRequestDto request) {
    if (IdsConstants.ID_TIPO_PESSOA_JURIDICA.equals(request.getIdTipoPessoa())) {
      verificaCgf(request);
      verificaTamanhoNumeroDocumentoPessoaJuridica(request);
    }

    if (IdsConstants.ID_TIPO_PESSOA_FISICA.equals(request.getIdTipoPessoa())) {
      verificaTamanhoNumeroDocumentoPessoaFisica(request);
    }

    request.setCgf(IdsConstants.ID_TIPO_PESSOA_JURIDICA.equals(request.getIdTipoPessoa()) ? request.getCgf() : null);
  }

  private static void verificaTamanhoNumeroDocumentoPessoaFisica(PessoaRequestDto request) {
    if (request.getDocumento().length() != SharedConstant.NUMERO_MODULO) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_TAMANHO_CPF_INVALIDO);
    }
  }

  private static void verificaTamanhoNumeroDocumentoPessoaJuridica(PessoaRequestDto request) {
    if (request.getDocumento().length() != SharedConstant.TAMANHO_MAXIMO_CNPJ) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_TAMANHO_CNPJ_INVALIDO);
    }
  }

  private static void verificaCgf(PessoaRequestDto request) {
    if (request.getCgf() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_CGF);
    }
  }

  private static void verificaPapelPessoaDivida(PessoaRequestDto request) {
    if (IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR.equals(request.getDividaPessoa().getIdPapelPessoa())) {
      validaDevedor(request);
    }

    if (IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_CORRESPONSAVEL.equals(request.getDividaPessoa().getIdPapelPessoa())) {
      validaCorresponsavel(request);
    }
  }

  private static void validaDevedor(PessoaRequestDto request) {
    if (request.getDividaPessoa().getIdTipoDevedor() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_TIPO_DEVEDOR);
    }
  }

  private static void validaCorresponsavel(PessoaRequestDto request) {
    if (request.getDividaPessoa().getIdQualificacaoCorresponsavel() == null) {
      throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_QUALIFICACAO_CORRESPONSAVEL);
    }
  }

  private static void verificaDataDeclaracaoAusenciaContato(PessoaRequestDto request, DividaPessoa dividaPessoa) {
    boolean pessoaSemContatos = ValidateDadosUtil.isNullOrVazio(request.getContatos());
    boolean hasDeclaracaoAusenciaContatos = request.getDividaPessoa().getDeclaracaoAusenciaContatos();

    if (pessoaSemContatos) {
      if (Boolean.FALSE.equals(hasDeclaracaoAusenciaContatos)) {
        throw new NegocioException(MessageCommonsConstants.MSG_ERRO_DECLARACAO_AUSENCIA_CONTATO);
      }
      dividaPessoa.setDataDeclaracaoAusenciaContato(LocalDateTime.now());
    }
  }

  public static void validarDocumentosUnicos(List<PessoaRequestDto> pessoas) {
    Set<String> documentos = new HashSet<>();
    for (PessoaRequestDto pessoa : pessoas) {
      if (!documentos.add(pessoa.getDocumento())) {
        throw new NegocioException(MessageCommonsConstants.MSG_ERRO_REGISTRO_JA_CADASTRADO);
      }
    }
  }
}
