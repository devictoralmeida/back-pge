package br.gov.ce.pge.gestao.controller.doc;

import java.util.UUID;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.dto.request.ProdutoServicoUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProdutoServicoDocumentation {
	
	@Operation(summary = "Salvar produto serviço")
	@ApiResponse(responseCode = "201", description = "Produto serviço salvo")
	@ApiResponse(responseCode = "404", description = "Erro ao salvar produto serviço")
	public ResponseEntity<?> save(@Valid @RequestBody ProdutoServicoRequestDto request);

	@Operation(summary = "Atualizar produto serviço", description = "Essa operação não pode ser revertida!")
	@ApiResponse(responseCode = "200", description = "Produto serviço atualizado")
	@ApiResponse(responseCode = "404", description = "Erro ao atualizar produto serviço")
	public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody ProdutoServicoUpdateRequestDto request) throws JsonProcessingException;

	@Operation(summary = "Encontrar produto serviço por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. Quando encontrar o produto servico por id")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id);

	
	@Operation(summary = "Encontrar todos os produtos servicos")
	@ApiResponse(responseCode = "200", description = "Lista com todos os produtos serviços")
	@ApiResponse(responseCode = "500", description = "Erro interno")
	public ResponseEntity<?> findAll();
	
	@Operation(summary = "Encontrar produto serviço por filtro")
	@ApiResponse(responseCode = "200", description = "Produtos serviços filtrados")
	@ApiResponse(responseCode = "500", description = "Erro ao filtrar produtos serviços")
	public ResponseEntity<?> findProdutoServicoByFilter(@Valid @RequestBody ProdutoServicoFilterRequestDto request);

	@Operation(summary = "Visualizar histórico de atualizações dos produtos por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> viewHistoryById(
			@PathVariable(name = "id") UUID id,
			@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page
	) ;

	@Operation(summary = "Deletar produto servico por ID")
	@ApiResponse(responseCode = "200", description = "Sucesso. ao excluir registro produto servico")
	@ApiResponse(responseCode = "404", description = "Erro interno")
	public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id);

}
