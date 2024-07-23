package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.constants.IdsConstants;
import br.gov.ce.pge.gestao.shared.auditoria.AuditoriaUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "tb_divida")
public class Divida extends AuditoriaUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ci_divida")
    private UUID id;

    @Column(name = "cd_tipo_receita", nullable = false)
    private UUID idTipoReceita;

    @Column(name = "cd_origem_debito", nullable = false)
    private UUID idOrigemDebito;

    @Column(name = "cd_produto_servico", nullable = false)
    private UUID idProdutoServico;

    @Column(name = "ds_disposicoes_legais", nullable = false, length = 500)
    private String disposicoesLegais;

    @Column(name = "ds_natureza_fundamentacao", length = 500)
    private String naturezaFundamentacao;

    @Column(name = "ds_inexistencia_suspensivas", nullable = false, length = 500)
    private String inexistenciaCausaSuspensivas;

    @Column(name = "nr_documento", nullable = false, length = 10)
    private String numeroDocumento;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_documento", nullable = false)
    private LocalDate dataDocumento;

    @Column(name = "ds_termo_revelia")
    private String termoRevelia;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_termo_revelia")
    private LocalDate dataTermoRevelia;

    @Column(name = "nr_oficio")
    private String numeroOficio;

    @Column(name = "nr_processo_administrativo")
    private String numeroProcessoAdministrativo;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_processo")
    private LocalDate dataProcesso;

    @Column(name = "nr_acordao")
    private String numeroAcordao;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_transito_julgado")
    private LocalDate dataTransitoJulgado;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "dt_declaracao_ausencia_corresponsavel")
    private LocalDateTime dataDeclaracaoAusenciaCorresponsaveis;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_vara_origem")
    private VaraOrigem varaOrigem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cd_tipo_processo", nullable = false)
    private TipoProcesso tipoProcesso;

    @OneToOne
    @JoinColumn(name = "cd_tipo_documento")
    private TipoDocumento tipoDocumento;

    @Column(name = "nr_inscricao", length = 13, nullable = false)
    private String numeroInscricao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divida")
    private List<Anexo> anexos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divida")
    private List<Debito> debitos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divida")
    private List<DividaPessoa> dividaPessoas = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divida")
    private List<AcaoJudicial> acoesJudiciais;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divida")
    private List<FaseDivida> fasesDivida = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divida")
    private List<MotivoAtualizacaoDivida> motivosAtualizacaoDivida = new ArrayList<>();

    public String toStringMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writeValueAsString(this);
    }

	@Transient
	private String getDocumentoTipoPessoa(UUID idTipoPapealPessoaDivida) {
		Optional<DividaPessoa> divPessoa = getDividaPessoas().stream()
				.filter(dividaPessoa -> dividaPessoa.getPapelPessoaDivida() != null)
				.filter(dividaPessoa -> idTipoPapealPessoaDivida.equals(dividaPessoa.getPapelPessoaDivida().getTipoPapelPessoaDivida().getId()))
				.findFirst();

		return divPessoa.isPresent() ? divPessoa.get().getPessoa().getDocumento() : null;
	}

    @Transient
	public String getDocumentoSucessorDivida() {
		return getDocumentoTipoPessoa(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_SUCESSOR);
	}

    @Transient
	public String getDocumentoDevedorDivida() {
		return getDocumentoTipoPessoa(IdsConstants.ID_TIPO_PAPEL_PESSOA_DIVIDA_DEVEDOR);
	}

    @Transient
	public Boolean possiuSucessor() {
		return getDocumentoSucessorDivida() != null;
	}

    @Transient
	public Boolean possiuDevedor() {
		return getDocumentoDevedorDivida() != null;
	}

    @Transient
	public Optional<FaseDivida> faseDividaAtual() {
		return getFasesDivida().stream().filter(fase -> fase.getFaseAtual()).distinct().findFirst();
	}
}
