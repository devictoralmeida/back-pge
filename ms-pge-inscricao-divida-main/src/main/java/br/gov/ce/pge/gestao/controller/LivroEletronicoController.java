package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "livro-eletronico")
public class LivroEletronicoController {
  private final LivroEletronicoService service;

  public LivroEletronicoController(LivroEletronicoService service) {
    this.service = service;
  }

  @PostMapping(value = "/filtros")
  public ResponseEntity<?> findByFilter(@RequestBody LivroEletronicoFilterRequestDto request) {
    var livros = this.service.findByFilter(request);
    String msg = livros.isEmpty() ? MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsContanst.MENSAGEM_CONSULTA_FILTRO_SUCESSO;
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(livros, HttpStatus.OK, msg));
  }
}
