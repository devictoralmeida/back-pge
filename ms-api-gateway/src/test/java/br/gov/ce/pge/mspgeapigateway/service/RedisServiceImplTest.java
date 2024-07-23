package br.gov.ce.pge.mspgeapigateway.service;

import br.gov.ce.pge.mspgeapigateway.service.impl.RedisServiceImpl;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisServiceImplTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisServiceImpl redisService;

    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void test_get_user_ip() {
        String nomeUsuario = "anônimo";
        List<Document> cache = List.of(new Document("ip", "127.0.0.1"));

        when(valueOperations.get(nomeUsuario)).thenReturn(cache);

        List<Document> cacheDocument = redisService.getUserIp(nomeUsuario);

        assertNotNull(cacheDocument);
        assertEquals(cache, cacheDocument);
        verify(valueOperations, times(1)).get(nomeUsuario);
    }

    @Test
    public void test_get_user_ip_null() {

        when(valueOperations.get("test")).thenReturn(null);

        List<Document> cacheDocument = redisService.getUserIp("test");

        assertEquals(null, cacheDocument);
        verify(valueOperations, times(1)).get("test");
    }

}
