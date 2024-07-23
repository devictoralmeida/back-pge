package br.gov.ce.pge.mspgeoauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.service.LoginService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	private final LoginService service;

	@Autowired
	public LoginController(LoginService service) {
		this.service = service;
	}

	@PatchMapping(value = "/remocao-sessao/{nomeUsuario}")
	public ResponseEntity<?> removerSessao(@PathVariable(name = "nomeUsuario") String nomeUsuario, HttpServletRequest request) throws JsonProcessingException {
		service.removerSessao(nomeUsuario, request.getRemoteAddr());
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK.value(), "Sucesso"));
	}

	@PostMapping(value = "/portal-divida")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletRequest request) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.login(loginDto, request), HttpStatus.OK.value(), "sucesso"));
	}

}

