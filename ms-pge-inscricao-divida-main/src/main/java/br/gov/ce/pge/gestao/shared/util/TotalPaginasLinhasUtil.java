package br.gov.ce.pge.gestao.shared.util;

import br.gov.ce.pge.gestao.constants.SharedConstant;

public class TotalPaginasLinhasUtil {

    private TotalPaginasLinhasUtil() {}

    public static int getTotalPaginas(Integer totalRegistros) {
        return (int) Math.ceil((double) totalRegistros / SharedConstant.REGISTROS_POR_PAGINA);
    }

    public static Integer getRestoDivisao(Integer totalRegistrosLivro) {
        return totalRegistrosLivro % SharedConstant.REGISTROS_POR_PAGINA;
    }


}
