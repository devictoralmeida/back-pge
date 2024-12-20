package br.gov.ce.pge.mspgeoauth.service;

import br.gov.ce.pge.mspgeoauth.client.CondicaoAcessoFeignClient;
import br.gov.ce.pge.mspgeoauth.client.UsuarioFeingClient;
import br.gov.ce.pge.mspgeoauth.constants.MessageCommonsContanst;
import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.*;
import br.gov.ce.pge.mspgeoauth.service.impl.CustomAuthenticationProviderServiceImpl;
import br.gov.ce.pge.mspgeoauth.shared.exception.NegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith({ MockitoExtension.class })
public class CustomAuthenticationProviderServiceTest {

    @Mock
    private UsuarioFeingClient client;

    @Mock
    private CondicaoAcessoFeignClient condicaoClient;

    @Mock
    private LdapAuthenticationProviderService ldapAuthentication;

    @InjectMocks
    private CustomAuthenticationProviderServiceImpl customAuthentication;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisService redisService;

    @Test
    void test_autenticate() throws JsonProcessingException {

        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioInterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);
        when(ldapAuthentication.authenticate(any(), eq("username"))).thenReturn(authentication);

        var authenticate = customAuthentication.authenticate(authentication);

        assertNotNull(authenticate);

    }

    @Test
    void test_authenticate_get_usuario_feign_exception() {

        when(client.findUser(any())).thenThrow(FeignException.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        assertThrows(NegocioException.class, () -> customAuthentication.authenticate(authentication));
    }

    @Test
    void test_authenticate_get_condicao_acesso_feign_exception() {

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioInterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenThrow(FeignException.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        assertThrows(NegocioException.class, () -> customAuthentication.authenticate(authentication));
    }

    @Test
    void test_autenticate_externo() throws JsonProcessingException {

        when(passwordEncoder.matches("A1b2c3s@nt0s", "$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.")).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioExterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);

        var authenticate = customAuthentication.authenticate(authentication);

        assertNotNull(authenticate);
    }

    @Test
    void test_autenticate_externo_error() {

        when(passwordEncoder.matches("A1b2c3s@nt0s", "$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.")).thenReturn(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioExterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(null);
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);

        Exception error = assertThrows(NegocioException.class, () -> {
            customAuthentication.authenticate(authentication);
        });

        assertEquals("Login e/ou senha incorretos! Tente novamente ou clique em “Esqueceu a senha?” para cadastrar uma nova senha.", error.getMessage());
    }

    @Test
    void test_autenticate_externo_error_tentativa() {

        when(passwordEncoder.matches("A1b2c3s@nt0s", "$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.")).thenReturn(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioExterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        ResponseDto<List<RequisicaoLogar>> responseLogins = new ResponseDto<>();
        responseLogins.setData(Arrays.asList(RequisicaoLogarTest.getRequisicaoLogar()));
        ResponseEntity<ResponseDto<List<RequisicaoLogar>>> responseEntityLogins = new ResponseEntity(responseLogins, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);
        when(client.getRequisicoesLogar(any())).thenReturn(responseEntityLogins);

        Exception error = assertThrows(NegocioException.class, () -> {
            customAuthentication.authenticate(authentication);
        });

        assertEquals("Seu usuário foi temporariamente bloqueado. Detectamos muitas tentativas incorretas de acesso ao sistema utilizando seu usuário. Para desbloquear seu acesso, entre em contato com o e-mail cti@pge.ce.gov.br.", error.getMessage());
    }

    @Test
    void test_autenticate_get_requisicoes_login_feign_exception() {

        when(passwordEncoder.matches("A1b2c3s@nt0s", "$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.")).thenReturn(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioExterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);
        when(client.getRequisicoesLogar(any())).thenThrow(FeignException.class);

        assertThrows(NegocioException.class, () -> customAuthentication.authenticate(authentication));
    }

    @Test
    void test_autenticate_situacao_error() {

        when(passwordEncoder.matches("A1b2c3s@nt0s", "$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.")).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioInativo());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);

        Exception error = assertThrows(NegocioException.class, () -> {
            customAuthentication.authenticate(authentication);
        });

        assertEquals("Não foi possível prosseguir com o seu acesso ao sistema. Para mais informações, entre em contato com o e-mail cti@pge.ce.gov.br.", error.getMessage());
    }

    @Test
    void test_autenticate_senha_expirada_error() {

        when(passwordEncoder.matches("A1b2c3s@nt0s", "$2a$10$GwQinyLK/4ZZTrKMg6gDWuzqDKL41pg4jaVEVSrxYizRb8.II6h5.")).thenReturn(true);

        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioSenhaExpirada());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        ResponseDto<List<CondicaoAcesso>> responseCondicaoDto = new ResponseDto<>();
        responseCondicaoDto.setData(Arrays.asList(CondicaoAcessoTest.getCondicaoAcesso()));
        ResponseEntity<ResponseDto<List<CondicaoAcesso>>> responseEntityCondicao = new ResponseEntity(responseCondicaoDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);
        when(condicaoClient.findCondicaoAcesso()).thenReturn(responseEntityCondicao);

        Exception error = assertThrows(NegocioException.class, () -> {
            customAuthentication.authenticate(authentication);
        });

        assertEquals("Sua senha expirou!"+UsuarioTest.getUsuarioSenhaExpirada().getId(), error.getMessage());
    }

    @Test
    void test_usuario_nao_encontrado() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("01234567890", "A1b2c3s@nt0s");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(null);
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);

        Exception error = assertThrows(NegocioException.class, () -> {
            customAuthentication.authenticate(authentication);
        });

        assertEquals("Login e/ou senha incorretos! Tente novamente ou clique em “Esqueceu a senha?” para cadastrar uma nova senha.", error.getMessage());
    }

    @Test
    void test_novo_dispositivo() throws JsonProcessingException {

        LoginDto loginDto = new LoginDto();
        loginDto.setLogin("login");
        loginDto.setSenha("senha");
        loginDto.setNovoDispositivo(true);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("0.0.1.1");

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioExterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);

        customAuthentication.validarDispositivo(loginDto, request);

    }

    @Test
    void test_novo_dispositivo_ja_logado() {

        LoginDto loginDto = new LoginDto();
        loginDto.setLogin("login");
        loginDto.setSenha("senha");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("0.0.1.1");

        IpUsuario ipUsuario = new IpUsuario("teste", "0.0.0.1", "token");

        when(redisService.getUserIp(any())).thenReturn(ipUsuario.toString());

        ResponseDto<Usuario> responseDto = new ResponseDto<>();
        responseDto.setData(UsuarioTest.getUsuarioExterno());
        ResponseEntity<ResponseDto<Usuario>> responseEntity = new ResponseEntity<>(responseDto, HttpStatus.OK);

        when(client.findUser(any())).thenReturn(responseEntity);

        Exception error = assertThrows(NegocioException.class, () -> {
            customAuthentication.validarDispositivo(loginDto, request);
        });

        assertEquals(MessageCommonsContanst.MENSAGEM_JA_LOGADO, error.getMessage());
    }

    @Test
    void test_supports() {
        assertTrue(customAuthentication.supports(UsernamePasswordAuthenticationToken.class));
    }
}
