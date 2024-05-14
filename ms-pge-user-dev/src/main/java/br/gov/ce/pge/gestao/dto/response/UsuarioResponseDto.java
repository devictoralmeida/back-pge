package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.entity.PerfilAcesso;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioResponseDto implements Serializable {

    private static final long serialVersionUID = 5010929940782608889L;

    private UUID id;

    private String nome;

    private SituacaoUsuario situacao;

    private List<Sistema> sistemas;

    private List<PerfilAcesso> perfisAcessos;

    private String usuarioRede;

    private String email;

    private String area;

    private String orgao;

    private String cpf;

    private TipoUsuario tipoUsuario;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private LocalDateTime dataAceiteTermoPortalDivida;

    private LocalDateTime dataAceiteTermoPortalOrigens;

    private LocalDateTime horaBloqueio;

    private LocalDateTime dataUltimaAlteracaoSenha;

    private LocalDateTime dataUltimoAcesso;
}
