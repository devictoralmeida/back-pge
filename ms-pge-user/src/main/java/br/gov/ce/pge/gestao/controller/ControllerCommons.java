package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.ServiceCommons;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Component
public class ControllerCommons<R, M>  {

	private final ServiceCommons<R, M> serviceCommons;

	public ControllerCommons(ServiceCommons<R, M> service) {
		this.serviceCommons = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody R request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(serviceCommons.save(request), HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody R request) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(serviceCommons.update(id, request), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(serviceCommons.findById(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(serviceCommons.findAll(), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_TODOS_SUCESSO));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		var historico = serviceCommons.findHistorys(id, page, size);
		String msg = historico.getList().isEmpty() ? MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_VAZIA : MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(historico, HttpStatus.OK, msg));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		serviceCommons.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO));
	}
}
