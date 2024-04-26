package br.gov.ce.pge.gestao.dto.validator;

import br.gov.ce.pge.gestao.shared.exception.NegocioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

public class StringValidatorTest {

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private StringValidator stringValidator;

    public StringValidatorTest() {
        MockitoAnnotations.openMocks(this);
        stringValidator = new StringValidator();
    }

    @Test
    public void test_is_valid_with_valid_string_returns_true() {
        String validString = "ITEM DIVIDA 1";
        boolean isValid = stringValidator.isValid(validString, constraintValidatorContext);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void test_is_valid_with_null_string_returns_true() {
        boolean isValid = stringValidator.isValid(null, constraintValidatorContext);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void test_is_valid_with_empty_string_returns_true() {
        boolean isValid = stringValidator.isValid("", constraintValidatorContext);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void test_is_valid_with_special_characters_throws_negocio_exception() {
        String invalidString = "ITEM DIVIDA 1@!";
        Assertions.assertThrows(NegocioException.class, () -> {
            stringValidator.isValid(invalidString, constraintValidatorContext);
        });
    }
}
