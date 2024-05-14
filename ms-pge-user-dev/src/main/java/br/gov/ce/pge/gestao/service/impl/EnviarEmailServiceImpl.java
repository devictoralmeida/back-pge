package br.gov.ce.pge.gestao.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.gov.ce.pge.gestao.config.PropertiesConfig;
import br.gov.ce.pge.gestao.service.EnviarEmailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class EnviarEmailServiceImpl implements EnviarEmailService {

    private final JavaMailSender emailSender;

    private final Configuration configuration;
    
    public EnviarEmailServiceImpl(JavaMailSender emailSender, Configuration configuration) {
    	this.emailSender = emailSender;
    	this.configuration = configuration;
    }

    @Override
    public void enviarEmailTemplate(String assunto, String destinario, String template, Map<String, Object> model) throws IOException, MessagingException, TemplateException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        InternetAddress sender = new InternetAddress(PropertiesConfig.getProperty("email.remetente"), "PGE - CE");
        InternetAddress destination = new InternetAddress(destinario, "Destinatário");
        helper.setSubject(assunto);
        helper.setTo(destination);
        helper.setFrom(sender);
        String emailContent = getEmailContent(model, template);
        helper.setText(emailContent, true);
        emailSender.send(mimeMessage);
    }

    private String getEmailContent(Map<String, Object> model, String template) throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(template).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}
