package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.PerfilAcessoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "perfil-acesso")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class PerfilAcessoController  {

	private final PerfilAcessoService service;

	public PerfilAcessoController(PerfilAcessoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody PerfilAcessoRequestDto request, HttpServletRequest httpRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request, httpRequest.getHeader("nomeUsuario")), HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO + " de Perfil de Acesso"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody PerfilAcessoRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException{
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request, httpRequest.getHeader("nomeUsuario")), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO));
	}

	@PostMapping("/filtros")
	public ResponseEntity<?> findByFilter(@RequestBody PerfilAcessoFilterRequestDto request, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
																				@RequestParam(value = "ordernacaoPor", required = false, defaultValue = "nome") String orderBy, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		var perfis = service.findByFilter(request, page, orderBy, size);
		String msg = perfis.getList().isEmpty() ?  MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO ;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(perfis, HttpStatus.OK, msg));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page, size), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO));
	}
}
