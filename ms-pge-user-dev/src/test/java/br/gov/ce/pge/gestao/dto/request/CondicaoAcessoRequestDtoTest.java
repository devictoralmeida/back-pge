package br.gov.ce.pge.gestao.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

public class CondicaoAcessoRequestDtoTest {

    public static CondicaoAcessoRequestDto getRequest() {
        var request = new CondicaoAcessoRequestDto();
        request.setAlteracaoSenha("1");
        request.setBloqueioAutomatico("1");
        request.setEncerramentoSessao("12:34");
        request.setSenhasCadastradas("1");
        request.setTentativasInvalidas("1");
        return request;
    }

    public static CondicaoAcessoRequestDto getRequestUpdate() {
        var request = new CondicaoAcessoRequestDto();
        request.setBloqueioAutomatico("2");
        request.setAlteracaoSenha("14");
        request.setEncerramentoSessao("43:21");
        request.setTentativasInvalidas("3");
        request.setSenhasCadastradas("15");
        return request;
    }

    @Test
    public void teste_all_fields() {
        var request = new CondicaoAcessoRequestDto();
        request.setAlteracaoSenha("1");
        request.setBloqueioAutomatico("1");
        request.setEncerramentoSessao("43:21");
        request.setSenhasCadastradas("1");
        request.setTentativasInvalidas("1");

        assertNotNull(request);
        assertEquals("1", request.getAlteracaoSenha());
        assertEquals("1", request.getBloqueioAutomatico());
        assertEquals("43:21", request.getEncerramentoSessao());
        assertEquals("1", request.getSenhasCadastradas());
        assertEquals("1", request.getTentativasInvalidas());
    }

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void test_alteracao_senha_notNull() {
        
        CondicaoAcessoRequestDto dto = new CondicaoAcessoRequestDto();
        dto.setBloqueioAutomatico("1");
        dto.setEncerramentoSessao("43:21");
        dto.setSenhasCadastradas("1");
        dto.setTentativasInvalidas("1");

        Set<ConstraintViolation<CondicaoAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("informar alteração de senha", violations.iterator().next().getMessage());
    }

    @Test
    public void test_bloqueio_automatico_notNull() {
        
        CondicaoAcessoRequestDto dto = new CondicaoAcessoRequestDto();
        dto.setAlteracaoSenha("1");
        dto.setEncerramentoSessao("43:21");
        dto.setSenhasCadastradas("1");
        dto.setTentativasInvalidas("1");

        Set<ConstraintViolation<CondicaoAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("informar bloqueio automático", violations.iterator().next().getMessage());
    }

    @Test
    public void test_encerramento_sessao_notNull() {
        
        CondicaoAcessoRequestDto dto = new CondicaoAcessoRequestDto();
        dto.setAlteracaoSenha("1");
        dto.setBloqueioAutomatico("1");
        dto.setSenhasCadastradas("1");
        dto.setTentativasInvalidas("1");

        Set<ConstraintViolation<CondicaoAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("informar encerramento da sessão", violations.iterator().next().getMessage());
    }

    @Test
    public void test_senha_cadastradas_notNull() {
        
        CondicaoAcessoRequestDto dto = new CondicaoAcessoRequestDto();
        dto.setAlteracaoSenha("1");
        dto.setBloqueioAutomatico("1");
        dto.setEncerramentoSessao("43:21");
        dto.setTentativasInvalidas("1");

        Set<ConstraintViolation<CondicaoAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("informar senhas cadastradas", violations.iterator().next().getMessage());
    }

    @Test
    public void test_tentativas_invalidas_notNull() {
        
        CondicaoAcessoRequestDto dto = new CondicaoAcessoRequestDto();
        dto.setAlteracaoSenha("1");
        dto.setBloqueioAutomatico("1");
        dto.setEncerramentoSessao("43:21");
        dto.setSenhasCadastradas("1");

        Set<ConstraintViolation<CondicaoAcessoRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertEquals("informar tentativas inválidas", violations.iterator().next().getMessage());
    }

    @Test
    public void test_ValidDto() {
        
        CondicaoAcessoRequestDto dto = new CondicaoAcessoRequestDto();
        dto.setAlteracaoSenha("1");
        dto.setBloqueioAutomatico("1");
        dto.setEncerramentoSessao("43:21");
        dto.setSenhasCadastradas("1");
        dto.setTentativasInvalidas("1");

        Set<ConstraintViolation<CondicaoAcessoRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

}
