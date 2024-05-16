package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuditoriaModuloDto extends AuditoriaDto {

	private static final long serialVersionUID = -5771217095784387939L;

	private String id;

    private String nome;
    
    private String idsAdicionados;

    private String idsRemovidos;

}
