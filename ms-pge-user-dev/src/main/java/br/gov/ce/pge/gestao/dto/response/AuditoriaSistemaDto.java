package br.gov.ce.pge.gestao.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuditoriaSistemaDto extends AuditoriaDto {

	private static final long serialVersionUID = 5338074130491842414L;

	private String id;

    private String nome;
    
    private String idsAdicionados;

    private String idsRemovidos;

}
