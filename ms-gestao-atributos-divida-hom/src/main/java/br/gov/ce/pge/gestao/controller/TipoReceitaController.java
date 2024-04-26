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

import br.gov.ce.pge.gestao.controller.doc.TipoReceitaDocumentation;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.dto.response.TipoReceitaConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.service.TipoReceitaService;

@RestController
@RequestMapping(value = "tipo-receita")
public class TipoReceitaController implements TipoReceitaDocumentation {
	
	private final TipoReceitaService service;

	public TipoReceitaController(TipoReceitaService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody TipoReceitaRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Tipos de Receita."));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody TipoReceitaUpdateRequestDto request) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request), HttpStatus.OK, "Tipo Receita alterada com sucesso"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Tipo Receita encontrada"));
	}
	
	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Tipos Receitas encontradas"));
	}
	
	@PostMapping("/filtros")
	public ResponseEntity<?> findTipoReceitaByFilter(@Valid @RequestBody TipoReceitaFilterRequestDto request) {
		List<TipoReceitaConsultaFilterResponseDto> tipoReceitas = service.findTipoReceitasByFilter(request);
		String msg  = tipoReceitas.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Tipo Receita encontrado com sucesso";
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(tipoReceitas, HttpStatus.OK, msg));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id,@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page), HttpStatus.OK, "Históricos do tipo receita encontrados"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Tipo Receita deletada com sucesso"));
	}

}
