package br.gov.ce.pge.mspgeoauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.mspgeoauth.dto.LoginDto;
import br.gov.ce.pge.mspgeoauth.dto.ResponseDto;
import br.gov.ce.pge.mspgeoauth.service.LoginService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

	private final LoginService service;
	
	public LoginController(LoginService service) {
		this.service = service;
	}

	@PostMapping(value = "/portal-divida")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.login(loginDto.getLogin(), loginDto.getSenha()), HttpStatus.OK.value(), "sucesso"));
	}

}
