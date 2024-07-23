package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.entity.QualificacaoCorresponsavel;
import br.gov.ce.pge.gestao.service.QualificacaoCorresponsavelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "qualificacao-corresponsavel")
public class QualificacaoCorresponsavelController {
  private final QualificacaoCorresponsavelService service;

  public QualificacaoCorresponsavelController(QualificacaoCorresponsavelService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<ResponseDto<List<QualificacaoCorresponsavel>>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO));
  }
}
