package br.gov.ce.pge.gestao.dto.validator;

import br.gov.ce.pge.gestao.dto.request.ProdutoServicoRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoServicoValidatorTest {

    private ProdutoServicoValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new ProdutoServicoValidator();
    }

    @Test
    void test_is_valid_with_valid_request_should_return_true() {
        ProdutoServicoRequestDto request = new ProdutoServicoRequestDto();
        request.setCodigo("123");

        assertTrue(validator.isValid(request, context));
    }

    @Test
    void test_is_valid_with_invalid_codigo_should_throw_negocio_exception() {
        ProdutoServicoRequestDto request = new ProdutoServicoRequestDto();
        request.setCodigo("abc");
        assertThrows(NegocioException.class, () -> validator.isValid(request, context));
    }

    @Test
    void test_is_valid_with_empty_origem_debitos_should_throw_negocio_exception() {
        ProdutoServicoRequestDto request = new ProdutoServicoRequestDto();
        request.setCodigo("123");
        request.setIdsTipoReceitas(Collections.emptyList());
        assertThrows(NegocioException.class, () -> validator.isValid(request, context));
    }
}
