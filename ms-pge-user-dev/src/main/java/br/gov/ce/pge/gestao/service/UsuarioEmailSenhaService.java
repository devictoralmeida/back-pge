package br.gov.ce.pge.gestao.service;

import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDto;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Service
public interface UsuarioEmailSenhaService {

    void sendChangeEmail(UsuarioCadastroSenhaRequestDto senha, UUID id, String nomeSistema);

    void sendRecoveryEmail(String email, String nomeSistema) throws MessagingException, TemplateException, IOException;

    void updatePassword(UUID id, UsuarioCadastroSenhaRequestDto request);

    void createPassword(UUID id, UUID token, @Valid UsuarioCadastroSenhaRequestDto request);

    void recoverPassword(UUID id, UUID token, @Valid UsuarioCadastroSenhaRequestDto request);

    void validarCodigo(UUID id, String codigo);
}
