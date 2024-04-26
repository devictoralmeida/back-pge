package br.gov.ce.pge.gestao.controller.doc;

import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.gov.ce.pge.gestao.dto.request.OrigemDebitoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.OrigemDebitoRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface OrigemDebitoDocumentation {
	
	@Operation(summary = "Salvar origem do débito")
	@ApiResponse(responseCode = "201", description = "Origem do débito salva")
	@ApiResponse(responseCode = "404", description = "Erro ao salvar origem do débito")
	public ResponseEntity<?> save(@Valid @RequestBody OrigemDebitoRequestDto request);

	@Operation(summary = "Atualizar origem do débito", description = "Essa operação não pode ser revertida!")
	@ApiResponse(responseCode = "200", description = "Origem do débito atualizada")
	@ApiResponse(responseCode = "404", description = "Erro ao atualizar origem do débito")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody OrigemDebitoRequestDto request) throws JsonProcessingException;

	@Operation(summary = "Encontrar origem do débito por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id);

	@Operation(summary = "Encontrar todas as origens de débitos")
	@ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registros não encontrados.")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> findAll();

	@Operation(summary = "Encontrar origem de débito por filtro")
	@ApiResponse(responseCode = "200", description = "Origens dos débitos filtradas")
	@ApiResponse(responseCode = "404", description = "Erro ao filtrar origens dos débitos")
	public ResponseEntity<?> findOrigemDebitoByFilter(@RequestBody OrigemDebitoFilterRequestDto request);

	@Operation(summary = "Visualizar histórico de atualizações das origens dos débitos por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> viewHistoryById(
			@PathVariable(name = "id") UUID id,
			@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page
	) ;
	
	@Operation(summary = "deletar debito origem por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. ao excluir registro debito origem")
	@ApiResponse(responseCode = "500", description = "Erro interno")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) ;

}
