package br.gov.ce.pge.mspgeoauth.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.CondicaoAcesso;

@Component
@FeignClient(name = "ms-pge-user", url = "${ms.service.user}" + "/condicao-acesso")
public interface CondicaoAcessoFeignClient {

    @GetMapping("/")
    ResponseEntity<ResponseDto<List<CondicaoAcesso>>> findCondicaoAcesso();

}
