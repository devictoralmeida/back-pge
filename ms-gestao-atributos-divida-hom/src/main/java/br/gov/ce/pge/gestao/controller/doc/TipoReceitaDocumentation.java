package br.gov.ce.pge.gestao.controller.doc;

import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.gov.ce.pge.gestao.dto.request.TipoReceitaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.dto.request.TipoReceitaUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface TipoReceitaDocumentation {
	
	@Operation(summary = "Salvar tipo receita")
	@ApiResponse(responseCode = "201", description = "Tipo receita salva")
	@ApiResponse(responseCode = "404", description = "Erro ao salvar tipo receita")
	public ResponseEntity<?> save(@Valid @RequestBody TipoReceitaRequestDto request);

	@Operation(summary = "Atualizar tipo receita", description = "Essa operação não pode ser revertida!")
	@ApiResponse(responseCode = "200", description = "Tipo receita atualizada")
	@ApiResponse(responseCode = "404", description = "Erro ao atualizar tipo receita")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody TipoReceitaUpdateRequestDto request) throws JsonProcessingException;

	@Operation(summary = "Encontrar tipo receita por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id);

	
	@Operation(summary = "Encontrar todos os tipos Receita")
	@ApiResponse(responseCode = "200", description = "Lista com todos os tipos Receitas")
	@ApiResponse(responseCode = "500", description = "Erro interno")
	public ResponseEntity<?> findAll();
	
	@Operation(summary = "Encontrar tipo receita por filtro")
	@ApiResponse(responseCode = "200", description = "Tipos Receitas filtradas")
	@ApiResponse(responseCode = "500", description = "Erro ao filtrar tipos receitas")
	public ResponseEntity<?> findTipoReceitaByFilter(@Valid @RequestBody TipoReceitaFilterRequestDto request);

	@Operation(summary = "Visualizar histórico de atualizações do tipo receita por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> viewHistoryById(
			@PathVariable(name = "id") UUID id,
			@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page
	) ;

	@Operation(summary = "deletar tipo receita por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. ao excluir registro tipo receita")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id);

}
