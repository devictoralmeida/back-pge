package br.gov.ce.pge.gestao.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.CondicaoAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.CondicaoAcessoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "condicao-acesso")
@Qualifier(value = "condicao-acesso")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class CondicaoAcessoController {

    private final CondicaoAcessoService service;
    
    public CondicaoAcessoController(CondicaoAcessoService service) {
    	this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CondicaoAcessoRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request), HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody CondicaoAcessoRequestDto request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO));
    }


}
