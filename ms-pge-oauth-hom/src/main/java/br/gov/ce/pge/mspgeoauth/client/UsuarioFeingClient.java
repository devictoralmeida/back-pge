package br.gov.ce.pge.mspgeoauth.client;

import br.gov.ce.pge.mspgeoauth.entity.RequisicaoLogar;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.entity.Usuario;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Component
@FeignClient(name = "api-usuario", url = "${ms.service.user}" + "/usuario")
public interface UsuarioFeingClient {

	@GetMapping("/cpf/{cpf}")
	ResponseEntity<ResponseDto<Usuario>> findByCpf(@PathVariable(name = "cpf") String cpf);

	@GetMapping("/buscar/{identificador}")
	ResponseEntity<ResponseDto<Usuario>> findUser(@PathVariable(name = "identificador") String identificador);

	@GetMapping("/{id}/requisicao-logar/")
	void requisicaoLogin(@PathVariable(name = "id") UUID id, @RequestParam("sucesso") boolean sucesso);

	@GetMapping("/{id}/requisicoes-login")
	ResponseEntity<ResponseDto<List<RequisicaoLogar>>> getRequisicoesLogar(@PathVariable(name = "id") UUID userId);

	@GetMapping("/{id}/bloqueio")
	void bloquearUsuario(@PathVariable(name = "id") UUID id);

	@GetMapping("/{id}/ultimo-acesso")
    void setUltimoLogin(@PathVariable(name = "id") UUID id);
}
