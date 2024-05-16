package br.gov.ce.pge.gestao.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@ExtendWith({ MockitoExtension.class })
public class EnviarEmailServiceImplTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private Configuration configuration;

    @InjectMocks
    private EnviarEmailServiceImpl enviarEmailService;

    @Test
    public void test_enviar_email_template() throws IOException, MessagingException, TemplateException {

        String assunto = "Assunto";
        String destinatario = "destinatario@example.com";
        String templateName = "cadastro-senha-externo.ftlh";
        Map<String, Object> model = new HashMap<>();

        Template templateMock = Mockito.mock(Template.class);

        when(configuration.getTemplate(templateName)).thenReturn(templateMock);

        MimeMessage mimeMessageMock = mock(MimeMessage.class);

        when(emailSender.createMimeMessage()).thenReturn(mimeMessageMock);

        enviarEmailService.enviarEmailTemplate(assunto, destinatario, templateName, model);

        verify(emailSender).send(any(MimeMessage.class));
    }

}
