package br.gov.ce.pge.gestao.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.validation.Valid;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.ce.pge.gestao.dto.request.UsuarioCadastroSenhaRequestDto;
import br.gov.ce.pge.gestao.dto.request.UsuarioRequestDto;

@Service
public interface UsuarioService {

	void save(@Valid UsuarioRequestDto request);

	void update(UUID id, @Valid UsuarioRequestDto request) throws JsonProcessingException;

	void delete(UUID id);

	void bloquearUsuario(UUID id);

	void aceitarTermo(UUID id, String nomeSistema);

	void ultimoAcesso(UUID id);
}
