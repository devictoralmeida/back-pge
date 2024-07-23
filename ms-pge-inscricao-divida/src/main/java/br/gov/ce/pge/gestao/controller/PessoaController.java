package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.DocumentoRequestDto;
import br.gov.ce.pge.gestao.dto.response.PessoaResponseDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.ConsultaPessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "pessoa")
public class PessoaController {
  private final ConsultaPessoaService service;

  public PessoaController(ConsultaPessoaService service) {
    this.service = service;
  }

  @PostMapping(value = "/documento")
  public ResponseEntity<?> findByDocumento(@Valid @RequestBody DocumentoRequestDto request) {
    PessoaResponseDto response = service.findByDocumento(request);
    String msg = response != null ? MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO : MessageCommonsConstants.MENSAGEM_CONSULTA_ID_NAO_ENCONTRADO;
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(response, HttpStatus.OK, msg));
  }
}
