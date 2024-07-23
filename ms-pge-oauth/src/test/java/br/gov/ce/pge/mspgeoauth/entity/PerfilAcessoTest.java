package br.gov.ce.pge.mspgeoauth.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.mspgeoauth.enums.Situacao;

public class PerfilAcessoTest {

    public static PerfilAcesso getPerfilAcesso() {
        var model = new PerfilAcesso();
        model.setId(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd"));
        model.setNome("ADMIN");
        model.setSituacao(Situacao.ATIVA);
        model.setSistemas(Arrays.asList(SistemaTest.getSistema()));
        model.setPermissoes(Arrays.asList(PermissaoTest.getPermissao()));
        return model;
    }
    
    @Test
	public void all_fiedls() {
		var model = getPerfilAcesso();
		
		assertNotNull(model);
		assertNotNull(model.equals(getPerfilAcesso()));
		assertNotNull(model.hashCode());
		assertEquals(getPerfilAcesso().getNome(), model.getNome());
		assertEquals(getPerfilAcesso().getSituacao(), model.getSituacao());
	}

}
