package br.gov.ce.pge.gestao.entity;

import br.gov.ce.pge.gestao.dto.response.CondicaoAcessoDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CondicaoAcessoTest {

    public static CondicaoAcesso getCondicaoAcesso() {
        var condicaoAcesso = new CondicaoAcesso();
        condicaoAcesso.setId(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"));
        condicaoAcesso.setAlteracaoSenha(1);
        condicaoAcesso.setBloqueioAutomatico(1);
        condicaoAcesso.setEncerramentoSessao("12:34");
        condicaoAcesso.setSenhasCadastradas(1);
        condicaoAcesso.setTentativasInvalidas(1);
        condicaoAcesso.setIntervaloBloqueio(1);
        condicaoAcesso.setDataCriacao(LocalDateTime.now());
        condicaoAcesso.setNomeUsuarioCadastro("anônimo");

        return condicaoAcesso;
    }



    public static CondicaoAcessoDto getCondicaoAcessoDto() {
        var condicaoAcesso = new CondicaoAcessoDto();
        condicaoAcesso.setId("5027c7f7-622b-4161-ac75-97c9110553f2");
        condicaoAcesso.setAlteracaoSenha(1);
        condicaoAcesso.setBloqueioAutomatico(1);
        condicaoAcesso.setEncerramentoSessao("12:34");
        condicaoAcesso.setSenhasCadastradas(1);
        condicaoAcesso.setTentativasInvalidas(1);
        return condicaoAcesso;
    }

    public static CondicaoAcesso getCondicaoAcessoUpdate() {
        var condicaoAcesso = new CondicaoAcesso();
        condicaoAcesso.setId(UUID.fromString("5027c7f7-622b-4161-ac75-97c9110553f2"));
        condicaoAcesso.setBloqueioAutomatico(2);
        condicaoAcesso.setAlteracaoSenha(14);
        condicaoAcesso.setEncerramentoSessao("43:21");
        condicaoAcesso.setTentativasInvalidas(3);
        condicaoAcesso.setSenhasCadastradas(15);
        condicaoAcesso.setIntervaloBloqueio(1);
        condicaoAcesso.setDataCriacao(LocalDateTime.now());
        condicaoAcesso.setNomeUsuarioCadastro("anônimo");
        return condicaoAcesso;
    }

    public static List<CondicaoAcesso> getListCondicaoAcesso() {
        return List.of(getCondicaoAcesso());
    }

    public static List<CondicaoAcessoDto> getListCondicaoAcessoDto() {
        return List.of(getCondicaoAcessoDto());
    }

    @Test
    public void teste_all_fields() {
        var model = getCondicaoAcesso();
        model.setDataAtualizacao(LocalDateTime.now());
        model.prePersist();
        model.preUpdate();

        assertNotNull(model);
        assertNotNull(model.getDataAtualizacao());
        assertNotNull(model.getDataCriacao());
        assertNotNull(model.equals(getCondicaoAcessoUpdate()));
        assertNotNull(model.equals(getCondicaoAcesso()));
        assertNotNull(model.hashCode());
        assertEquals(getCondicaoAcesso().getId(), model.getId());
        assertEquals(getCondicaoAcesso().getAlteracaoSenha(), model.getAlteracaoSenha());
        assertEquals(getCondicaoAcesso().getBloqueioAutomatico(), model.getBloqueioAutomatico());
        assertEquals(getCondicaoAcesso().getEncerramentoSessao(), model.getEncerramentoSessao());
        assertEquals(getCondicaoAcesso().getSenhasCadastradas(), model.getSenhasCadastradas());
        assertEquals(getCondicaoAcesso().getTentativasInvalidas(), model.getTentativasInvalidas());
        assertEquals("anônimo", model.getNomeUsuarioAtualizacao());
        assertEquals("anônimo", model.getNomeUsuarioCadastro());
    }
}
