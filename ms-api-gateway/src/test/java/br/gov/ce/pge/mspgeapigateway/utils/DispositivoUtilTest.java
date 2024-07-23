package br.gov.ce.pge.mspgeapigateway.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DispositivoUtilTest {

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private HttpHeaders httpHeaders;

    @Test
    void test_dispositivo_util() {

        String detalhesDispositivo = DispositivoUtil.getDetalhesDispositivo("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");

        asserts(detalhesDispositivo);
    }

    @Test
    void test_dispositivo_util_outro_user_agent() {

        String detalhesDispositivo = DispositivoUtil.getDetalhesDispositivo("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:126.0) Gecko/20100101 Firefox/126.0");

        asserts(detalhesDispositivo);
    }
    
    @Test
    void test_user_agent_vazio() {
        String detalhesDispositivo = DispositivoUtil.getDetalhesDispositivo("");
        
        assertDetalhesVazio(detalhesDispositivo);
    }

    private void assertDetalhesVazio(String detalhesDispositivo) {
        assertEquals("", detalhesDispositivo);
    }

    static void asserts(String detalhes) {

        assertNotNull(detalhes);
        assertEquals("X11 Linux x86_64", detalhes);

    }

    @Test
    public void test_extrair_com_x_forwarded_for() {
        String xForwardedFor = "203.0.113.195";
        when(serverWebExchange.getRequest()).thenReturn(serverHttpRequest);
        when(serverHttpRequest.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getFirst("x-forwarded-for")).thenReturn(xForwardedFor);

        String ip = DispositivoUtil.extrairIp(serverWebExchange);

        assertEquals("203.0.113.195", ip);
    }

    @Test
    public void test_extrair_ip_endereco_remoto() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("203.0.113.195");
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 0);

        when(serverWebExchange.getRequest()).thenReturn(serverHttpRequest);
        when(serverHttpRequest.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getFirst("x-forwarded-for")).thenReturn(null);
        when(serverHttpRequest.getRemoteAddress()).thenReturn(inetSocketAddress);

        String ip = DispositivoUtil.extrairIp(serverWebExchange);

        assertEquals("203.0.113.195", ip);
    }

    @Test
    public void test_extrair_ip_endereco_remoto_vazio() {

        when(serverWebExchange.getRequest()).thenReturn(serverHttpRequest);
        when(serverHttpRequest.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getFirst("x-forwarded-for")).thenReturn(null);
        when(serverHttpRequest.getRemoteAddress()).thenReturn(null);

        String ip = DispositivoUtil.extrairIp(serverWebExchange);

        assertEquals("", ip);
    }

}
