package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.TipoProcesso;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_divida")
public class Divida extends AuditoriaUser {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_divida")
    private UUID id;

    @Column(name = "cd_tipo_receita", nullable = false)
    private UUID idTipoReceita;

    @Column(name = "cd_origem_debito", nullable = false)
    private UUID idOrigemDebito;

    @Column(name = "nm_origem_debito", nullable = false)
    private String nomeOrigemDebito;

    @Column(name = "ds_disposicoes_legais", nullable = false, length = 500)
    private String disposicoesLegais;

    @Column(name = "ds_natureza_fundamentacao", length = 500)
    private String naturezaFundamentacao;

    @Column(name = "ds_inexistencia_suspensivas", length = 500)
    private String inexistenciaCausaSuspensivas;

    @Column(name = "tp_documento")
    private String tipoDocumento;

    @Column(name = "nr_documento", length = 10)
    private String numeroDocumento;

    @Column(name = "dt_documento")
    private LocalDate dataDocumento;

    @Column(name = "ds_termo_revelia")
    private String termoRevelia;

    @Column(name = "dt_termo_revelia")
    private LocalDate dataTermoRevelia;

    @Column(name = "nr_oficio")
    private String numeroOficio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_processo")
    private TipoProcesso tipoProcesso;

    @Column(name = "nr_processo_administrativo")
    private String numeroProcessoAdministrativo;

    @Column(name = "dt_processo")
    private LocalDate dataProcesso;

    @Column(name = "nr_acordao")
    private String numeroAcordao;

    @Column(name = "dt_transito_julgado")
    private LocalDate dataTransitoJulgado;

    @Column(name = "dt_constituicao_definitiva")
    private LocalDate dataConstituicaoDefinitivaCredito;

    @Column(name = "ds_placa_veiculo")
    private String placaVeiculo;

    @Column(name = "nr_chassi")
    private String numeroChassi;

    @Column(name = "nr_guia_itcd")
    private String guiaItcd;

    @Column(name = "sq_parcelamento")
    private String sequencialParcelamento;

    @Column(name = "nr_protocolo_judicial")
    private String protocoloJudicial;

    @Column(name = "nm_anexo_processo")
    private String nomeAnexoProcessoDigitalizado;
}
