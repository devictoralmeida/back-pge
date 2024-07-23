package br.gov.ce.pge.mspgeapigateway.service.impl;

import br.gov.ce.pge.mspgeapigateway.service.RedisService;
import org.bson.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;

    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Document> getUserIp(String nomeUsuario) {
        Object cache = redisTemplate.opsForValue().get(nomeUsuario);
        return cache != null ? (List<Document>) cache : null;
    }
}
