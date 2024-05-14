package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class CondicaoAcessoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private int bloqueioAutomatico;

    private int alteracaoSenha;

    private String encerramentoSessao;

    private int tentativasInvalidas;

    private int senhasCadastradas;

    private int intervaloBloqueio;
}
