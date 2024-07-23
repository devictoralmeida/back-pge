package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.contantes.SharedConstantes;
import br.gov.ce.pge.gestao.controller.doc.TipoReceitaDocumentation;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.service.TipoReceitaService;
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
@RequestMapping(value = "tipo-receita")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class TipoReceitaController implements TipoReceitaDocumentation {

	private final TipoReceitaService service;

	public TipoReceitaController(TipoReceitaService service) {
		this.service = service;
	}

	@Override
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody TipoReceitaRequestDto request, HttpServletRequest httpRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request, httpRequest.getHeader(SharedConstantes.NOME_USUARIO)), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Tipos de Receita."));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody TipoReceitaUpdateRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request, httpRequest.getHeader(SharedConstantes.NOME_USUARIO)), HttpStatus.OK, "Tipo Receita alterada com sucesso"));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Tipo Receita encontrada"));
	}

	@Override
	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Tipos Receitas encontradas"));
	}

	@Override
	@PostMapping("/filtros")
	public ResponseEntity<?> findTipoReceitaByFilter(@Valid @RequestBody TipoReceitaFilterRequestDto request) {
		List<TipoReceitaConsultaFilterResponseDto> tipoReceitas = service.findTipoReceitasByFilter(request);
		String msg  = tipoReceitas.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Tipo Receita encontrado com sucesso";
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(tipoReceitas, HttpStatus.OK, msg));
	}

	@Override
	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page, size), HttpStatus.OK, "Históricos do tipo receita encontrados"));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id, HttpServletRequest httpRequest) {
		service.delete(id, httpRequest.getHeader(SharedConstantes.NOME_USUARIO));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Tipo Receita deletada com sucesso"));
	}

}
