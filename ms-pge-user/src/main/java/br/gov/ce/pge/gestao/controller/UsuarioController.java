package br.gov.ce.pge.gestao.controller;


import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.UsuarioFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.PortalColaboradorService;
import br.gov.ce.pge.gestao.service.RequisicaoLogarService;
import br.gov.ce.pge.gestao.service.UsuarioConsultaService;
import br.gov.ce.pge.gestao.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

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
	public ResponseEntity<?> save(@Valid @RequestBody UsuarioRequestDto request, HttpServletRequest httpRequest) {
		service.save(request, httpRequest.getHeader("nomeUsuario"));
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO + " de Usuário"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody UsuarioRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException{
		service.update(id, request, httpRequest.getHeader("nomeUsuario"));
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(consultaService.findById(id), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
	}

	@PostMapping("/filtros")
	public ResponseEntity<?> findByFilter(@RequestBody UsuarioFilterRequestDto request, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
																				@RequestParam(value = "ordernacaoPor", required = false, defaultValue = "nome") String orderBy, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		var usuarios = consultaService.findByFilter(request, page, orderBy, size);
		String msg = usuarios.getList().isEmpty() ?  MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO ;
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(usuarios, HttpStatus.OK, msg));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, MessageCommonsContanst.MENSAGEM_DELETE_SUCESSO));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(consultaService.findHistorys(id, page, size), HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_HISTORICO_ENCONTRADO));
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
