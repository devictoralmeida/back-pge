package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.AcaoJudicialRequestDto;
import br.gov.ce.pge.gestao.dto.request.AtualizaFaseRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaRequestDto;
import br.gov.ce.pge.gestao.dto.request.DividaUpdateRequestDto;
import br.gov.ce.pge.gestao.dto.response.DividaFilterResponseDto;
import br.gov.ce.pge.gestao.dto.response.PaginacaoResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.DividaConsultaService;
import br.gov.ce.pge.gestao.service.DividaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "divida")
public class DividaController {

  private final DividaService service;

  private final DividaConsultaService dividaConsultaService;

  public DividaController(DividaService service, DividaConsultaService dividaConsultaService) {
    this.service = service;
    this.dividaConsultaService = dividaConsultaService;
  }

  @PostMapping(value = "/")
  public ResponseEntity<ResponseDto<?>> save(@Valid @RequestBody DividaRequestDto request) {
    service.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null,
            HttpStatus.CREATED, MessageCommonsConstants.MENSAGEM_SAVE_SUCESSO + " de Inscrições na Dívida Ativa."));
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ResponseDto<?>> update(@PathVariable(value = "id") UUID id, @Valid @RequestBody DividaUpdateRequestDto request, HttpServletRequest httpRequest) throws JsonProcessingException {
    service.update(id, request, httpRequest.getHeader("nomeUsuario"));
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null,
            HttpStatus.OK, MessageCommonsConstants.MENSAGEM_UPDATE_SUCESSO));
  }


  @PostMapping(value = "/filtros")
  public ResponseEntity<?> findByFilterRegisters(@Valid @RequestBody DividaFilterRequestDto request,
                                                 @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limite", required = false, defaultValue = "5") Long limit,
                                                 @RequestParam(value = "ordenacaoPor", required = false) String orderBy) {

    PaginacaoResponseDto<List<DividaFilterResponseDto>> registros = dividaConsultaService.findDividaByFilter(request, page, limit, orderBy);

    String msg = registros.getList().isEmpty() ? MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO;

    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(registros, HttpStatus.OK, msg));
  }


  @PatchMapping("/atualiza-fase")
  public ResponseEntity<?> atualizarFase(@Valid @RequestBody AtualizaFaseRequestDto request) {
    service.atualizarFase(request);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.MSG_ATUALIZA_FASE));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(dividaConsultaService.findById(id), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO));
  }

  @PatchMapping("/acao-judicial/{id}")
  public ResponseEntity<?> registrarAcaoJudicial(@RequestBody AcaoJudicialRequestDto request, @PathVariable(name = "id") UUID id) {
    service.registrarAcao(request, id);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.MSG_ATUALIZA_FASE));
  }

  @GetMapping("/acao-judicial/{id}")
  public ResponseEntity<?> findAcoesJudiciais(@PathVariable(name = "id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(dividaConsultaService.findAcoesJudiciaisByDivida(id), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO));
  }

  @GetMapping(value = "/")
  public ResponseEntity<?> findByIds(@RequestParam(name = "ids") List<UUID> ids) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(dividaConsultaService.findByIds(ids), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO));
  }

  @GetMapping(value = "/inscricao/{numeroInscricao}")
  public ResponseEntity<?> findByNumeroInscricao(@PathVariable(name = "numeroInscricao") String numeroInscricao) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(dividaConsultaService.findByNumeroInscricao(numeroInscricao), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO));
  }

}
