package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.request.LivroEletronicoFilterRequestDto;
import br.gov.ce.pge.gestao.dto.request.RegistroLivroFilterRequestDto;
import br.gov.ce.pge.gestao.dto.response.*;
import br.gov.ce.pge.gestao.service.LivroEletronicoService;
import br.gov.ce.pge.gestao.service.RegistroLivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "livro-eletronico")
public class LivroEletronicoController {

  private final LivroEletronicoService service;

  private final RegistroLivroService registroService;

  public LivroEletronicoController(LivroEletronicoService service, RegistroLivroService registroService) {
    this.service = service;
    this.registroService = registroService;
  }

  @PostMapping(value = "/inicia")
  public ResponseEntity<?> iniciarLivro() {
    service.abertura();
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessageCommonsConstants.LIVRO_INICIADO));
  }

  @PostMapping(value = "/fecha")
  public ResponseEntity<?> fecharLivro() {
    service.fechamento();
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.LIVRO_FECHADO));
  }

  @PostMapping(value = "/filtros")
  public ResponseEntity<?> findByFilter(@Valid @RequestBody LivroEletronicoFilterRequestDto request) {
    List<LivroEletronicoFilterResponseDto> livros = service.findByFilter(request);
    String msg = livros.isEmpty() ? MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO;
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(livros, HttpStatus.OK, msg));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> findById(@PathVariable(name = "id") UUID id) {

    LivroEletronicoResponseDto livro = service.findById(id);

    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(livro, HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_ID_SUCESSO));
  }

  @PostMapping(value = "/filtros-registros")
  public ResponseEntity<?> findByFilterRegisters(@Valid @RequestBody RegistroLivroFilterRequestDto request,
                                                 @RequestParam(value = "pagina", required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limite", required = false, defaultValue = "5") Long limit,
                                                 @RequestParam(value = "ordenacaoPor", required = false) String orderBy) {

    PaginacaoResponseDto<List<RegistroInscricaoResponseDto>> registros = registroService.findByFilterRegistroInscricao(request, page, limit, orderBy);

    String msg = registros.getList().isEmpty() ? MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_VAZIA : MessageCommonsConstants.MENSAGEM_CONSULTA_FILTRO_SUCESSO;

    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(registros, HttpStatus.OK, msg));
  }

}
