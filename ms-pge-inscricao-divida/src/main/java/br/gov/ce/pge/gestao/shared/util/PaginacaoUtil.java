package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;

public class PaginacaoUtil {

  private PaginacaoUtil() {
  }

  public static int getTotalPaginas(Integer totalHistorys, long itensPorPagina) {
    return (int) Math.ceil((double) totalHistorys / itensPorPagina);
  }

  public static Long getOffset(Integer page, long itensPorPagina) {
    return Long.valueOf((page - SharedConstant.INCREMENTO) * itensPorPagina);
  }

}
