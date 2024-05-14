package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.enums.Situacao;
import br.gov.ce.pge.gestao.enums.TipoCobranca;
import br.gov.ce.pge.gestao.enums.TipoMovimentacaoFase;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_fase_divida")
@Audited
@AuditTable("tb_fase_divida_aud")
public class FaseDivida  extends AuditoriaUser implements  Serializable {

    private static final long serialVersionUID = -3770169452237675929L;

    @Id
    @GeneratedValue
    @Column(name = "ci_fase_divida")
    private UUID id;

    @Column(name = "cd_fase_divida", unique = true, nullable = false)
    private String codigo;

    @Column(name = "nm_fase_divida", nullable = false)
    private String nome;

    @Column(name = "ds_fase_divida", nullable = false)
    private String descricao;

    @Column(name = "tp_movimentacao", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoFase tipoMovimentacao;

    @Column(name = "ds_situacao_fase_divida", nullable = false)
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @Column(name = "fl_exigivel_cobranca")
    private Boolean exigeCobranca;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_fase_divida_tp_cobranca", joinColumns =
    @JoinColumn(name = "cd_fase_divida"))
    @Column(name = "tp_cobranca", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<TipoCobranca> tipoCobranca = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "tb_fluxo_fase_divida",
            joinColumns = @JoinColumn(name = "cd_fase_divida_atual", referencedColumnName = "ci_fase_divida"),
            inverseJoinColumns = @JoinColumn(name = "cd_fase_divida_permitida", referencedColumnName = "ci_fase_divida")
    )
    @NotAudited
    @JsonIgnore
    @JsonIgnoreProperties("fasesPermitidas")
    private Set<FaseDivida> fasesPermitidas;

    public String toStringMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
