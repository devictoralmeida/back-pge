package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.InscricaoRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "inscricao")
public class InscricaoController {
    @Autowired
    private InscricaoService service;

    @PostMapping(value = "/")
    public ResponseEntity<ResponseDto<?>> save(@RequestBody @Valid InscricaoRequestDto request) {
        this.service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null,
                HttpStatus.CREATED, MessageCommonsContanst.MENSAGEM_SAVE_SUCESSO));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto<?>> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(this.service.findById(id),
                HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid InscricaoRequestDto request) {
        this.service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null,
                HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
    }
}
