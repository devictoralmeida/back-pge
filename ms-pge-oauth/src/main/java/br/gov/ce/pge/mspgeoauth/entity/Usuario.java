package br.gov.ce.pge.mspgeoauth.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.ce.pge.mspgeoauth.enums.SituacaoUsuario;
import br.gov.ce.pge.mspgeoauth.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Usuario implements UserDetails {

	private static final long serialVersionUID = -1132282936018121221L;

	private UUID id;

	private TipoUsuario tipoUsuario;

	private String cpf;

	private String nome;

	private String orgao;

	private String area;

	private String email;

	private String usuarioRede;

	private List<Sistema> sistemas;

	private List<PerfilAcesso> perfisAcessos;

	private SituacaoUsuario situacao;

	private String senha;

	@JsonIgnore
	private List<TermoCondicao> termos;

	private LocalDateTime dataAceiteTermoPortalDivida;

	private LocalDateTime dataAceiteTermoPortalOrigens;

	private LocalDateTime dataUltimaAlteracaoSenha;

	private String ultimoIp;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfisAcessos.stream()
				.map(perfil -> new SimpleGrantedAuthority(perfil.getNome()))
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		return this.cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.situacao == SituacaoUsuario.ATIVA;
	}
}
