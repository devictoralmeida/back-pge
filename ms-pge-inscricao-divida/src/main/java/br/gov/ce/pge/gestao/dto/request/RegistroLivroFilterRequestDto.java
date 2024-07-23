package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.FormataCampo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class RegistroLivroFilterRequestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7343223050284945324L;

    private UUID idLivro;

    private Integer pagina;

    private Integer linha;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CGF_APENAS_NUMEROS)
    @Size(max = 9, min = 9, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CGF_INVALIDO)
    private String cgf;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CPF_APENAS_NUMEROS)
    @Size(max = 11, min = 11, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CPF_INVALIDO)
    private String cpf;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CNPJ_APENAS_NUMEROS)
    @Size(max = 14, min = 8, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CNPJ_INVALIDO)
    private String cnpj;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_NUMERO_INSCRICAO_APENAS_NUMEROS)
    @Size(max = 13, min = 13, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_INSCRICAO_INVALIDO)
    private String numeroInscricao;

    @Size(max = 250, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NOME_RAZAO_SOCIAL_INVALIDO)
    private String nomeRazaoSocial;

    private LocalDate dataRegistro;

    private String consultaGenerica;

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
        filter.put("cnpj", this.cnpj == null ? null : this.cnpj + "%");
        filter.put("numeroInscricao", this.numeroInscricao == null ? null : this.numeroInscricao);
        filter.put("nomeRazaoSocial", this.nomeRazaoSocial == null || this.nomeRazaoSocial.isEmpty() ? null : "%" + this.nomeRazaoSocial.toUpperCase() + "%");
        filter.put("pagina", this.pagina == null ? null : this.pagina);
        filter.put("linha", this.linha == null ? null : this.linha);
        filter.put("dataRegistro", this.dataRegistro == null ? null : this.getDataRegistro());

        FormataCampo.definirConsultaGenerica(filter, this);

        filter.put("offset", offset);
        filter.put("limit", limit);
        filter.put("orderBy", orderBy == null ? "r.nr_linha" : getOrdenacao(orderBy));
        return filter;
    }

    private String getOrdenacao(String orderBy) {
        switch (orderBy) {
            case "documento":
                return "p.nr_documento_pessoa, p.nr_documento_pessoa";
            case "documento-desc":
                return "p.nr_documento_pessoa desc, p.nr_documento_pessoa desc";
            case "numeroInscricao":
                return "div.nr_inscricao";
            case "numeroInscricao-desc":
                return "div.nr_inscricao desc";
            case "nomeRazaoSocial":
                return "p.nm_pessoa";
            case "nomeRazaoSocial-desc":
                return "p.nm_pessoa desc";
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
                return "vl_principal_debito";
            case "valor-desc":
                return "vl_principal_debito desc";
            case "nomeUsuario":
                return "nm_usuario_cadastro";
            case "nomeUsuario-desc":
                return "nm_usuario_cadastro desc";
            default:
                throw new NegocioException("Não é possível ordenar por: " + orderBy);
        }
    }

}
