package br.gov.ce.pge.mspgeoauth.service;

import br.gov.ce.pge.mspgeoauth.entity.IpUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface RedisService {

    void inserirUserIp(IpUsuario ipUsuario) throws JsonProcessingException;

    String getUserIp(String idUsuario);

    void removerUserIp(String nomeUsuario, String ip) throws JsonProcessingException;
}
