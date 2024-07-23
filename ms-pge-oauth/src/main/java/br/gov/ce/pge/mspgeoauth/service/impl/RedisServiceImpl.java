package br.gov.ce.pge.mspgeoauth.service.impl;

import br.gov.ce.pge.mspgeoauth.entity.IpUsuario;
import br.gov.ce.pge.mspgeoauth.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;

    public RedisServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void inserirUserIp(IpUsuario ipUsuario) {
        redisTemplate.opsForValue().set(ipUsuario.getNomeUsuario(), ipUsuario.toString());
    }

    @Override
    public String getUserIp(String nomeUsuario) {
        Object cache = redisTemplate.opsForValue().get(nomeUsuario);
        return cache != null ? cache.toString() : "";
    }

    @Override
    public void removerUserIp(String nomeUsuario, String ip) throws JsonProcessingException {

        if(Boolean.TRUE.equals(redisTemplate.hasKey(nomeUsuario))) {
            var values = redisTemplate.opsForValue();
            var ipUsuario = values.get(nomeUsuario).toString();

            ObjectMapper objectMapper = new ObjectMapper();
            IpUsuario userIp = objectMapper.readValue(ipUsuario, IpUsuario.class);

            if(ipUsuario != null && userIp.getIp().equals(ip)) {
                redisTemplate.delete(nomeUsuario);
            }
        }
    }
}
