package br.gov.ce.pge.gestao.controller;

import br.gov.ce.pge.gestao.constants.MessageCommonsContanst;
import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDto;
import br.gov.ce.pge.gestao.dto.response.ResponseDto;
import br.gov.ce.pge.gestao.service.UsuarioEmailSenhaService;
import freemarker.template.TemplateException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/usuario")
@CircuitBreaker(name = "circuitBreakerConfiguration")
public class UsuarioEmailSenhaController {

    private final UsuarioEmailSenhaService usuarioEmailService;

    public UsuarioEmailSenhaController(UsuarioEmailSenhaService usuarioEmailService) {
        this.usuarioEmailService = usuarioEmailService;
    }

    @PostMapping("/{id}/email-alteracao/{nomeSistema}")
    public ResponseEntity<?> sendChangeEmail(@PathVariable(name = "id") UUID id, @PathVariable(name = "nomeSistema") String nomeSistema, @RequestBody UsuarioCadastroSenhaRequestDto request) throws MessagingException, TemplateException, IOException, NoSuchAlgorithmException {
        usuarioEmailService.sendChangeEmail(request, id, nomeSistema);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_EMAIL_RECUPERACAO_SENHA));
    }

    @GetMapping("/{email}/email-recuperacao/{nomeSistema}")
    public ResponseEntity<?> sendRecoveryEmail(@PathVariable(name = "email") String email, @PathVariable(name = "nomeSistema") String nomeSistema) throws MessagingException, TemplateException, IOException {
        usuarioEmailService.sendRecoveryEmail(email, nomeSistema);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_EMAIL_RECUPERACAO_SENHA));
    }

    @PatchMapping("/{id}/alteracao-senha/")
    public ResponseEntity<?> changePassword(@PathVariable(name = "id") UUID id, @Valid @RequestBody UsuarioCadastroSenhaRequestDto request) {
        usuarioEmailService.updatePassword(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
    }

    @PatchMapping("/{id}/recuperacao-senha/{token}")
    public ResponseEntity<?> recoverPassword(@PathVariable(name = "id") UUID id, @PathVariable(name = "token") UUID token, @Valid @RequestBody UsuarioCadastroSenhaRequestDto request) {
        usuarioEmailService.recoverPassword(id, token, request);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
    }

    @PatchMapping("/{id}/senha/{token}")
    public ResponseEntity<?> createPassword(@PathVariable(name = "id") UUID id, @PathVariable(name = "token") UUID token, @Valid @RequestBody UsuarioCadastroSenhaRequestDto request) {
        usuarioEmailService.createPassword(id, token, request);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_UPDATE_SUCESSO));
    }

    @GetMapping("/{id}/validacao-codigo/{codigo}")
    public ResponseEntity<?> validarCodigo(@PathVariable(name = "id") UUID id, @PathVariable(name = "codigo") String codigo) {
        usuarioEmailService.validarCodigo(id, codigo);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsContanst.MENSAGEM_CONSULTA_ID_SUCESSO));
    }
}
