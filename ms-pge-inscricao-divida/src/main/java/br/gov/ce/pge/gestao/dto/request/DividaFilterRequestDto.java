package br.gov.ce.pge.gestao.dto.request;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.constants.SharedConstant;
import br.gov.ce.pge.gestao.enums.Natureza;
import br.gov.ce.pge.gestao.enums.Operador;
import br.gov.ce.pge.gestao.enums.TipoValor;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.FormataCampo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DividaFilterRequestDto implements Serializable {

    private static final long serialVersionUID = -5996345195006190082L;

    private String tipoDevedor;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CPF_APENAS_NUMEROS)
    @Size(max = 11, min = 11, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CPF_INVALIDO)
    private String cpf;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CNPJ_APENAS_NUMEROS)
    @Size(max = 14, min = 8, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CNPJ_INVALIDO)
    private String cnpj;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_CGF_APENAS_NUMEROS)
    @Size(max = 9, min = 9, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_CGF_INVALIDO)
    private String cgf;

    @Size(max = 250, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NOME_DEVEDOR)
    private String nomeDevedor;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_NUMERO_INSCRICAO_APENAS_NUMEROS)
    @Size(max = 13, min = 13, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_INSCRICAO_INVALIDO)
    private String numeroInscricao;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_NUMERO_PROCESSO_APENAS_NUMEROS)
    @Size(min = 20, max = 20, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_PROCESSO_INVALIDO)
    private String numeroProcesso;

    @Pattern(regexp = SharedConstant.REGEX_APENAS_NUMEROS, message = MessageCommonsConstants.MSG_ERRO_PROTOCOLO_APENAS_NUMEROS)
    @Size(min = 20, max = 20, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_PROTOCOLO_JUDICIAL_INVALIDO)
    private String protocoloJudicial;

    @Size(min = 5, max = 5, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_LOTE)
    private String lote;

    @Size(min = 5, max = 10, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_GUIAITCD_INVALIDO)
    private String guiaItcd;

    @Size(max = 10, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_AI_INVALIDO)
    private String numeroAI;

    @Pattern(regexp = SharedConstant.REGEX_PLACA, message = MessageCommonsConstants.MSG_ERRO_PLACA)
    @Size(min = 7, max = 7, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_PLACA_INVALIDO)
    private String placa;

    @Size(max = 25, message = MessageCommonsConstants.MSG_ERRO_TAMANHO_NUMERO_CHASSI_INVALIDO)
    private String chassi;

    private Natureza natureza;

    private List<String> origemDebito;

    private List<String> tipoReceita;

    private List<String> produtoServico;

    private Boolean notificada = true;

    private Boolean protestada = true;

    private Boolean ajuizada = true;

    private List<String> faseAtual;

    private List<String> faseAnterior;

    private Boolean quitadoOuExtinto = true;

    private Boolean cobrancaSuspensa;

    private TipoValor tipoValor;

    private Operador operador;

    private BigDecimal valorUm;

    private BigDecimal valorDois;

    private String consultaGenerica;

    @JsonIgnore
    private long offset;

    @JsonIgnore
    private long limit;

    @JsonIgnore
    private String orderBy;

    public Map<String, Object> filters() {
        Map<String, Object> filter = new HashMap<>();
        filter.put("tipoDevedor", tipoDevedor == null ? null : tipoDevedor);
        filter.put("cpf", cpf == null ? null : cpf);
        filter.put("cnpj", cnpj == null ? null : cnpj);
        filter.put("cgf", cgf == null ? null : cgf);
        filter.put("nomeDevedor", nomeDevedor == null || nomeDevedor.isEmpty() ? null : "%" + nomeDevedor.toUpperCase() + "%");
        filter.put("numeroInscricao", numeroInscricao == null ? null : numeroInscricao);
        filter.put("numeroProcesso", numeroProcesso == null ? null : numeroProcesso);
        filter.put("protocoloJudicial", protocoloJudicial == null ? null : protocoloJudicial);
        filter.put("lote", lote == null ? null : lote);
        filter.put("guiaItcd", guiaItcd == null ? null : guiaItcd);
        filter.put("numeroAI", numeroAI == null ? null : numeroAI);
        filter.put("placa", placa == null ? null : placa);
        filter.put("chassi", chassi == null ? null : chassi);
        filter.put("natureza", natureza == null ? null : natureza);
        filter.put("origemDebito", origemDebito == null ? null : origemDebito);
        filter.put("tipoReceita", tipoReceita == null ? null : tipoReceita);
        filter.put("produtoServico", produtoServico == null ? null : produtoServico);
        filter.put("notificada", notificada == null ? null : notificada);
        filter.put("protestada", protestada == null ? null : protestada);
        filter.put("ajuizada", ajuizada == null ? null : ajuizada);
        filter.put("faseAtual", faseAtual == null ? null : faseAtual);
        filter.put("faseAnterior", faseAnterior == null ? null : faseAnterior);
        filter.put("quitadoOuExtinto", quitadoOuExtinto == null ? null : quitadoOuExtinto);
        filter.put("cobrancaSuspensa", cobrancaSuspensa == null ? null : cobrancaSuspensa);
        filter.put("tipoValor", tipoValor == null ? null : tipoValor.toString());
        filter.put("operador", operador == null ? null : operador.toString());
        filter.put("valorUm", valorUm == null ? null : valorUm);
        filter.put("valorDois", valorDois == null ? null : valorDois);

        FormataCampo.definirConsultaGenericaInscricao(filter, this);

        filter.put("offset", offset);
        filter.put("limit", limit);
        filter.put("orderBy", orderBy == null ? "p.nm_pessoa" : getOrdenacao(orderBy));
        return filter;
    }

    private String getOrdenacao(String orderBy) {

        switch (orderBy) {
            case "numeroInscricao":
                return "nr_inscricao";
            case "numeroInscricao-desc":
                return "nr_inscricao desc";
            case "documento":
                return "p.nr_documento_pessoa, p.nr_cgf_pessoa";
            case "documento-desc":
                return "p.nr_documento_pessoa desc, p.nr_cgf_pessoa desc";
            case "nomeDevedor":
                return "p.nm_pessoa";
            case "nomeDevedor-desc":
                return "p.nm_pessoa desc";
            case "arquivos":
                return "tem_anexo";
            case "arquivos-desc":
                return "tem_anexo desc";
            case "saldoDevedor":
                return "vl_saldo_devedor";
            case "saldoDevedor-desc":
                return "vl_saldo_devedor desc";
            case "numeroAI":
                return "nr_processo_administrativo";
            case "numeroAI-desc":
                return "nr_processo_administrativo desc";
            default:
                throw new NegocioException("Não é possível ordenar por: " + orderBy);
        }
    }

}
