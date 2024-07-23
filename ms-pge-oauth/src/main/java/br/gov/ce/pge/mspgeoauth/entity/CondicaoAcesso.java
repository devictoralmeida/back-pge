package br.gov.ce.pge.mspgeoauth.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CondicaoAcesso {

    private UUID id;

    private int bloqueioAutomatico;

    private int alteracaoSenha;

    private String encerramentoSessao;

    private int tentativasInvalidas;

    private int senhasCadastradas;

    private int intervaloBloqueio;
}
