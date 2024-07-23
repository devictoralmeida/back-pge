package br.gov.ce.pge.gestao.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.gestao.constants.MessagesSucessor;
import br.gov.ce.pge.gestao.dto.request.SucessorRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.impl.SucessorServiceImpl;

@RestController
@RequestMapping(value = "sucessor")
public class SucessorController {
	
	private final SucessorServiceImpl service;
	
	public SucessorController(SucessorServiceImpl service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody SucessorRequestDto request) {
		service.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessagesSucessor.MENSAGEM_SAVE_SUCESSO));
	}
}
