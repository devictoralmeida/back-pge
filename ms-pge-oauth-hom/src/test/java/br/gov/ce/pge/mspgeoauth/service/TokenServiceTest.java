package br.gov.ce.pge.mspgeoauth.service;

import br.gov.ce.pge.mspgeoauth.entity.UsuarioTest;
import br.gov.ce.pge.mspgeoauth.security.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenService service;

    @Mock
    private Algorithm algorithmMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "secret");
    }

    @Test
    void test_generate_token() {

        String token = "generatedToken";

        when(algorithmMock.sign(any())).thenReturn(token.getBytes(StandardCharsets.UTF_8));
        String generatedToken = tokenService.generateToken(UsuarioTest.getUsuarioExterno());
        assertNotNull(generatedToken);

        DecodedJWT decodedJWT = JWT.decode(generatedToken);
        assertNotNull(decodedJWT);
        assertEquals("ms-pge-oauth", decodedJWT.getIssuer());
        assertEquals(UsuarioTest.getUsuarioExterno().getId(), UUID.fromString(decodedJWT.getSubject()));
    }


    @Test
    void test_validade_token() {

        String token = tokenService.validadeToken("token");
        assertNotNull(token);
    }
}
