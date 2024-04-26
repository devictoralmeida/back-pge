package br.gov.ce.pge.gestao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.gov.ce.pge.gestao.dto.response.ColaboradorResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "ms-pge-portal-colaborador", url = "${ms.service.portal}" + "/colaborador")
public interface PortalColaboradorFeingClient {

	@GetMapping("/{cpf}")
	ResponseEntity<ColaboradorResponseDto> findByCpf(@PathVariable(name = "cpf") String cpf);

	@PostMapping("/busca-cpfs")
    ResponseEntity<List<ColaboradorResponseDto>> findByListCpf(@RequestBody List<String> cpfs);
}
