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

import br.gov.ce.pge.gestao.controller.doc.FaseDividaDocumentation;
import br.gov.ce.pge.gestao.dto.request.FaseDividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.FaseDividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.FluxoFaseDividaWrapperRequestDto;
import br.gov.ce.pge.gestao.dto.response.FaseDividaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.FaseDividaService;

@RestController
@RequestMapping(value = "fase-divida")
public class FaseDividaController implements FaseDividaDocumentation {

    private final FaseDividaService service;

    public FaseDividaController(FaseDividaService faseDividaService) {
        this.service = faseDividaService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody FaseDividaRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(service.save(request), HttpStatus.CREATED, "O registro foi salvo com sucesso! Para visualizar o registro cadastrado, acesse a Consulta de Fases da Dívida."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @Valid @RequestBody FaseDividaRequestDto request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.update(id, request), HttpStatus.OK, "O registro foi salvo com sucesso!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "Fase da dívida deletada com sucesso"));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, "Fases da dívida encontradas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findById(id), HttpStatus.OK, "Fase da dívida encontrada"));
    }

    @PostMapping("/filtros")
    public ResponseEntity<?> findFaseDividaByFilter(@Valid @RequestBody FaseDividaFilterRequestDto request) {
        List<FaseDividaResponseDto> fasesDivida = service.findByFilter(request);
        String msg  = fasesDivida.isEmpty() ? "Nenhum resultado encontrado! Tente mudar os filtros ou o termo buscado." : "Fases da dívida encontrada com sucesso";
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(fasesDivida, HttpStatus.OK, msg));
    }

    @GetMapping("/historico/{id}")
    public ResponseEntity<?> viewChangeHistoryById(@PathVariable(name = "id") UUID id,@RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.viewChangeHistoryById(id, page), HttpStatus.OK, "Históricos da fase da dívida encontrada"));
    }

    @GetMapping("/fluxo-fase")
    public ResponseEntity<?> obterFluxoFase() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.obterFluxoFase(), HttpStatus.OK, "Fases da dívida encontradas"));

    }

    @GetMapping("/semelhantes")
    public ResponseEntity<?> findSemelhantes(@RequestParam String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findSemelhantes(nome), HttpStatus.OK, "Fases da dívida semelhantes encontradas"));

    }

    @PutMapping("/fluxo-fase")
    public ResponseEntity<?> salvarFluxoFases(@RequestBody FluxoFaseDividaWrapperRequestDto request) {
        service.salvarFluxoFases(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, "O registro foi salvo com sucesso!"));
    }


}
