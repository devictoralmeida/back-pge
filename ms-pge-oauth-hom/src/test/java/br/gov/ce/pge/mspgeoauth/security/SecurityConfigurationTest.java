package br.gov.ce.pge.mspgeoauth.security;

import br.gov.ce.pge.mspgeoauth.service.CustomAuthenticationProviderService;
import br.gov.ce.pge.mspgeoauth.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.test.context.ContextConfiguration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = SecurityConfiguration.class)
public class SecurityConfigurationTest {

    @MockBean
    private SecurityFilter securityFilter;

    @MockBean
    private CustomAuthenticationProviderService customAuthenticationProvider;

    @MockBean
    private AuthenticationConfiguration authenticationConfiguration;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;
    
    @InjectMocks
    private SecurityConfiguration securityConfiguration;

    @Test
    void test_authentication_manager() throws Exception {
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
        securityConfiguration.authenticationManager(authenticationConfiguration);
        verify(authenticationConfiguration, atLeast(1)).getAuthenticationManager();
    }

    @Test
    void test_class() {
        TokenService tokenService = mock(TokenService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);
        SecurityFilter securityFilter = new SecurityFilter(tokenService, usuarioService);
        assertNotNull(securityFilter);
    }

    @Test
    public void test_doFilter_internal() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        TokenService tokenService = mock(TokenService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(tokenService.validadeToken("token")).thenReturn("cpf");
        when(usuarioService.loadUserByUsername("cpf")).thenReturn(User.withUsername("cpf").password("password").roles("USER").build());

        SecurityFilter filter = new SecurityFilter(tokenService, usuarioService);

        filter.doFilterInternal(request, response, filterChain);

        verify(usuarioService).loadUserByUsername("cpf");
        UserDetails userDetails = User.withUsername("cpf").password("password").roles("USER").build();
        UsernamePasswordAuthenticationToken expectedAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        expectedAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void test__doFilter_internal_header_null() throws ServletException, IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        TokenService tokenService = mock(TokenService.class);
        UsuarioService usuarioService = mock(UsuarioService.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        SecurityFilter filter = new SecurityFilter(tokenService, usuarioService);

        filter.doFilterInternal(request, response, filterChain);

        UserDetails userDetails = User.withUsername("cpf").password("password").roles("USER").build();
        UsernamePasswordAuthenticationToken expectedAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        expectedAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void test_pass_encoder() {
        PasswordEncoder passwordEncoder = securityConfiguration.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

}
