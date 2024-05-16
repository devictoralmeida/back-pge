package br.gov.ce.pge.gestao.service;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public interface EnviarEmailService {
    void enviarEmailTemplate(String assunto, String destinario, String template, Map<String, Object> model) throws IOException, MessagingException, TemplateException;
}
