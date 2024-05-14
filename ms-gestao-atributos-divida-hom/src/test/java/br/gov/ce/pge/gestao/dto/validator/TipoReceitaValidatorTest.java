package br.gov.ce.pge.gestao.dto.validator;


import br.gov.ce.pge.gestao.dto.request.TipoReceitaRequestDto;
import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import br.gov.ce.pge.gestao.shared.util.ValidateDadosUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoReceitaValidatorTest {

    private TipoReceitaValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new TipoReceitaValidator();
    }

    @Test
    void test_is_valid_with_valid_request_should_return_true() {
        TipoReceitaRequestDto request = new TipoReceitaRequestDto();
        request.setCodigo("123");

        assertTrue(validator.isValid(request, context));
    }

    @Test
    void test_is_valid_with_invalid_codigo_should_throw_negocio_exception() {
        TipoReceitaRequestDto request = new TipoReceitaRequestDto();
        request.setCodigo("abc");
        assertThrows(NegocioException.class, () -> validator.isValid(request, context));
    }

    @Test
    void test_is_valid_with_empty_origem_debitos_should_throw_negocio_exception() {
        TipoReceitaRequestDto request = new TipoReceitaRequestDto();
        request.setCodigo("123");
        request.setOrigemDebitos(Collections.emptyList());
        assertThrows(NegocioException.class, () -> validator.isValid(request, context));
    }
}
