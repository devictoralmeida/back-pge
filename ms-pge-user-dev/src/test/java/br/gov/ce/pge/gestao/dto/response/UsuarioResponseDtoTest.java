package br.gov.ce.pge.gestao.dto.response;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.entity.UsuarioTest;
import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import br.gov.ce.pge.gestao.enums.TipoUsuario;

public class UsuarioResponseDtoTest {

    public static UsuarioResponseDto getUsuarioResponse() {
        var response = new UsuarioResponseDto();
        response.setId(UsuarioTest.getUsuario().getId());
        response.setNome(UsuarioTest.getUsuario().getNome());
        response.setCpf(UsuarioTest.getUsuario().getCpf());
        response.setArea(UsuarioTest.getUsuario().getArea());
        response.setEmail(UsuarioTest.getUsuario().getEmail());
        response.setOrgao(UsuarioTest.getUsuario().getOrgao());
        response.setTipoUsuario(UsuarioTest.getUsuario().getTipoUsuario());

        return response;
    }
    
    
    private UsuarioResponseDto usuarioResponseDto;

    @BeforeEach
    public void setUp() {
        usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setId(UUID.randomUUID());
        usuarioResponseDto.setNome("John Doe");
        usuarioResponseDto.setSituacao(SituacaoUsuario.ATIVA);
        usuarioResponseDto.setUsuarioRede("johndoe");
        usuarioResponseDto.setEmail("john.doe@example.com");
        usuarioResponseDto.setArea("Some Area");
        usuarioResponseDto.setOrgao("Some Organization");
        usuarioResponseDto.setCpf("12345678901");
        usuarioResponseDto.setTipoUsuario(TipoUsuario.INTERNO);
        usuarioResponseDto.setDataCriacao(LocalDateTime.now());
        usuarioResponseDto.setDataAtualizacao(LocalDateTime.now());
    }

    @Test
    public void test_getter_and_setters() {
        assertEquals(usuarioResponseDto.getId(), usuarioResponseDto.getId());
        assertEquals(usuarioResponseDto.getNome(), "John Doe");
        assertEquals(usuarioResponseDto.getSituacao(), SituacaoUsuario.ATIVA);
        assertEquals(usuarioResponseDto.getUsuarioRede(), "johndoe");
        assertEquals(usuarioResponseDto.getEmail(), "john.doe@example.com");
        assertEquals(usuarioResponseDto.getArea(), "Some Area");
        assertEquals(usuarioResponseDto.getOrgao(), "Some Organization");
        assertEquals(usuarioResponseDto.getCpf(), "12345678901");
        assertEquals(usuarioResponseDto.getTipoUsuario(), TipoUsuario.INTERNO);
        assertNotNull(usuarioResponseDto.getDataCriacao());
        assertNotNull(usuarioResponseDto.getDataAtualizacao());

        assertDoesNotThrow(() -> {
            usuarioResponseDto.setSistemas(null);
            usuarioResponseDto.setPerfisAcessos(null);
        });
        assertNull(usuarioResponseDto.getSistemas());
        assertNull(usuarioResponseDto.getPerfisAcessos());
    }

}
