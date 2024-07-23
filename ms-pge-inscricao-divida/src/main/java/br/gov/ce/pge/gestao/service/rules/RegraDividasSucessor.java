package br.gov.ce.pge.gestao.service.rules;

import java.util.ArrayList;
import java.util.List;

import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.shared.exception.DividasComSucessorCadastradoException;
import br.gov.ce.pge.gestao.shared.exception.SucessorJaCadastradoException;
import br.gov.ce.pge.gestao.shared.util.FormataInscricaoDivida;

public class RegraDividasSucessor {
	
	private RegraDividasSucessor() {
		
	}
	
	public static void verificaDividasComSucessorCadastrado(List<Divida> dividas) {

		List<String> numerosInscricoes = new ArrayList<>();
		dividas.stream().forEach(divida -> {

			if (Boolean.TRUE.equals(divida.possiuSucessor())) 
				numerosInscricoes.add(FormataInscricaoDivida.formatarNumero(divida.getNumeroInscricao()));
		});

		if (!numerosInscricoes.isEmpty()) {
			String inscricoes = String.join(", ", numerosInscricoes);
			throw new DividasComSucessorCadastradoException(inscricoes);
		}

	}
	
	public static void verificaSucessorJaCadastrado(String documentoSucessorRequest, List<Divida> dividas) {
		List<String> numerosInscricoes = new ArrayList<>();
		dividas.stream().forEach(divida -> {

			if (Boolean.TRUE.equals(divida.possiuSucessor()) && divida.getDocumentoSucessorDivida().equals(documentoSucessorRequest)) 
				numerosInscricoes.add(FormataInscricaoDivida.formatarNumero(divida.getNumeroInscricao()));

		});

		if (!numerosInscricoes.isEmpty()) {
			String inscricoes = String.join(", ", numerosInscricoes);
			throw new SucessorJaCadastradoException(inscricoes);
		}
	}

}
