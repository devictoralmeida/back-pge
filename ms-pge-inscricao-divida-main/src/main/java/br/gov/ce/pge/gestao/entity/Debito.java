package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_debito")
public class Debito extends AuditoriaUser {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_debito")
    private UUID id;

    @Column(name = "ds_referencia_inicial", nullable = false, length = 7)
    private String referenciaInicial;

    @Column(name = "ds_referencia_final", nullable = false, length = 7)
    private String referenciaFinal;

    @Column(name = "dt_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "dt_constituicao_definitiva")
    private LocalDate dataConstituicaoDefinitivaCredito;

    @Column(name = "vl_principal_debito", nullable = false)
    private BigDecimal valorPrincipal;

    @Column(name = "vl_multa_debito", nullable = false)
    private BigDecimal valorMulta;

    @ManyToOne
    @JoinColumn(name = "cd_inscricao")
    private Inscricao inscricao;

}
