package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.TermoCondicaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "termos-condicoes")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class TermoCondicaoController {

	private final TermoCondicaoService service;

	public TermoCondicaoController(TermoCondicaoService service) {
		this.service = service;
	}

	@PostMapping("/{id}")
	public ResponseEntity<?> save(@PathVariable(name = "id") UUID id, @Valid @RequestBody TermoCondicaoRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
		service.update(id, request, httpRequest.getHeader("nomeUsuario"));
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody TermoCondicaoRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
		service.updateExistente(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@GetMapping("/")
	public ResponseEntity<?> findBySistema() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findBySistema(), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page, size), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO));
	}

}
