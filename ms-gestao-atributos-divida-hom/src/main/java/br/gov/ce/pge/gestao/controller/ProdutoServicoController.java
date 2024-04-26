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

import br.gov.ce.pge.gestao.controller.doc.ProdutoServicoDocumentation;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.ProdutoServicoConsultaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.ProdutoServicoService;

@RestController
@RequestMapping(value = "produto-servico")
public class ProdutoServicoController implements ProdutoServicoDocumentation {
	
	private final ProdutoServicoService service;
	
	public ProdutoServicoController(ProdutoServicoService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ProdutoServicoRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Produto/Serviço."));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody ProdutoServicoUpdateRequestDto request) throws JsonProcessingException {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request), HttpStatus.OK, "Produto serviço alterada com sucesso"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Produto serviço encontrado"));
	}

	@GetMapping("/")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Produtos Serviços encontrados"));
	}
	
	@PostMapping("/filtros")
	public ResponseEntity<?> findProdutoServicoByFilter(@Valid @RequestBody ProdutoServicoFilterRequestDto request) {
		List<ProdutoServicoConsultaFilterResponseDto> produtosServicos = service.findProdutoServicosByFilter(request);
		String msg = produtosServicos.isEmpty()? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Produtos serviços encontrados com sucesso"; 
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(produtosServicos, HttpStatus.OK, msg));
	}

	@GetMapping("/historico/{id}")
	public ResponseEntity<?> viewHistoryById(@PathVariable(name = "id") UUID id,@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
		return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findHistorys(id, page), HttpStatus.OK, "Históricos do produto encontrados"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Produto serviço deletado com sucesso"));
	}

}
