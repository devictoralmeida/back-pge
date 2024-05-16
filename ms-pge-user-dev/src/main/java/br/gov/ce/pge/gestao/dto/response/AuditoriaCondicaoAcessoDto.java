package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriaCondicaoAcessoDto extends AuditoriaDto {

    private static final long serialVersionUID = 1L;

    private String id;

    private int bloqueioAutomatico;

    private int alteracaoSenha;

    private String encerramentoSessao;

    private int tentativasInvalidas;

    private int senhasCadastradas;

}
