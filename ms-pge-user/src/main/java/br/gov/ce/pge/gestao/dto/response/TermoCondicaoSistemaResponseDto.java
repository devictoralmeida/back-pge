package br.gov.ce.pge.gestao.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TermoCondicaoSistemaResponseDto implements Serializable {

	private static final long serialVersionUID = 2781226911716607854L;

	private String id;

	private String nomeSistema;

	private String idSistema;

	private String versao;

	private String conteudo;

	private LocalDateTime dataCriacao;

	private String nomeUsuario;

	private int qtdPendente;

	private List<UsuarioTermoResponseDto> usuariosPendentes;

	private int qtdAceite;

	private List<UsuarioTermoResponseDto> usuariosAceites;

	private LocalDateTime dataAtualizacao;
}
