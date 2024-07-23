package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;

public class PaginacaoUtil {

	private PaginacaoUtil() {}

	public static int getTotalPaginas(Integer totalHistorys, Integer itensPorPagina) {
		return (int) Math.ceil((double) totalHistorys / itensPorPagina);
	}

	public static Long getOffset(Integer page, Integer itensPorPagina) {
		return Long.valueOf((page - SharedConstant.SUBTRACAO_INDICE) * itensPorPagina.longValue());
	}

}
