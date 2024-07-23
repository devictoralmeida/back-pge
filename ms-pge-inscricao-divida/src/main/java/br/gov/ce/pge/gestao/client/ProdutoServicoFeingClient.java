package br.gov.ce.pge.gestao.client;

import br.gov.ce.pge.gestao.dto.response.ProdutoServicoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Component
@FeignClient(name = "ms-atributos-gestao-produto-servico", url = "${ms.service.gestao}" + "/produto-servico")
public interface ProdutoServicoFeingClient {

    @GetMapping("/{id}")
    ResponseEntity<ResponseClientDto<ProdutoServicoResponseDto>> findById(@PathVariable(name = "id") UUID id);
}
