package br.gov.ce.pge.gestao.dto.response;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuditoriaUsuarioDto extends AuditoriaDto{

	private static final long serialVersionUID = -6859400770499638170L;

	private String id;

    private String nome;

    private SituacaoUsuario situacaoUsuario;

    private String cpf;

    private String email;

    private String orgao;

    private String area;

    private TipoUsuario tipoUsuario;

    private String usuarioRede;

    private String idsSistemaAdicionados;

    private String idsSistemaRemovidos;

    private String idsPerfisAdicionados;

    private String idsPerfisRemovidos;

}
