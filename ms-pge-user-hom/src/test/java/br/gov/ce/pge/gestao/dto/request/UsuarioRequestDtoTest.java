package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.gov.ce.pge.gestao.enums.SituacaoUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.gov.ce.pge.gestao.enums.TipoUsuario;

public class UsuarioRequestDtoTest {

    public static UsuarioRequestDto getRequest() {
        var request = new UsuarioRequestDto();

        request.setNome("teste usuario");
        request.setCpf("01234567890");
        request.setSituacao(SituacaoUsuario.ATIVA);
        request.setEmail("teste@pge.ce.gov.br");
        request.setOrgao("orgao teste");
        request.setTipoUsuario(TipoUsuario.EXTERNO);
        request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
        request.setPerfisAcessos(List.of(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd")));
        request.setUsuarioRede("teste");

        return request;
    }

    public static UsuarioRequestDto getRequestUsuarioInterno() {
        var request = new UsuarioRequestDto();

        request.setNome("teste usuario");
        request.setCpf("01234567890");
        request.setSituacao(SituacaoUsuario.ATIVA);
        request.setEmail("teste@pge.ce.gov.br");
        request.setOrgao("Procuradoria-Geral do Estado do Ceará");
        request.setTipoUsuario(TipoUsuario.INTERNO);
        request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
        request.setPerfisAcessos(List.of(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd")));
        request.setUsuarioRede("outro teste2");

        return request;
    }

    public static UsuarioRequestDto getRequestObjetoIgual() {
        var request = new UsuarioRequestDto();

        request.setNome("teste usuario");
        request.setCpf("01234567890");
        request.setSituacao(SituacaoUsuario.ATIVA);
        request.setEmail("teste@pge.ce.gov.br");
        request.setOrgao("Procuradoria-Geral do Estado do Ceará");
        request.setTipoUsuario(TipoUsuario.INTERNO);
        request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
        request.setPerfisAcessos(List.of(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd")));
        request.setUsuarioRede("outro teste");
        request.setArea("area teste");

        return request;
    }
    
    public static UsuarioRequestDto getRequestOther() {
        var request = new UsuarioRequestDto();

        request.setNome("teste usuario pge");
        request.setCpf("98765432100");
        request.setSituacao(SituacaoUsuario.ATIVA);
        request.setEmail("testeoutro@pge.ce.gov.br");
        request.setOrgao("orgao teste");
        request.setTipoUsuario(TipoUsuario.EXTERNO);
        request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
        request.setPerfisAcessos(List.of(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd")));
        request.setUsuarioRede("outro");

        return request;
    }

    public static UsuarioRequestDto getRequestSemNome() {
        var request = new UsuarioRequestDto();

        request.setCpf("01234567890");
        request.setSituacao(SituacaoUsuario.ATIVA);
        request.setEmail("teste@pge.ce.gov.br");
        request.setOrgao("orgao teste");
        request.setTipoUsuario(TipoUsuario.EXTERNO);
        request.setSistemas(List.of(UUID.fromString("f08cc600-1268-4e35-8279-63ecef41454b")));
        request.setPerfisAcessos(List.of(UUID.fromString("a597aa9e-0a05-48de-adcb-a7a1b631aedd")));

        return request;
    }
    
    
    private UsuarioRequestDto usuarioRequestDto;

    @BeforeEach
    public void setUp() {
        usuarioRequestDto = new UsuarioRequestDto();
        usuarioRequestDto.setTipoUsuario(TipoUsuario.EXTERNO);
        usuarioRequestDto.setCpf("12345678901");
        usuarioRequestDto.setNome("John Doe");
        usuarioRequestDto.setOrgao("Some Organization");
        usuarioRequestDto.setArea("Some Area");
        usuarioRequestDto.setEmail("john.doe@example.com");
        usuarioRequestDto.setUsuarioRede("johndoe");
        usuarioRequestDto.setSistemas(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        usuarioRequestDto.setPerfisAcessos(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        usuarioRequestDto.setSituacao(SituacaoUsuario.ATIVA);
    }

    @Test
    public void test_getter_and_setters() {
        assertEquals(usuarioRequestDto.getTipoUsuario(), TipoUsuario.EXTERNO);
        assertEquals(usuarioRequestDto.getCpf(), "12345678901");
        assertEquals(usuarioRequestDto.getNome(), "John Doe");
        assertEquals(usuarioRequestDto.getOrgao(), "Some Organization");
        assertEquals(usuarioRequestDto.getArea(), "Some Area");
        assertEquals(usuarioRequestDto.getEmail(), "john.doe@example.com");
        assertEquals(usuarioRequestDto.getUsuarioRede(), "johndoe");
        assertNotNull(usuarioRequestDto.getSistemas());
        assertEquals(usuarioRequestDto.getSistemas().size(), 2);
        assertNotNull(usuarioRequestDto.getPerfisAcessos());
        assertEquals(usuarioRequestDto.getPerfisAcessos().size(), 2);
        assertEquals(usuarioRequestDto.getSituacao(), SituacaoUsuario.ATIVA);
    }
}
