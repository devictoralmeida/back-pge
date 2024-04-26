package br.gov.ce.pge.gestao.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.gov.ce.pge.gestao.dto.response.OrigemDebitoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;

@Component
@FeignClient(name = "ms-atributos-gestao-origem", url = "${ms.service.gestao}" + "/origem-debito")
public interface OrigemDebitoFeingClient {

	@GetMapping("/{id}")
	ResponseEntity<ResponseClientDto<OrigemDebitoResponseDto>> findById(@PathVariable(name = "id") UUID id);
}
