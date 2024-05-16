package br.gov.ce.pge.gestao.controller;


import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.PortalColaboradorService;
import br.gov.ce.pge.gestao.service.RequisicaoLogarService;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;
import br.gov.ce.pge.gestao.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "usuario")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class UsuarioController  {

	private final UsuarioService service;

	private final UsuarioConsultaService consultaService;

	private final RequisicaoLogarService logarService;

	private final PortalColaboradorService portalService;
	
	public UsuarioController(UsuarioService service, UsuarioConsultaService consultaService,
			RequisicaoLogarService logarService, PortalColaboradorService portalService) {
		this.service = service;
		this.consultaService = consultaService;
		this.logarService = logarService;
		this.portalService = portalService;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody UsuarioRequestDto request) {
		service.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO + " de Usuário"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody UsuarioRequestDto request) throws JsonProcessingException{
		service.update(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(consultaService.findById(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@PostMapping("/filtros")
	public ResponseEntity<?> findByFilter(@RequestBody UsuarioFilterRequestDto request, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
										  @RequestParam(value = "ordernacaoPor", required = false, defaultValue = "nome") String orderBy) {
		var usuarios = consultaService.findByFilter(request, page, orderBy);
		String msg = usuarios.getList().isEmpty() ?  MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO ;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(usuarios, HttpStatus.OK, msg));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(consultaService.findHistorys(id, page), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO));
	}

	@GetMapping("/buscar/{identificador}")
	public ResponseEntity<?> findByIdentificador(@PathVariable(name = "identificador") String identificador) {
		var usuario = consultaService.findByIdentificador(identificador);
		String msg = usuario == null ?  MessageCommonsContanst.MENSAGEM_USUARIO_NAO_ENCONTRADO : MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO ;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(usuario, HttpStatus.OK, msg));
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<?> findByCpf(@PathVariable(name = "cpf") String cpf) {
		var usuario = consultaService.findByCpf(cpf);
		String msg = usuario == null ?  MessageCommonsContanst.MENSAGEM_USUARIO_NAO_ENCONTRADO : MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO ;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(usuario, HttpStatus.OK, msg));
	}

	@GetMapping("/interno/{cpf}")
	public ResponseEntity<?> findUsuarioInternoByCpf(@PathVariable(name = "cpf") String cpf) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(portalService.getColaborador(cpf), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_CPF_SUCESSO));
	}

	@GetMapping("/{id}/requisicao-logar/")
	public ResponseEntity<?> requisicaoLogar(@PathVariable(name = "id") UUID id, @RequestParam("sucesso") boolean sucesso) {
		logarService.save(id, sucesso);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}/requisicoes-login")
	public ResponseEntity<?> requisicoesLogar(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(logarService.getRequestByUserId(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@GetMapping("/{id}/bloqueio")
	public ResponseEntity<?> bloquearUsuario(@PathVariable(name = "id") UUID id) {
		service.bloquearUsuario(id);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@PatchMapping("/{id}/aceite-termo/{nomeSistema}")
	public ResponseEntity<?> aceitarTermo(@PathVariable(name = "id") UUID id, @PathVariable(name = "nomeSistema") String nomeSistema) {
		service.aceitarTermo(id, nomeSistema);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}/ultimo-acesso")
	public ResponseEntity<?> ultimoAcesso(@PathVariable(name = "id") UUID id) {
		service.ultimoAcesso(id);
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}
}
