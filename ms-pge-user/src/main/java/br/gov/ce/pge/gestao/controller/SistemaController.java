package br.gov.ce.pge.gestao.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.SistemaRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.entity.Sistema;
import br.gov.ce.pge.gestao.service.SistemaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "sistema")
@Qualifier(value = "sistema")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class SistemaController extends ControllerCommons<SistemaRequestDto, Sistema> {
	
	@Qualifier("sistemaServiceImpl")
	private SistemaService service;
	
	public SistemaController(@Qualifier("sistemaServiceImpl") SistemaService service) {
		super(service);
	   this.service = service;
	}
	
	@GetMapping("/lista-permissao")
	public ResponseEntity<?> findAllPermissoesBySistema() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAllPermissoesBySistema(), HttpStatus.OK, "Sistemas encontrados"));
	}
	
	@GetMapping("/")
	@Override
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAllOrdenados(), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO));
	}
}
