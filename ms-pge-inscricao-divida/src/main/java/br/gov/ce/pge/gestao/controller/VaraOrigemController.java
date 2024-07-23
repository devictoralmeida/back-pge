package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsConstants;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.dto.response.VaraOrigemResponseDto;
import br.gov.ce.pge.gestao.service.VaraOrigemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "vara-origem")
public class VaraOrigemController {
    private final VaraOrigemService service;

    public VaraOrigemController(VaraOrigemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<VaraOrigemResponseDto>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(service.findAll(), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CONSULTA_TODOS_SUCESSO));
    }
}
