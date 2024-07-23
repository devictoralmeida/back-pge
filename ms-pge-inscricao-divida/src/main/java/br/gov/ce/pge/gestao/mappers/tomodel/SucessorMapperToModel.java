package br.gov.ce.pge.gestao.mappers.tomodel;

import br.gov.ce.pge.gestao.configs.PessoaContatoConfig;
import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.SucessorRequestDto;
import br.gov.ce.pge.gestao.entity.*;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;
import br.gov.ce.pge.gestao.shared.validation.ValidationDadosPessoa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SucessorMapperToModel {

	private SucessorMapperToModel() {
	}

	public static List<DividaPessoa> converterList(SucessorRequestDto request, Divida divida,
			PessoaContatoConfig pessoaContatoConfig) {

		List<DividaPessoa> dividaPessoas = divida.getDividaPessoas();
		if(Boolean.TRUE.equals(divida.possiuSucessor())) {
			dividaPessoas.stream().forEach(dividaPessoa -> {
				if(dividaPessoa.isPapelSucessorDivida()) 
					dividaPessoa.setPessoa(getPessoa(request, pessoaContatoConfig));
				
			});
			return dividaPessoas;
			
		} else {
			dividaPessoas.add(adicionarSucessorNaDivida(request, divida, pessoaContatoConfig));
			return dividaPessoas;
		}
		
	}
	
	public static DividaPessoa adicionarSucessorNaDivida(SucessorRequestDto request, Divida divida,
			PessoaContatoConfig pessoaContatoConfig) {

		Pessoa pessoa = getPessoa(request, pessoaContatoConfig);
		return criarDividaPessoa(request, divida, pessoa, pessoaContatoConfig);
	}
	
	private static Pessoa getPessoa(SucessorRequestDto request, PessoaContatoConfig pessoaContatoConfig) {
		
		verificaTipoPessoa(request);
		request.validarContatos();
		
		Pessoa pessoa = pessoaContatoConfig.getConsultaPessoaService().findByDocumentoEntity(request.getDocumento());
		
		if(pessoa != null) {
			return pessoa;
		} else {
			pessoa = new Pessoa();

			pessoa.setDocumento(request.getDocumento());
			pessoa.setNomeRazaoSocial(request.getNomeRazaoSocial());
			pessoa.setCgf(request.getCgf());

			pessoa.setContatos(ValidateDadosUtil.isNullOrVazio(request.getContatos()) ? null
					: ContatoMapperToModel.converterList(request.getContatos(), pessoa, pessoaContatoConfig));
			List<Endereco> enderecos = new ArrayList<>();
			enderecos.add(new Endereco(request.getEndereco(), pessoa));
			pessoa.setEnderecos(enderecos);
			pessoa.setTipoPessoa(pessoaContatoConfig.getTipoPessoaService().findById(request.getIdTipoPessoa()));
			
			return pessoa;
		}
		
	}

	private static DividaPessoa criarDividaPessoa(SucessorRequestDto request, Divida divida, Pessoa pessoa,
			PessoaContatoConfig pessoaContatoConfig) {
		DividaPessoa dividaPessoa = new DividaPessoa();
		dividaPessoa.setDivida(divida);
		dividaPessoa.setPessoa(pessoa);
		dividaPessoa.setPapelPessoaDivida(criarPapelPessoaSucessor(pessoaContatoConfig));

		 verificaDataDeclaracaoAusenciaContato(request, dividaPessoa);
		return dividaPessoa;
	}

	private static PapelPessoaDivida criarPapelPessoaSucessor(PessoaContatoConfig pessoaContatoConfig) {

		PapelPessoaDivida papelPessoaDivida = new PapelPessoaDivida();
		papelPessoaDivida.setTipoPapelPessoaDivida(pessoaContatoConfig.getTipoPapelPessoaDividaService().findById(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_SUCESSOR));
		return papelPessoaDivida;
	}

	private static void verificaTipoPessoa(SucessorRequestDto request) {
		if (IdsConstants.ID_TIPO_PESSOA_JURIDICA.equals(request.getIdTipoPessoa())) {
			ValidationDadosPessoa.verificaCgf(request.getCgf());
			ValidationDadosPessoa.verificaTamanhoNumeroDocumentoPessoaJuridica(request.getDocumento());
			request.setCgf(request.getCgf());
		}

		if (IdsConstants.ID_TIPO_PESSOA_FISICA.equals(request.getIdTipoPessoa())) {
			ValidationDadosPessoa.verificaTamanhoNumeroDocumentoPessoaFisica(request.getDocumento());
			request.setCgf(null);
		}
	}

	private static void verificaDataDeclaracaoAusenciaContato(SucessorRequestDto request, DividaPessoa dividaPessoa) {
		boolean pessoaSemContatos = ValidateDadosUtil.isNullOrVazio(request.getContatos());
		boolean hasDeclaracaoAusenciaContatos = request.getDeclaracaoAusenciaContato();

		if (pessoaSemContatos && Boolean.FALSE.equals(hasDeclaracaoAusenciaContatos)) {
			throw new NegocioException(MessageCommonsConstants.MSG_ERRO_DECLARACAO_AUSENCIA_CONTATO);
		} else {
			dividaPessoa.setDataDeclaracaoAusenciaContato(LocalDateTime.now());
		}
	}
	
}
