package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HistoricoAtualizacaoResponseDto implements Serializable {

    private static final long serialVersionUID = -2130513080207946087L;

    private transient List<Object> dadosAlterados;

    private String responsavel;

    private String dataAlterado;
}
