package br.gov.ce.pge.mspgeoauth.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class CondicaoAcessoTest {

    public static CondicaoAcesso getCondicaoAcesso() {
        var condicaoAcesso = new CondicaoAcesso();
        condicaoAcesso.setId(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"));
        condicaoAcesso.setAlteracaoSenha(1);
        condicaoAcesso.setBloqueioAutomatico(1);
        condicaoAcesso.setEncerramentoSessao("12:34");
        condicaoAcesso.setSenhasCadastradas(1);
        condicaoAcesso.setTentativasInvalidas(1);
        condicaoAcesso.setAlteracaoSenha(30);
        condicaoAcesso.setTentativasInvalidas(1);
        condicaoAcesso.setIntervaloBloqueio(10);

        return condicaoAcesso;
    }
    
    @Test
    public void teste_all_fields() {
        var model = getCondicaoAcesso();

        assertNotNull(model);
        assertNotNull(model.equals(getCondicaoAcesso()));
        assertNotNull(model.hashCode());
        assertEquals(getCondicaoAcesso().getId(), model.getId());
        assertEquals(getCondicaoAcesso().getAlteracaoSenha(), model.getAlteracaoSenha());
        assertEquals(getCondicaoAcesso().getBloqueioAutomatico(), model.getBloqueioAutomatico());
        assertEquals(getCondicaoAcesso().getEncerramentoSessao(), model.getEncerramentoSessao());
        assertEquals(getCondicaoAcesso().getSenhasCadastradas(), model.getSenhasCadastradas());
        assertEquals(getCondicaoAcesso().getTentativasInvalidas(), model.getTentativasInvalidas());
    }

}
