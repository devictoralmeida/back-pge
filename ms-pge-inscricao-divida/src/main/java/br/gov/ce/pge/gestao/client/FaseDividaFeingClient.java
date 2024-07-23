package br.gov.ce.pge.gestao.client;

import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Component
@FeignClient(name = "ms-atributos-gestao-fase-divida", url = "${ms.service.gestao}" + "/fase-divida")
public interface FaseDividaFeingClient {

	@GetMapping("/{id}")
	ResponseEntity<ResponseClientDto<FaseDividaResponseDto>> findById(@PathVariable(name = "id") UUID id);

	@GetMapping("/")
	ResponseEntity<ResponseClientDto<List<FaseDividaResponseDto>>> findAll();
}
