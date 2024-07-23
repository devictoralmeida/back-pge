package br.gov.ce.pge.gestao.shared.util;

public class PaginacaoUtil {

	private PaginacaoUtil() {}

	public static int getTotalPaginas(Integer totalHistorys, Integer itensPorPagina) {
		return (int) Math.ceil((double) totalHistorys / itensPorPagina);
	}

	public static Long getOffset(Integer page, Integer itensPorPagina) {
		return Long.valueOf((page - 1) * itensPorPagina.longValue());
	}

}
