package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.Situacao;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuditoriaPerfilAcessoDto extends AuditoriaDto {

	private static final long serialVersionUID = 5338074130491842414L;

	private String id;

    private String nome;
    
    private Situacao situacao;
    
    private String idsAdicionados;

    private String idsRemovidos;
    
    private String idsSistemaAdicionados;
    
    private String idsSistemaRemovidos;

}
