package br.gov.ce.pge.gestao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.Situacao;

public class PerfilAcessoTest {

	public static PerfilAcesso getPerfilAcesso() {
		var model = new PerfilAcesso();
		model.setId(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
		model.setNome("ADMIN");
		model.setSituacao(Situacao.ATIVA);
		model.setSistemas(SistemaTest.getListSistema());
		model.setPermissoes(PermissaoTest.getListPermissao());
		return model;
	}
	
	public static PerfilAcesso getPerfilAcessoUpdate() {
		var model = new PerfilAcesso();
		model.setId(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
		model.setNome("ADMIN");
		model.setSituacao(Situacao.INATIVA);
		model.setSistemas(SistemaTest.getListSistema());
		model.setPermissoes(PermissaoTest.getListPermissao());
		return model;
	}
	
	public static PerfilAcesso getPerfilAcessoUpdate_com_outro_nome() {
		var model = new PerfilAcesso();
		model.setId(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
		model.setNome("ADMIN update");
		model.setSituacao(Situacao.INATIVA);
		model.setSistemas(SistemaTest.getListSistema());
		model.setPermissoes(PermissaoTest.getListPermissao());
		return model;
	}
	
	public static List<PerfilAcesso> getListaPerfilAcesso() {
		return List.of(getPerfilAcesso());
	}
	
	@Test
	public void all_fiedls() {
		var model = getPerfilAcesso();
		model.setDataAtualizacao(LocalDateTime.now());
		model.setDataCriacao(LocalDateTime.now());
		model.prePersist();
		model.preUpdate();
		
		assertNotNull(model);
		assertNotNull(model.getDataAtualizacao());
		assertNotNull(model.getDataCriacao());
		assertNotNull(model.equals(getPerfilAcesso()));
		assertNotNull(model.equals(getPerfilAcessoUpdate()));
		assertNotNull(model.hashCode());
		assertEquals(getPerfilAcesso().getNome(), model.getNome());
		assertEquals(getPerfilAcesso().getSituacao(), model.getSituacao());
		assertEquals(getPerfilAcesso().getSistemas().toString(), model.getSistemas().toString());
		assertEquals(getPerfilAcesso().getPermissoes().toString(), model.getPermissoes().toString());
		assertEquals(null, model.getNomeUsuarioAtualizacao());
		assertEquals(null, model.getNomeUsuarioCadastro());
	}
	
}
