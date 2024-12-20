package br.gov.ce.pge.gestao.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.gestao.dto.request.ModuloRequestDto;
import br.gov.ce.pge.gestao.entity.Modulo;
import br.gov.ce.pge.gestao.service.ModuloService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "modulo")
@Qualifier(value = "modulo")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class ModuloController extends ControllerCommons<ModuloRequestDto, Modulo>  {
	
	@Qualifier("moduloServiceImpl")
	private ModuloService service;
	
	
	public ModuloController(@Qualifier("moduloServiceImpl") ModuloService service) {
		super(service);
		this.service = service;
	}

}
