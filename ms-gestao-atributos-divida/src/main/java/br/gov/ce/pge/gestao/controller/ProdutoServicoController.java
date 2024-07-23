package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.contantes.SharedConstantes;
import br.gov.ce.pge.gestao.controller.doc.ProdutoServicoDocumentation;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;
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
@RequestMapping(value = "produto-servico")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class ProdutoServicoController implements ProdutoServicoDocumentation {

	private final ProdutoServicoService service;

	public ProdutoServicoController(ProdutoServicoService service) {
		this.service = service;
	}

	@Override
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ProdutoServicoRequestDto request, HttpServletRequest httpRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request, httpRequest.getHeader(SharedConstantes.NOME_USUARIO)), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Produto/Serviço."));
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody ProdutoServicoUpdateRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request, httpRequest.getHeader(SharedConstantes.NOME_USUARIO)), HttpStatus.OK, "Produto serviço alterada com sucesso"));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Produto serviço encontrado"));
	}

	@Override
	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Produtos Serviços encontrados"));
	}

	@Override
	@PostMapping("/filtros")
	public ResponseEntity<?> findProdutoServicoByFilter(@Valid @RequestBody ProdutoServicoFilterRequestDto request) {
		List<ProdutoServicoConsultaFilterResponseDto> produtosServicos = service.findProdutoServicosByFilter(request);
		String msg = produtosServicos.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Produtos serviços encontrados com sucesso";
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(produtosServicos, HttpStatus.OK, msg));
	}

	@Override
	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page, size), HttpStatus.OK, "Históricos do produto encontrados"));
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id, HttpServletRequest httpRequest) {
		service.delete(id, httpRequest.getHeader(SharedConstantes.NOME_USUARIO));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Produto serviço deletado com sucesso"));
	}

}
