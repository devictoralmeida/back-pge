package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.entity.AcaoJudicial;
import br.gov.ce.pge.gestao.service.AcaoJudicialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "acao-judicial")
public class AcaoJudicialController {
  private final AcaoJudicialService service;

  public AcaoJudicialController(AcaoJudicialService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<ResponseDto<List<AcaoJudicial>>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findByIdModel(id), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO));
  }
}
