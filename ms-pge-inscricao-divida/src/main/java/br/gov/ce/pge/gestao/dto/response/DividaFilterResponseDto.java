package br.gov.ce.pge.gestao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DividaFilterResponseDto implements Serializable {

    private static final long serialVersionUID = -5443364958222254413L;

    private String id;

    private Boolean statusCobranca;

    private String numeroInscricao;

    private String documento;

    private String cgf;

    private String idTipoReceita;

    private String tipoReceita;

    private String idFaseAtual;

    private String faseAtual;

    private LocalDateTime faseAtualEm;

    private String idFaseAnterior;

    private String faseAnterior;

    private BigDecimal saldoDevedor;

    private Boolean arquivos;

    private String tipoDevedor;

    private String nomeDevedor;

    private String numeroProcesso;

    private String protocoloJudicial;

    private String lote;

    private String guiaItcd;

    private String numeroAI;

    private String placa;

    private String chassi;

    private String idOrigemDebito;

    private String idProdutoServico;

    private Boolean notificada;

    private Boolean protestada;

    private Boolean ajuizada;

    private Boolean quitadoOuExtinto;

    private Boolean cobrancaSuspensa;

    private String nomeSucessor;

    private String documentoSucessor;

    private Boolean pagamentoAtrasado;

    private String dataVencimento;

}
