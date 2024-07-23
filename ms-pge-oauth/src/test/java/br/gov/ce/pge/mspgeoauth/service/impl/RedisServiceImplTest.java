package br.gov.ce.pge.mspgeoauth.service.impl;

import br.gov.ce.pge.mspgeoauth.entity.IpUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RedisServiceImplTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RedisServiceImpl redisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void test_inserir_user_ip() throws JsonProcessingException {
        IpUsuario ipUsuario = new IpUsuario();
        ipUsuario.setNomeUsuario("user123");
        ipUsuario.setIp("192.168.1.1");

        redisService.inserirUserIp(ipUsuario);

        verify(valueOperations, times(1)).set(eq("user123"), eq(ipUsuario.toString()));
    }

    @Test
    void test_get_user_ip() {
        String idUsuario = "user123";
        String ip = "192.168.1.1";

        when(valueOperations.get(idUsuario)).thenReturn(ip);

        Object result = redisService.getUserIp(idUsuario);

        verify(valueOperations, times(1)).get(idUsuario);
        assertEquals(ip, result);
    }

    @Test
    void test_remover_user_ip() throws JsonProcessingException {;

        IpUsuario ipUsuario = new IpUsuario("test", "192.168.1.1", "token");

        when(redisTemplate.hasKey("test")).thenReturn(true);
        when(valueOperations.get("test")).thenReturn(ipUsuario.toString());

        redisService.removerUserIp("test", "192.168.1.1");

        verify(redisTemplate, times(1)).hasKey("test");
        verify(valueOperations, times(1)).get("test");
        verify(redisTemplate, times(1)).delete("test");
    }
}
