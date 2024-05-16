package br.gov.ce.pge.gestao.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioTermoResponseDtoTest {

    private UsuarioTermoResponseDto usuarioTermoResponseDto;

    @BeforeEach
    void setUp() {
        usuarioTermoResponseDto = new UsuarioTermoResponseDto();
        usuarioTermoResponseDto.setId(UUID.randomUUID());
        usuarioTermoResponseDto.setNome("Teste dto");
        usuarioTermoResponseDto.setOrgao("Orgao teste");
        usuarioTermoResponseDto.setDataAceiteTermoPortalDivida(LocalDateTime.now());
        usuarioTermoResponseDto.setDataAceiteTermoPortalOrigens(LocalDateTime.now());
    }

    @Test
    void test_getter_and_setters() {
        assertNotNull(usuarioTermoResponseDto);
        assertEquals(usuarioTermoResponseDto.getId(), usuarioTermoResponseDto.getId());
        assertEquals(usuarioTermoResponseDto.getNome(), "Teste dto");
        assertEquals(usuarioTermoResponseDto.getOrgao(), "Orgao teste");
        assertNotNull(usuarioTermoResponseDto.getDataAceiteTermoPortalDivida());
        assertNotNull(usuarioTermoResponseDto.getDataAceiteTermoPortalOrigens());
    }
}
