package br.gov.ce.pge.gestao.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.controller.doc.OrigemDebitoDocumentation;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;

@RestController
@RequestMapping(value = "origem-debito")
public class OrigemDebitoController implements OrigemDebitoDocumentation {
	
	private final OrigemDebitoService service;
	
	public OrigemDebitoController(OrigemDebitoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody OrigemDebitoRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Origens do Débito."));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody OrigemDebitoRequestDto request) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request), HttpStatus.OK, "Origem débito alterada com sucesso"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Origem débito encontrada"));
	}

	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Origem débito encontrada"));
	}

	@PostMapping("/filtros")
	public ResponseEntity<?> findOrigemDebitoByFilter(@RequestBody OrigemDebitoFilterRequestDto request) {
		List<OrigemDebitoConsultaResponseDto> origens = service.findOrigemDebitoByFilter(request);
		String msg = origens.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Origem débito encontrada com sucesso";
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(origens, HttpStatus.OK, msg));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id,@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page), HttpStatus.OK, "Históricos do débito encontrados"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Origem Débito deletado com sucesso"));
	}
}
