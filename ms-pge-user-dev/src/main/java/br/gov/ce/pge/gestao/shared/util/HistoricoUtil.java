package br.gov.ce.pge.gestao.shared.util;

public class HistoricoUtil {
	
	private HistoricoUtil() {
		
	}
	
	public static Integer getTotalPaginas(Integer totalHistorys,  long itensPorPagina) {
		return (int) Math.ceil((double) totalHistorys / itensPorPagina);
	}
	
	public static Long getOffset(Integer page,  long itensPorPagina) {
		return Long.valueOf((page-1)) * itensPorPagina;
	}

}
