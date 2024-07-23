package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_debito")
public class Debito extends AuditoriaUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_debito")
    private UUID id;

    @Column(name = "ds_referencia_inicial", nullable = false, length = 7)
    private String referenciaInicial;

    @Column(name = "ds_referencia_final", length = 7)
    private String referenciaFinal;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_constituicao_definitiva")
    private LocalDate dataConstituicaoDefinitivaCredito;

    @Column(name = "vl_principal_debito")
    private BigDecimal valorPrincipal;

    @Column(name = "vl_multa_debito")
    private BigDecimal valorMulta;

    @Column(name = "vl_juros", nullable = false)
    private BigDecimal valorJuros;

    @Column(name = "vl_encargos", nullable = false)
    private BigDecimal valorEncargos;

    @Column(name = "vl_honorarios", nullable = false)
    private BigDecimal valorHonorarios;

    @Column(name = "vl_saldo_devedor", nullable = false)
    private BigDecimal saldoDevedor;

    @Column(name = "vl_principal_corrigido")
    private BigDecimal valorPrincipalCorrigido;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_atualizacao_monetaria", nullable = false)
    private LocalDate dataInicioAtualizacaoMonetaria;

    @OneToOne
    @JoinColumn(name = "cd_status_debito", nullable = false)
    private StatusDebito status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_divida", nullable = false)
    private Divida divida;
}
