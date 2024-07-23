package br.gov.ce.pge.mspgeapigateway.service;

import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisService {

    List<Document> getUserIp(String nomeUsuario);

}
