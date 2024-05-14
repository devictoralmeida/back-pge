package br.gov.ce.pge.mspgeportalcolaborador.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.mspgeportalcolaborador.dto.ColaboradorNotFoundResponse;
import br.gov.ce.pge.mspgeportalcolaborador.service.ColaboradorService;
import br.gov.ce.pge.mspgeportalcolaborador.shared.exception.ColaboradorNotFoundException;

@RestController
@RequestMapping(value = "colaborador")
public class ColaboradorController {

	private final ColaboradorService service;

	public ColaboradorController(ColaboradorService service) {
		this.service = service;
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<?> findByCpf(@PathVariable(name = "cpf") String cpf) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findByCpf(cpf));
		} catch (ColaboradorNotFoundException exception) {
			return responseError(exception);
		}
	}

	@PostMapping("/busca-cpfs")
	public ResponseEntity<?> findByListCpf(@RequestBody List<String> cpfs) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findByListCpfs(cpfs));
		} catch (ColaboradorNotFoundException exception) {
			return responseError(exception);
		}
	}

	private ResponseEntity<?> responseError(ColaboradorNotFoundException exception) {
		var response = new ColaboradorNotFoundResponse();
		response.setMessage(exception.getMessage());
		response.setSuccess(false);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
