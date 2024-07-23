package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.MotivoAtualizacaoFaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "motivo-atualizacao-fase")
public class MotivoAtualizacaoFaseController {

    private final MotivoAtualizacaoFaseService service;

    public MotivoAtualizacaoFaseController(MotivoAtualizacaoFaseService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public ResponseEntity<ResponseDto<?>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO));
    }

}
