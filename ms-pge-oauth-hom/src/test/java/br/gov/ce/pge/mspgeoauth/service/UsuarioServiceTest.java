package br.gov.ce.pge.mspgeoauth.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import br.gov.ce.pge.mspgeoauth.client.UsuarioFeingClient;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import br.gov.ce.pge.mspgeoauth.entity.UsuarioTest;

@ExtendWith({ MockitoExtension.class })
public class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    UsuarioFeingClient client;

    @Test
    void test_load_username() {
        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioInterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(client.findByCpf(any())).thenReturn(responseEntity);

        UserDetails userDetails = usuarioService.loadUserByUsername(any());
        assertNotNull(userDetails);
    }

    @Test
    void test_class() {
        assertNotNull(usuarioService);
    }

    @Test
    void test_principal_usuario() {
        Usuario result = usuarioService.getPrincipalUsuario(UsuarioTest.getUsuarioExterno(), "username");

        assertNotNull(result);
    }

    @Test
    void test_principal_ldap() {

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioInterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);

        Usuario user = usuarioService.getPrincipalUsuario("usuario", "username");

        assertNotNull(user);
    }

    @Test
    void test_set_ultimo_login() {
        doNothing().when(client).setUltimoLogin(any());

        usuarioService.setUltimoLoginUsuario(any());
    }

}
