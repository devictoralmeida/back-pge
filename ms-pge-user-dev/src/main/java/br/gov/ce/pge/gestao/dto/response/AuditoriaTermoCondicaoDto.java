package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditoriaTermoCondicaoDto extends AuditoriaDto{

    private static final long serialVersionUID = -2770179354889390621L;

    private String id;

    private String versao;

    private String conteudo;

}
