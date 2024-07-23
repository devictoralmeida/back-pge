package br.gov.ce.pge.gestao.controller.doc;

import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.FluxoFaseDividaWrapperRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

public interface FaseDividaDocumentation {

    @Operation(summary = "Encontrar todas as fases da dívida")
    @ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registros não encontrados.")
    @ApiResponse(responseCode = "404", description = "Erro interno")
    public ResponseEntity<?> findAll();

    @Operation(summary = "Encontrar fase da dívida por ID")
    @ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
    @ApiResponse(responseCode = "404", description = "Erro interno")
    public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id);

    @Operation(summary = "Encontrar fases da dívida por filtro")
    @ApiResponse(responseCode = "200", description = "Fases da dívida filtradas")
    @ApiResponse(responseCode = "500", description = "Erro ao filtrar fase da dívida")
    public ResponseEntity<?> findFaseDividaByFilter(@Valid @RequestBody FaseDividaFilterRequestDto request);

    @Operation(summary = "Visualizar histórico de atualizações da fase da dívida por ID")
    @ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registro não encontrado.")
    @ApiResponse(responseCode = "404", description = "Erro interno")
    public ResponseEntity<?> viewChangeHistoryById(
            @PathVariable(name = "id") UUID id,
            @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size
    ) ;

    @Operation(summary = "Salvar fase da dívida")
    @ApiResponse(responseCode = "201", description = "Fase da dívida salva")
    @ApiResponse(responseCode = "404", description = "Erro ao salvar fase da dívida")
    public ResponseEntity<?> save(@Valid @RequestBody FaseDividaRequestDto request, HttpServletRequest httpRequest);

    @Operation(summary = "Atualizar fase da dívida", description = "Essa operação não pode ser revertida!")
    @ApiResponse(responseCode = "200", description = "Fase da dívida atualizada")
    @ApiResponse(responseCode = "404", description = "Erro ao atualizar fase da dívida")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody FaseDividaRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException;

    @Operation(summary = "Deletar fase da dívida por ID")
    @ApiResponse(responseCode = "200", description = "Sucesso ao excluir registro fase da dívida")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id, HttpServletRequest httpRequest) ;

    @Operation(summary = "Salvar fluxo de fase da dívida")
    @ApiResponse(responseCode = "201", description = "Fase da dívida salva")
    @ApiResponse(responseCode = "404", description = "Erro ao salvar fase da dívida")
    public ResponseEntity<?> salvarFluxoFases(@RequestBody FluxoFaseDividaWrapperRequestDto request);

    @Operation(summary = "Encontrar todos os fluxos das fases da dívida")
    @ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registros não encontrados.")
    @ApiResponse(responseCode = "404", description = "Erro interno")
    public ResponseEntity<?> obterFluxoFase();

    @Operation(summary = "Encontrar todas as fases da dívida com nome semelhante")
    @ApiResponse(responseCode = "200", description = "Sucesso. Retornará resultado vazio em caso de registros não encontrados.")
    @ApiResponse(responseCode = "404", description = "Erro interno")
    public ResponseEntity<?> findSemelhantes(@RequestParam String nome);
}
