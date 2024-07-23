package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.entity.PerfilAcessoTest;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.enums.Situacao;

public class PerfilAcessoResponseDtoTest {

	public static PerfilAcessoResponseDto getPerfilAcessoResponse() {
		var res = new PerfilAcessoResponseDto();
		res.setId(PerfilAcessoTest.getPerfilAcesso().getId());
		res.setNome(PerfilAcessoTest.getPerfilAcesso().getNome());
		res.setSituacao(Situacao.ATIVA);
		res.setSistemas(PerfilAcessoTest.getPerfilAcesso().getSistemas());
		return res;
	}
	
	
	@Test
    public void test_get_perfil_acesso_response() {
        PerfilAcessoResponseDto perfilAcessoResponseDto = PerfilAcessoResponseDtoTest.getPerfilAcessoResponse();

        assertNotNull(perfilAcessoResponseDto);
        assertEquals(PerfilAcessoTest.getPerfilAcesso().getId(), perfilAcessoResponseDto.getId());
        assertEquals(PerfilAcessoTest.getPerfilAcesso().getNome(), perfilAcessoResponseDto.getNome());
        assertEquals(Situacao.ATIVA, perfilAcessoResponseDto.getSituacao());
    }

    @Test
    public void test_set_and_getId() {
        PerfilAcessoResponseDto perfilAcessoResponseDto = new PerfilAcessoResponseDto();
        perfilAcessoResponseDto.setId(UUID.randomUUID());

        assertNotNull(perfilAcessoResponseDto.getId());
    }

    @Test
    public void test_set_and_getNome() {
        PerfilAcessoResponseDto perfilAcessoResponseDto = new PerfilAcessoResponseDto();
        perfilAcessoResponseDto.setNome("Novo Perfil");

        assertEquals("Novo Perfil", perfilAcessoResponseDto.getNome());
    }

    @Test
    public void test_set_and_get_situacao() {
        PerfilAcessoResponseDto perfilAcessoResponseDto = new PerfilAcessoResponseDto();
        perfilAcessoResponseDto.setSituacao(Situacao.INATIVA);

        assertEquals(Situacao.INATIVA, perfilAcessoResponseDto.getSituacao());
    }

    @Test
    public void test_set_and_get_sistemas() {
        PerfilAcessoResponseDto perfilAcessoResponseDto = new PerfilAcessoResponseDto();
		List<Sistema> sistemas = Arrays.asList(new Sistema(), new Sistema());
		perfilAcessoResponseDto.setSistemas(sistemas);

		assertEquals(sistemas, perfilAcessoResponseDto.getSistemas());
    }

}
