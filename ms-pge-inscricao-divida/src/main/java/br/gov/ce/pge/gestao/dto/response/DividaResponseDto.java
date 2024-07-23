package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DividaResponseDto implements Serializable {

    private static final long serialVersionUID = 7648299184858399353L;

    private UUID id;

    private UUID idTipoReceita;

    private UUID idOrigemDebito;

    private UUID idProdutoServico;

    private String disposicoesLegais;

    private String naturezaFundamentacao;

    private String inexistenciaCausaSuspensivas;

    private String numeroDocumento;

    private LocalDate dataDocumento;

    private String termoRevelia;

    private LocalDate dataTermoRevelia;

    private String numeroOficio;

    private String numeroProcessoAdministrativo;

    private LocalDate dataProcesso;

    private String numeroAcordao;

    private LocalDate dataTransitoJulgado;

    private LocalDate dataConstituicaoDefinitivaCredito;

    private String placaVeiculo;

    private String numeroChassi;

    private String guiaItcd;

    private String sequencialParcelamento;

    private String protocoloJudicial;

    private LocalDateTime dataDeclaracaoAusenciaCorresponsaveis;

    private VaraOrigem varaOrigem;

    private TipoProcesso tipoProcesso;

    private TipoDocumento tipoDocumento;

    private String numeroInscricao;

    private List<Anexo> anexos;

    private List<Debito> debitos;

    private List<DividaPessoa> dividaPessoas;

    private List<AcaoJudicial> acoesJudiciais;

    private List<FaseDivida> fasesDivida;

    private String faseAtualEm;
}
