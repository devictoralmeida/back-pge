package br.gov.ce.pge.gestao.shared.validation;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;

public class ValidationDadosPessoa {

	private ValidationDadosPessoa() {

	}

	public static void verificaTamanhoNumeroDocumentoPessoaFisica(String cpf) {
		if (cpf.length() != SharedConstant.NUMERO_MODULO) {
			throw new NegocioException(MessageCommonsConstants.MENSAGEM_TAMANHO_CPF_INVALIDO);
		}
	}

	public static void verificaTamanhoNumeroDocumentoPessoaJuridica(String cnpj) {
		if (cnpj.length() != SharedConstant.TAMANHO_MAXIMO_CNPJ) {
			throw new NegocioException(MessageCommonsConstants.MENSAGEM_TAMANHO_CNPJ_INVALIDO);
		}
	}

	public static void verificaCgf(String cgf) {
		if (cgf == null) {
			throw new NegocioException(MessageCommonsConstants.MENSAGEM_INFORMAR_CGF);
		}
	}

}
