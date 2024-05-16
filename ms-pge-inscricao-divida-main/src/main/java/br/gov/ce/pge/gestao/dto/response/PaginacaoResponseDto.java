package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;

public class PaginacaoResponseDto<T> implements Serializable {

    private static final long serialVersionUID = -566674516440973618L;

    private Integer totalRegistros;
    private Integer totalPaginas;
    private Integer paginaAtual;
    private transient T list;

    private PaginacaoResponseDto() {}

    public static <T> PaginacaoResponseDto<T> fromResultado(T list, Integer totalRegistros, Integer totalPaginas, Integer paginaAtual) {
        return new PaginacaoResponseDto<T>()
                .setTotalRegistros(totalRegistros)
                .setTotalPaginas(totalPaginas)
                .setPaginaAtual(paginaAtual)
                .setList(list);
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public PaginacaoResponseDto<T> setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
        return this;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public PaginacaoResponseDto<T> setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
        return this;
    }

    public Integer getPaginaAtual() {
        return paginaAtual;
    }

    public PaginacaoResponseDto<T> setPaginaAtual(Integer paginaAtual) {
        this.paginaAtual = paginaAtual;
        return this;
    }

    public T getList() {
        return list;
    }

    public PaginacaoResponseDto<T> setList(T resultado) {
        this.list = resultado;
        return this;
    }
}

