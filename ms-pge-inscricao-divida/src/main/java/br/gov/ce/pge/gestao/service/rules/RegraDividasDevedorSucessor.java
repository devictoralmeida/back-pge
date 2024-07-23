package br.gov.ce.pge.gestao.service.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.shared.exception.DividasComDevedoresDistintoException;
import br.gov.ce.pge.gestao.shared.exception.SucessorCadastradoComoDevedorException;
import br.gov.ce.pge.gestao.shared.util.FormataInscricaoDivida;

public class RegraDividasDevedorSucessor {
	
	private RegraDividasDevedorSucessor() {
		
	}
	
	public static void verificaSucessorCadastradoComoDevedor(String documentoSucessorRequest, List<Divida> dividas) {
		List<String> numerosInscricoes = new ArrayList<>();
		dividas.stream().forEach(divida -> {

			if (Boolean.TRUE.equals(divida.possiuDevedor()) && divida.getDocumentoDevedorDivida().equals(documentoSucessorRequest)) 
				numerosInscricoes.add(FormataInscricaoDivida.formatarNumero(divida.getNumeroInscricao()));

		});

		if (!numerosInscricoes.isEmpty()) {
			String inscricoes = String.join(", ", numerosInscricoes);
			throw new SucessorCadastradoComoDevedorException(inscricoes);
		}
	}
	
	public static void verificaDividasComDevedoresDistinto(List<Divida> dividas) {
		List<String> documentosDevedores = new ArrayList<>();

		dividas.stream().forEach(divida -> {
			if (Boolean.TRUE.equals(divida.possiuDevedor()))
				documentosDevedores.add(divida.getDocumentoDevedorDivida());
		});

		Set<String> documentosDiferente = new HashSet<>(documentosDevedores);
		if (documentosDiferente.size() > 1) {
			throw new DividasComDevedoresDistintoException();
		}

	}

}
