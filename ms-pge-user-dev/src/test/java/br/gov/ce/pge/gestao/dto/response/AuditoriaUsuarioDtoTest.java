package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoUsuario;

class AuditoriaUsuarioDtoTest {

	@Test
    public void test_getter_and_setter() {
        AuditoriaUsuarioDto dto = new AuditoriaUsuarioDto();
        dto.setId("1");
        dto.setNome("TestNome");
        dto.setSituacaoUsuario(SituacaoUsuario.ATIVA);
        dto.setCpf("12345678901");
        dto.setEmail("test@example.com");
        dto.setOrgao("TestOrgao");
        dto.setArea("TestArea");
        dto.setTipoUsuario(TipoUsuario.EXTERNO);
        dto.setUsuarioRede("testuser");
        dto.setIdsSistemaAdicionados("1,2,3");
        dto.setIdsSistemaRemovidos("4,5");
        dto.setIdsPerfisAdicionados("6,7");
        dto.setIdsPerfisRemovidos("8,9");

        assertEquals("1", dto.getId());
        assertEquals("TestNome", dto.getNome());
        assertEquals(SituacaoUsuario.ATIVA, dto.getSituacaoUsuario());
        assertEquals("12345678901", dto.getCpf());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("TestOrgao", dto.getOrgao());
        assertEquals("TestArea", dto.getArea());
        assertEquals(TipoUsuario.EXTERNO, dto.getTipoUsuario());
        assertEquals("testuser", dto.getUsuarioRede());
        assertEquals("1,2,3", dto.getIdsSistemaAdicionados());
        assertEquals("4,5", dto.getIdsSistemaRemovidos());
        assertEquals("6,7", dto.getIdsPerfisAdicionados());
        assertEquals("8,9", dto.getIdsPerfisRemovidos());
    }

}
