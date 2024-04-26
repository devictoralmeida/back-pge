package br.gov.ce.pge.gestao.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.InscricaoService;

@RestController
@RequestMapping(value = "inscricao")
public class InscricaoController {
	
	@Autowired
	private InscricaoService service;
	
	@PostMapping(value = "/")
	public ResponseEntity<ResponseDto<?>> save(@RequestBody @Valid InscricaoRequestDto request) {
		service.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, 
				HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseDto<?>> findById(@PathVariable(value = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), 
				HttpStatus.OK, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ResponseDto<?>> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid InscricaoRequestDto request) {
		service.update(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, 
				HttpStatus.OK, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO));
	}
}
