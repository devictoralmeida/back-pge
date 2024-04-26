package br.gov.ce.pge.gestao.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.TermoCondicaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.TermoCondicaoService;

@RestController
@RequestMapping(value = "termos-condicoes")
public class TermoCondicaoController {
	
	private final TermoCondicaoService service;
	
	public TermoCondicaoController(TermoCondicaoService service) {
		this.service = service;
	}
 
	@PostMapping("/{id}")
	public ResponseEntity<?> save(@PathVariable(name = "id") UUID id, @Valid @RequestBody TermoCondicaoRequestDto request) throws JsonProcessingException{
		service.update(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody TermoCondicaoRequestDto request) throws JsonProcessingException{
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
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO));
	}

}
