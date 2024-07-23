package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.contantes.SharedConstantes;
import br.gov.ce.pge.gestao.controller.doc.FaseDividaDocumentation;
import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.FluxoFaseDividaWrapperRequestDto;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.FaseDividaService;
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
@RequestMapping(value = "fase-divida")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class FaseDividaController implements FaseDividaDocumentation {

    private final FaseDividaService service;

    public FaseDividaController(FaseDividaService faseDividaService) {
        service = faseDividaService;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody FaseDividaRequestDto request, HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request, httpRequest.getHeader(SharedConstantes.NOME_USUARIO)), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Fases da Dívida."));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody FaseDividaRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request, httpRequest.getHeader(SharedConstantes.NOME_USUARIO)), HttpStatus.OK, "O registro foi salvo com sucesso!"));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, HttpServletRequest httpRequest) {
        service.delete(id, httpRequest.getHeader(SharedConstantes.NOME_USUARIO));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Fase da dívida deletada com sucesso"));
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Fases da dívida encontradas"));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Fase da dívida encontrada"));
    }

    @Override
    @PostMapping("/filtros")
    public ResponseEntity<?> findFaseDividaByFilter(@Valid @RequestBody FaseDividaFilterRequestDto request) {
        List<FaseDividaResponseDto> fasesDivida = service.findByFilter(request);
        String msg  = fasesDivida.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Fases da dívida encontrada com sucesso";
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(fasesDivida, HttpStatus.OK, msg));
    }

    @Override
    @GetMapping("/historico/{id}")
    public ResponseEntity<?> viewChangeHistoryById(@PathVariable(name = "id") UUID id, @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page, @RequestParam(value = "tamanho", required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.viewChangeHistoryById(id, page, size), HttpStatus.OK, "Históricos da fase da dívida encontrada"));
    }

    @Override
    @GetMapping("/fluxo-fase")
    public ResponseEntity<?> obterFluxoFase() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.obterFluxoFase(), HttpStatus.OK, "Fases da dívida encontradas"));

    }

    @Override
    @GetMapping("/semelhantes")
    public ResponseEntity<?> findSemelhantes(@RequestParam String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findSemelhantes(nome), HttpStatus.OK, "Fases da dívida semelhantes encontradas"));

    }

    @Override
    @PutMapping("/fluxo-fase")
    public ResponseEntity<?> salvarFluxoFases(@RequestBody FluxoFaseDividaWrapperRequestDto request) {
        service.salvarFluxoFases(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "O registro foi salvo com sucesso!"));
    }


}
