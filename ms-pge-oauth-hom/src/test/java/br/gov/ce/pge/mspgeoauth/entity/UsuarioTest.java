package br.gov.ce.pge.mspgeoauth.entity;

import br.gov.ce.pge.mspgeoauth.enums.SituacaoUsuario;
import br.gov.ce.pge.mspgeoauth.enums.TipoUsuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioTest {

	public static Usuario getUsuarioInterno() {
		var model = new Usuario();
		model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
		model.setSituacao(SituacaoUsuario.ATIVA);
		model.setNome("teste usuario");
		model.setEmail("teste@pge.ce.gov.br");
		model.setCpf("01234567890");
		model.setArea("area teste");
		model.setOrgao("orgao teste");
		model.setUsuarioRede("teste");
		model.setTipoUsuario(TipoUsuario.INTERNO);
		model.setDataUltimaAlteracaoSenha(LocalDateTime.now().minus(10, ChronoUnit.DAYS));

		return model;
	}

	public static Usuario getUsuarioExterno() {
		var model = new Usuario();
		model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
		model.setSituacao(SituacaoUsuario.ATIVA);
		model.setNome("teste usuario");
		model.setEmail("teste@pge.ce.gov.br");
		model.setCpf("01234567890");
		model.setArea("area teste");
		model.setOrgao("orgao teste");
		model.setUsuarioRede("teste");
		model.setTipoUsuario(TipoUsuario.EXTERNO);
		model.setSenha("$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.");
		model.setDataUltimaAlteracaoSenha(LocalDateTime.now().minus(10, ChronoUnit.DAYS));
		model.setPerfisAcessos(Arrays.asList(PerfilAcessoTest.getPerfilAcesso()));
		model.setSistemas(Arrays.asList(SistemaTest.getSistema()));

		return model;
	}

	public static Usuario getUsuarioInativo() {
		var model = new Usuario();
		model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
		model.setSituacao(SituacaoUsuario.INATIVA);
		model.setNome("teste usuario");
		model.setEmail("teste@pge.ce.gov.br");
		model.setCpf("01234567890");
		model.setArea("area teste");
		model.setOrgao("orgao teste");
		model.setUsuarioRede("teste");
		model.setTipoUsuario(TipoUsuario.EXTERNO);
		model.setSenha("$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.");
		model.setDataUltimaAlteracaoSenha(LocalDateTime.now().minus(10, ChronoUnit.DAYS));
		model.setPerfisAcessos(Arrays.asList(PerfilAcessoTest.getPerfilAcesso()));
		model.setSistemas(Arrays.asList(SistemaTest.getSistema()));

		return model;
	}

	public static Usuario getUsuarioSenhaExpirada() {
		var model = new Usuario();
		model.setId(UUID.fromString("c4095434-f704-4209-be74-3d42d519d438"));
		model.setSituacao(SituacaoUsuario.INATIVA);
		model.setNome("teste usuario");
		model.setEmail("teste@pge.ce.gov.br");
		model.setCpf("01234567890");
		model.setArea("area teste");
		model.setOrgao("orgao teste");
		model.setUsuarioRede("teste");
		model.setTipoUsuario(TipoUsuario.EXTERNO);
		model.setSenha("$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.");
		model.setDataUltimaAlteracaoSenha(LocalDateTime.now().minus(40, ChronoUnit.DAYS));
		model.setPerfisAcessos(Arrays.asList(PerfilAcessoTest.getPerfilAcesso()));
		model.setSistemas(Arrays.asList(SistemaTest.getSistema()));

		return model;
	}

	@Test
	void test_getUsernamme() {
		var usuario = new Usuario();
		usuario.setCpf("12345678990");
		assertEquals("12345678990", usuario.getUsername());
	}

	@Test
	void test_isAccount_nonExpired() {
		var usuario = new Usuario();
		assertEquals(true, usuario.isAccountNonExpired());
	}

	@Test
	void test_isAccount_nonLocked() {
		var usuario = new Usuario();
		assertEquals(true, usuario.isAccountNonLocked());
	}

	@Test
	void test_isCredentials_nonExpired() {
		var usuario = new Usuario();
		assertEquals(true, usuario.isCredentialsNonExpired());
	}

	@Test
	void test_isEnabled() {
		var usuario = new Usuario();
		usuario.setSituacao(SituacaoUsuario.ATIVA);
		assertEquals(true, usuario.isEnabled());
	}

	@Test
	void test_isNotEnabled() {
		var usuario = new Usuario();
		usuario.setSituacao(SituacaoUsuario.INATIVA);
		assertEquals(false, usuario.isEnabled());
	}

	@Test
	void test_classe_termoCondicao() {
		var termo = new TermoCondicao();
		assertNotNull(termo);
	}

}
