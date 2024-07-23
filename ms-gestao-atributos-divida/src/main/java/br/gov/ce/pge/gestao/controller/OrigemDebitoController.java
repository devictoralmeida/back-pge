package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.contantes.SharedConstantes;
import br.gov.ce.pge.gestao.controller.doc.OrigemDebitoDocumentation;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import br.gov.ce.pge.gestao.dto.response.OrigemDebitoConsultaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.OrigemDebitoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "origem-debito")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class OrigemDebitoController implements OrigemDebitoDocumentation {

	private final OrigemDebitoService service;

	public OrigemDebitoController(OrigemDebitoService service) {
		this.service = service;
	}

	@Override
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody OrigemDebitoRequestDto request, HttpServletRequest httpRequest) {
		String nomeUsuario = httpRequest.getHeader(SharedConstantes.NOME_USUARIO);
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request, nomeUsuario), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Origens do Débito."));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody OrigemDebitoRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
		String nomeUsuario = httpRequest.getHeader(SharedConstantes.NOME_USUARIO);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request, nomeUsuario), HttpStatus.OK, "Origem débito alterada com sucesso"));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Origem débito encontrada"));
	}

	@Override
	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Origem débito encontrada"));
	}

	@Override
	@PostMapping("/filtros")
	public ResponseEntity<?> findOrigemDebitoByFilter(@RequestBody OrigemDebitoFilterRequestDto request) {
		List<OrigemDebitoConsultaResponseDto> origens = service.findOrigemDebitoByFilter(request);
		String msg = origens.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Origem débito encontrada com sucesso";
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(origens, HttpStatus.OK, msg));
	}

	@Override
  @GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page, size), HttpStatus.OK, "Históricos do débito encontrados"));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id, HttpServletRequest httpRequest) {
		service.delete(id, httpRequest.getHeader(SharedConstantes.NOME_USUARIO));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Origem Débito deletado com sucesso"));
	}
}
