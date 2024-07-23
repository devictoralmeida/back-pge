package br.gov.ce.pge.gestao.service.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.entity.Divida;
import br.gov.ce.pge.gestao.service.impl.FaseDividaServiceImpl;
import br.gov.ce.pge.gestao.shared.exception.DividasFaseFinalisticaException;
import br.gov.ce.pge.gestao.shared.util.FormataInscricaoDivida;

public class RegraDividasFase {

	private RegraDividasFase() {

	}

	public static void verificaFasesDasDividas(List<Divida> dividas, FaseDividaServiceImpl faseDividaServiceImpl) {
		List<String> numerosInscricoes = new ArrayList<>();
		List<String> fasesFinalistas = Arrays.asList("QUITADA", "EXTINTA");

		dividas.stream().forEach(divida -> {

			if (divida.faseDividaAtual().isPresent()) {
				FaseDividaResponseDto fase = faseDividaServiceImpl.findById(divida.faseDividaAtual().get().getIdFase());

				if (fasesFinalistas.contains(fase.getNome())) {
					numerosInscricoes.add(FormataInscricaoDivida.formatarNumero(divida.getNumeroInscricao()));
				}
			}
		});

		if (!numerosInscricoes.isEmpty()) {
			String inscricoes = String.join(", ", numerosInscricoes);
			throw new DividasFaseFinalisticaException(inscricoes);
		}

	}

}
