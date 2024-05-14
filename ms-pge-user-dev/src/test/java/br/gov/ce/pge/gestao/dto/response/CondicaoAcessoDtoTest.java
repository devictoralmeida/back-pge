package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CondicaoAcessoDtoTest {
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

    @Test
    public void test_get_condicao_acessoDto() {
        CondicaoAcessoDto condicaoAcessoDto = CondicaoAcessoDtoTest.getCondicaoAcessoDto();

        assertEquals("5027c7f7-622b-4161-ac75-97c9110553f2", condicaoAcessoDto.getId());
        assertEquals(1, condicaoAcessoDto.getBloqueioAutomatico());
        assertEquals(1, condicaoAcessoDto.getAlteracaoSenha());
        assertEquals("12:34", condicaoAcessoDto.getEncerramentoSessao());
        assertEquals(1, condicaoAcessoDto.getTentativasInvalidas());
        assertEquals(1, condicaoAcessoDto.getSenhasCadastradas());
    }

    @Test
    public void testGetterAndSetter() {
        CondicaoAcessoDto dto = new CondicaoAcessoDto();

        dto.setBloqueioAutomatico(2);
        dto.setAlteracaoSenha(14);
        dto.setEncerramentoSessao("43:21");
        dto.setTentativasInvalidas(3);
        dto.setSenhasCadastradas(15);

        assertEquals(2, dto.getBloqueioAutomatico());
        assertEquals(14, dto.getAlteracaoSenha());
        assertEquals("43:21", dto.getEncerramentoSessao());
        assertEquals(3, dto.getTentativasInvalidas());
        assertEquals(15, dto.getSenhasCadastradas());

    }
}
