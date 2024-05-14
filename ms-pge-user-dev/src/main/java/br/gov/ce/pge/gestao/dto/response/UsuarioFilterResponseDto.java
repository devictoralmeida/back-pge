package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioFilterResponseDto implements Serializable {
	
	private static final long serialVersionUID = -160384726072354620L;

	private String id;
	
	private TipoUsuario tipoUsuario;
	
	private String cpf;
	
	private String nome;
	
	private String orgao;
	
	private String email;

	private String area;

	private String usuarioRede;
	
	private List<SistemaFilterResponseDto> sistemas;
	
	private List<PerfilAcessoFilterResponseDto> perfisAcessos;
	
	private SituacaoUsuario situacao;

	private LocalDateTime dataCadastro;
	
	private LocalDateTime dataAtualizacao;
	
	private LocalDateTime dataUltimoAcesso;

	private LocalDateTime dataAceiteTermoPortalDivida;

	private LocalDateTime dataAceiteTermoPortalOrigens;
}
