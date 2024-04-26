package br.gov.ce.pge.gestao.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.gov.ce.pge.gestao.dto.response.ResponseClientDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaResponseDto;

@Component
@FeignClient(name = "ms-atributos-gestao-tipo-receita", url = "${ms.service.gestao}" + "/tipo-receita")
public interface TipoReceitaFeingClient {

	@GetMapping("/{id}")
	ResponseEntity<ResponseClientDto<TipoReceitaResponseDto>> findById(@PathVariable(name = "id") UUID id);
}