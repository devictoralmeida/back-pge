package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class RegistroLivroFilterRequestDto {

    private static final long serialVersionUID = 7343223050284945324L;

    private UUID idLivro;

    private Integer pagina;

    private Integer linha;

    @Size(max = 9, min = 9, message = "O campo cgf deve conter {max} caracteres")
    private String cgf;

    @Size(max = 11, min = 11, message = "O campo cpf deve conter {max} caracteres")
    private String cpf;

    @Size(max = 14, min = 14, message = "O campo cnpj deve conter {max} caracteres")
    private String cnpj;

    private String numeroInscricao;

    @Size(max = 250, message = "O campo nome ou razão social deve conter no máximo {max} caracteres")
    private String nomeRazaoSocial;

    private LocalDate dataRegistro;

    @JsonIgnore
    private long offset;

    @JsonIgnore
    private long limit;

    @JsonIgnore
    private String orderBy;

    public Map<String, Object> filters() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("idLivro", this.idLivro == null ? null : this.idLivro);
        filter.put("cgf", this.cgf == null ? null : this.cgf);
        filter.put("cpf", this.cpf == null ? null : this.cpf);
        filter.put("cnpj", this.cnpj == null ? null : this.cnpj);
        filter.put("numeroInscricao", this.numeroInscricao == null ? null : this.numeroInscricao);
        filter.put("nomeRazaoSocial", this.nomeRazaoSocial == null || this.nomeRazaoSocial.isEmpty() ? null : "%" + this.nomeRazaoSocial.toUpperCase() + "%");
        filter.put("pagina", this.pagina == null ? null : this.pagina);
        filter.put("linha", this.linha == null ? null : this.linha);
        filter.put("dataRegistro", this.dataRegistro == null ? null : this.getDataRegistro());
        filter.put("offset", offset);
        filter.put("limit", limit);
        filter.put("orderBy", orderBy == null ? "dev.nm_devedor" : getOrdenacao(orderBy));
        return filter;
    }

    private String getOrdenacao(String orderBy) {
        switch (orderBy) {
            case "cgf":
                return "dev.nr_cgf_devedor";
            case "cgf-desc":
                return "dev.nr_cgf_devedor desc";
            case "cpf":
                return "dev.nr_documento_devedor";
            case "cpf-desc":
                return "dev.nr_documento_devedor desc";
            case "cnpj":
                return "dev.nr_documento_devedor";
            case "cnpj-desc":
                return "dev.nr_documento_devedor desc";
            case "numeroInscricao":
                return "i.nr_inscricao";
            case "numeroInscricao-desc":
                return "i.nr_inscricao desc";
            case "nomeRazaoSocial":
                return "dev.nm_devedor";
            case "nomeRazaoSocial-desc":
                return "dev.nm_devedor desc";
            case "pagina":
                return "r.nr_pagina";
            case "pagina-desc":
                return "r.nr_pagina desc";
            case "linha":
                return "r.nr_linha";
            case "linha-desc":
                return "r.nr_linha desc";
            case "dataRegistro":
                return "r.dt_criacao";
            case "dataRegistro-desc":
                return "r.dt_criacao desc";
            case "valor":
                return "d.vl_principal_debito";
            case "valor-desc":
                return "d.vl_principal_debito desc";
            case "origemDebito":
                return "div.nm_origem_debito";
            case "origemDebito-desc":
                return "div.nm_origem_debito desc";
            default:
                throw new NegocioException("Não é possível ordenar por: " + orderBy);
        }
    }

}
