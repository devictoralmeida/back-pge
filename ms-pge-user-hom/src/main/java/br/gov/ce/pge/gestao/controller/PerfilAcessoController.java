package br.gov.ce.pge.gestao.controller;

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

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.PerfilAcessoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.PerfilAcessoService;

@RestController
@RequestMapping(value = "perfil-acesso")
public class PerfilAcessoController  {
	
	private final PerfilAcessoService service;
	
	public PerfilAcessoController(PerfilAcessoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody PerfilAcessoRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request), HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO + " de Perfil de Acesso"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody PerfilAcessoRequestDto request) throws JsonProcessingException{
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO));
	}

	@PostMapping("/filtros")
	public ResponseEntity<?> findByFilter(@RequestBody PerfilAcessoFilterRequestDto request, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "ordernacaoPor", required = false, defaultValue = "nome") String orderBy) {
		var perfis = service.findByFilter(request, page, orderBy);
		String msg = perfis.getList().isEmpty() ?  MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO ;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(perfis, HttpStatus.OK, msg));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO));
	}
}
