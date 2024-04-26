package br.gov.ce.pge.gestao.mappers.tomodel;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import br.gov.ce.pge.gestao.dto.request.DividaRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.TipoDocumento;
import br.gov.ce.pge.gestao.service.FileService;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class DividaMapperToModel {

	private static final String BUCKET_DIVIDA = "divida";

	private DividaMapperToModel() {
	}

	public static Divida converter(DividaRequestDto request, TipoReceitaResponseDto tipoReceita,
			OrigemDebitoResponseDto origemDebito, FileService fileService) {

		validacaoCampos(request, tipoReceita, origemDebito);
		uploadFile(request, fileService);

		var divida = new Divida();
		BeanUtils.copyProperties(request, divida, "dataCriacao", "nomeUsuarioCadastro");
		divida.setTipoDocumento(getTipoDocumento(request));
		return divida;
	}
	
	public static Divida converterUpdate(DividaRequestDto request, Divida divida, TipoReceitaResponseDto tipoReceita,
			OrigemDebitoResponseDto origemDebito, FileService fileService) {

		validacaoCampos(request, tipoReceita, origemDebito);
		uploadFileUpdate(request, divida, fileService);

		BeanUtils.copyProperties(request, divida, "dataCriacao", "nomeUsuarioCadastro");
		divida.setTipoDocumento(getTipoDocumento(request));
		return divida;
	}

	private static void uploadFileUpdate(DividaRequestDto request, Divida divida, FileService fileService) {
		if (divida.getNomeAnexoProcessoDigitalizado() != null) {
			String fileInBucket = fileService.download(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA);
			
			if (request.getArquivo() == null) {
				fileService.delete(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA);
				request.setNomeAnexoProcessoDigitalizado(null);
			} else if (!fileService.compareFile(fileInBucket, request.getArquivo())) {
				fileService.delete(divida.getNomeAnexoProcessoDigitalizado(), BUCKET_DIVIDA);
				uploadFile(request, fileService);
			}
		} else {
			uploadFile(request, fileService);
		}
		
	}

	private static void uploadFile(DividaRequestDto request, FileService fileService) {
		if(request.getArquivo() != null) {
			var fileName = "divida_" + UUID.randomUUID().toString();
			request.setNomeAnexoProcessoDigitalizado(fileName);
			fileService.upload(request.getArquivo(), fileName, BUCKET_DIVIDA);
		}
	}

	private static String getTipoDocumento(DividaRequestDto request) {
		return request != null && request.getTipoDocumento() != null? request.getTipoDocumento().name() : null;
	}

	private static void validacaoCampos(DividaRequestDto request, TipoReceitaResponseDto tipoReceita,
			OrigemDebitoResponseDto origemDebito) {

		isOrigemAiOuAiam(request, origemDebito);
		isOrigemRestoParcelamento(request, origemDebito);
		isOrigemItcd(request, origemDebito);
		isOrigemIpva(request, origemDebito);
		isOrigemCustasJudiciais(request, origemDebito);
		
		isTipoReceitaNaoTributario(request, tipoReceita);

	}
	
	/**
	 * @param request
	 * @param tipoReceita
	 * @return Verificando_tipo_receita_nao_triburaria
	 */
	
	private static void isTipoReceitaNaoTributario(DividaRequestDto request, TipoReceitaResponseDto tipoReceita) {
		if (tipoReceita.getNatureza() == Natureza.NAO_TRIBUTARIA) {
			verificaNaturezaFundamentacao(request);
			verificaDataConstituicaoDefinitivaCredito(request);
		}
	}

	private static void verificaNaturezaFundamentacao(DividaRequestDto request) {
		if (request.getNaturezaFundamentacao() == null) {
			throw new NegocioException("Favor informar a Natureza e Fundamentação Legal do Débito.");
		}
	}

	/**
	 * @param request
	 * @param origemDebito
	 * @return Verificando_algumas_Origens
	 */
	
	private static void isOrigemAiOuAiam(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
		if (origemDebito.getNome().equalsIgnoreCase("AI") || origemDebito.getNome().equalsIgnoreCase("AIAM")) {
			verificaTipoDocumento(request);
			verificaDataTransitoJulgado(request);
		}
	}
	
	private static void isOrigemRestoParcelamento(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
		if (origemDebito.getNome().equalsIgnoreCase("Resto de Parcelamento")) {
			verificaDataConstituicaoDefinitivaCredito(request);
			verificaSequencialParcelamento(request);
		}
	}
	
	private static void isOrigemItcd(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
		if (origemDebito.getNome().equalsIgnoreCase("ITCD")) {
			verificaDataConstituicaoDefinitivaCredito(request);
			verificaGuiaItcd(request);
		}
	}
	
	private static void isOrigemIpva(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
		if (origemDebito.getNome().equalsIgnoreCase("IPVA")) {
			verificaPlacaVeiculo(request);
			verificaChassi(request);
		}
	}

	private static void isOrigemCustasJudiciais(DividaRequestDto request, OrigemDebitoResponseDto origemDebito) {
		if (origemDebito.getNome().equalsIgnoreCase("CUSTAS JUDICIAIS")) {
			verificaProtocoloJudicial(request);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return Verificando_campos_dependentes
	 */

	private static void verificaProtocoloJudicial(DividaRequestDto request) {
		if (request.getProtocoloJudicial() == null) {
			throw new NegocioException("Favor informar o protocolo judicial.");
		}
	}

	private static void verificaChassi(DividaRequestDto request) {
		if (request.getNumeroChassi() == null) {
			throw new NegocioException("Favor informar o número do chassi.");
		}
	}

	private static void verificaPlacaVeiculo(DividaRequestDto request) {
		if (request.getPlacaVeiculo() == null) {
			throw new NegocioException("Favor informar a placa do veículo.");
		}
	}

	private static void verificaGuiaItcd(DividaRequestDto request) {
		if (request.getGuiaItcd() == null) {
			throw new NegocioException("Favor informar a Guia do ITCD.");
		}
	}

	private static void verificaSequencialParcelamento(DividaRequestDto request) {
		if (request.getSequencialParcelamento() == null) {
			throw new NegocioException("Favor informar o Sequencial do Parcelamento.");
		}
	}

	private static void verificaDataConstituicaoDefinitivaCredito(DividaRequestDto request) {
		if (request.getDataConstituicaoDefinitivaCredito() == null) {
			throw new NegocioException("Favor informar a Data da Constituição Definitiva do Crédito.");
		} else {
			verificaDataConstituicaoFutura(request);
		}
	}

	private static void verificaDataConstituicaoFutura(DividaRequestDto request) {
		if (request.getDataConstituicaoDefinitivaCredito().isAfter(LocalDate.now())) {
			throw new NegocioException("A data não pode ser superior a data atual");
		}
	}

	private static void verificaDataTransitoJulgado(DividaRequestDto request) {
		if (request.getDataTransitoJulgado() == null) {
			throw new NegocioException("Favor informar a Data do Trânsito em Julgado.");
		} else {
			verificaDataTransitoJulgadoFutura(request);
			request.setDataConstituicaoDefinitivaCredito(request.getDataTransitoJulgado());
		}
	}

	private static void verificaDataTransitoJulgadoFutura(DividaRequestDto request) {
		if (request.getDataTransitoJulgado().isAfter(LocalDate.now())) {
			throw new NegocioException("A data trânsito em julgado não pode ser superior a data atual");
		}
	}

	private static void verificaTermoRevelia(DividaRequestDto request) {
		if (request.getTermoRevelia() == null) {
			throw new NegocioException("Favor informar o Termo de revelia.");
		} else {
			verificaDataTermoRevelia(request);
		}
	}

	private static void verificaDataTermoRevelia(DividaRequestDto request) {
		if (request.getDataTermoRevelia() == null) {
			throw new NegocioException("Favor informar a data do Termo de revelia.");
		}
	}

	private static void verificaTipoDocumento(DividaRequestDto request) {
		if (getTipoDocumento(request) == null) {
			throw new NegocioException("Favor informar o Tipo Documento");
		} else {
			verificaNumeroDocumento(request);
			verificaDataDocumento(request);
			verificaSeTipoDocumentoIsAI(request);
		}
	}

	private static void verificaSeTipoDocumentoIsAI(DividaRequestDto request) {
		if(request.getTipoDocumento() == TipoDocumento.AI) {
			verificaTermoRevelia(request);
		}
	}

	private static void verificaDataDocumento(DividaRequestDto request) {
		if (request.getDataDocumento() == null) {
			throw new NegocioException("Favor informar a data do documento");
		} else {
			verificaDataDocumentoFutura(request);
		}
	}

	private static void verificaDataDocumentoFutura(DividaRequestDto request) {
		if (request.getDataDocumento().isAfter(LocalDate.now())) {
			throw new NegocioException("A data do documento não pode ser superior a data atual");
		}
	}

	private static void verificaNumeroDocumento(DividaRequestDto request) {
		if (request.getNumeroDocumento() == null) {
			throw new NegocioException("Favor informar o número do documento");
		}
	}
}
